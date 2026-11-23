package com.smvit.ar.qrsheet

import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import java.io.ByteArrayOutputStream
import java.io.File

/**
 * Renders printable QR markers as an A4 PDF, one code per page, drawn as vector
 * squares at an exact physical size. Print at 100% ("Actual size") and check
 * the 10 cm ruler on each page.
 *
 * Usage: qrsheet OUT.pdf SIZE_MM PAYLOAD [PAYLOAD...]
 */
fun main(args: Array<String>) {
    if (args.size < 3) {
        System.err.println("usage: qrsheet OUT.pdf SIZE_MM PAYLOAD [PAYLOAD...]")
        kotlin.system.exitProcess(2)
    }
    val out = File(args[0])
    val sizeMm = args[1].toDouble()
    val payloads = args.drop(2)

    val pages = payloads.map { pageContent(it, sizeMm) }
    out.parentFile?.mkdirs()
    out.writeBytes(Pdf.write(pages))
    println("wrote ${out.path}: ${payloads.size} page(s), symbol edge $sizeMm mm")
}

private const val PAGE_W_MM = 210.0
private const val PAGE_H_MM = 297.0

private fun pt(mm: Double) = mm * 72 / 25.4

private fun fmt(v: Double) = "%.3f".format(java.util.Locale.ROOT, v)

private fun pageContent(payload: String, sizeMm: Double): String {
    // Version 1 (21x21) has no alignment pattern, so scanners only guess the
    // bottom-right corner (as if the code were seen square-on). Under any tilt
    // that corner is off by several pixels and the app rejects the pose, so
    // never print version 1. Level Q also survives more dirt and wear outdoors.
    fun encode(version: Int?) = QRCodeWriter().encode(
        payload, BarcodeFormat.QR_CODE, 0, 0,
        buildMap {
            put(EncodeHintType.MARGIN, 0)
            put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q)
            if (version != null) put(EncodeHintType.QR_VERSION, version)
        },
    )
    val matrix = encode(null).let { if (it.width == 21) encode(2) else it }
    val modules = matrix.width
    val moduleMm = sizeMm / modules
    val left = (PAGE_W_MM - sizeMm) / 2
    val top = 60.0 // mm from the top edge of the page to the top of the symbol

    val sb = StringBuilder()
    sb.append("0 g\n")
    // Horizontal runs of dark modules as rectangles (PDF y axis points up).
    for (row in 0 until modules) {
        var col = 0
        while (col < modules) {
            if (!matrix.get(col, row)) { col++; continue }
            val start = col
            while (col < modules && matrix.get(col, row)) col++
            val x = left + start * moduleMm
            val y = PAGE_H_MM - top - (row + 1) * moduleMm
            sb.append("${fmt(pt(x))} ${fmt(pt(y))} ${fmt(pt((col - start) * moduleMm))} ${fmt(pt(moduleMm))} re\n")
        }
    }
    sb.append("f\n")

    fun text(xMm: Double, yFromTopMm: Double, sizePt: Int, s: String) {
        val escaped = s.replace("\\", "\\\\").replace("(", "\\(").replace(")", "\\)")
        sb.append("BT /F1 $sizePt Tf ${fmt(pt(xMm))} ${fmt(pt(PAGE_H_MM - yFromTopMm))} Td ($escaped) Tj ET\n")
    }
    text(20.0, 20.0, 16, "Postavicky na trase - QR marker")
    text(20.0, 28.0, 11, "Print at 100% / Actual size. Keep the white margin around the code.")
    text(20.0, 34.0, 11, "Check: the ruler below must measure exactly 10 cm.")

    val below = top + sizeMm + 15
    text(left, below, 13, "ID: $payload")
    text(left, below + 7, 11, "Black square edge = ${fmt(sizeMm).trimEnd('0').trimEnd('.')} mm, ${modules}x$modules modules")

    // 10 cm ruler with 1 cm ticks.
    val rulerY = pt(PAGE_H_MM - (below + 25))
    sb.append("0.5 w\n${fmt(pt(left))} ${fmt(rulerY)} m ${fmt(pt(left + 100))} ${fmt(rulerY)} l S\n")
    for (cm in 0..10) {
        val x = fmt(pt(left + cm * 10.0))
        val h = if (cm % 5 == 0) 4.0 else 2.0
        sb.append("$x ${fmt(rulerY)} m $x ${fmt(rulerY + pt(h))} l S\n")
    }
    text(left, below + 32, 9, "0")
    text(left + 98, below + 32, 9, "10 cm")
    return sb.toString()
}

/** Minimal PDF 1.4 writer: A4 pages with vector content and Helvetica text. */
private object Pdf {
    fun write(pageContents: List<String>): ByteArray {
        val objects = mutableListOf<String>()
        fun add(body: String): Int {
            objects += body
            return objects.size
        }
        val catalog = add("") // filled in once the page tree number is known
        val font = add("<< /Type /Font /Subtype /Type1 /BaseFont /Helvetica >>")
        val pagesId = objects.size + 1 + pageContents.size * 2
        val pageIds = pageContents.map { content ->
            val bytes = content.toByteArray(Charsets.US_ASCII)
            val stream = add("<< /Length ${bytes.size} >>\nstream\n$content\nendstream")
            add(
                "<< /Type /Page /Parent $pagesId 0 R /MediaBox [0 0 ${fmt(pt(PAGE_W_MM))} ${fmt(pt(PAGE_H_MM))}] " +
                    "/Resources << /Font << /F1 $font 0 R >> >> /Contents $stream 0 R >>"
            )
        }
        check(add("<< /Type /Pages /Kids [${pageIds.joinToString(" ") { "$it 0 R" }}] /Count ${pageIds.size} >>") == pagesId)
        objects[catalog - 1] = "<< /Type /Catalog /Pages $pagesId 0 R >>"

        val out = ByteArrayOutputStream()
        fun emit(s: String) = out.write(s.toByteArray(Charsets.US_ASCII))
        emit("%PDF-1.4\n")
        val offsets = objects.mapIndexed { i, body ->
            val offset = out.size()
            emit("${i + 1} 0 obj\n$body\nendobj\n")
            offset
        }
        val xref = out.size()
        emit("xref\n0 ${objects.size + 1}\n0000000000 65535 f \n")
        offsets.forEach { emit("%010d 00000 n \n".format(it)) }
        emit("trailer\n<< /Size ${objects.size + 1} /Root $catalog 0 R >>\nstartxref\n$xref\n%%EOF\n")
        return out.toByteArray()
    }
}
