package com.smvit.ar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/** UI texts (Slovak: the trail is for Slovak hikers). */
object Texts {
    const val TRACKING_PROMPT = "Pomaly pohybuj telefónom, kým sa spustí sledovanie…"
    const val SCAN_PROMPT = "Namier kameru na QR kód"
    fun holdSteady(name: String, count: Int, required: Int) = "Nájdené: $name – podrž telefón ($count/$required)"
    fun unknownCode(payload: String) = "Neznámy kód: „$payload“"
    const val RESCAN = "Znova skenovať"
    fun companionJoined(name: String) = "$name ide s tebou!"
    fun collectionButton(progress: TrailProgress) = "Zbierka ${progress.count}/${progress.total}"
    fun newlyCollected(progress: TrailProgress) = "Nové! Pridané do zbierky (${progress.count}/${progress.total})"
    const val ALREADY_COLLECTED = "Už v zbierke"
    const val CLOSE = "Zavrieť"
    const val COLLECTION_TITLE = "Zbierka postavičiek"
    const val NOT_FOUND_YET = "??? – ešte si ju nenašiel"
    const val BADGE_TITLE = "Odznak získaný!"
    const val BADGE_TEXT = "Našiel si všetky postavičky na trase. Gratulujeme!"
    const val CLEAR_COLLECTION = "Vymazať zbierku"
    const val CLEAR_CONFIRM = "Naozaj vymazať? (Klepni znova.)"
}

/** The character's name and story, shown when it appears. */
@Composable
fun StoryCard(entry: CatalogEntry, collectedLine: String, onClose: () -> Unit, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(entry.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text(collectedLine, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
            entry.story?.let { Text(it, style = MaterialTheme.typography.bodyMedium) }
            TextButton(onClick = onClose, modifier = Modifier.align(Alignment.End)) { Text(Texts.CLOSE) }
        }
    }
}

/** Every trail character, found ones with their story, plus the badge once all are found. */
@Composable
fun CollectionScreen(catalog: Catalog, progress: TrailProgress, onClose: () -> Unit, onClear: () -> Unit) {
    Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            Modifier
                .safeDrawingPadding()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(Texts.COLLECTION_TITLE, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Text(Texts.collectionButton(progress), style = MaterialTheme.typography.titleMedium)
            if (progress.isComplete) {
                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                    Column(Modifier.padding(16.dp)) {
                        Text("🏅 " + Texts.BADGE_TITLE, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text(Texts.BADGE_TEXT)
                    }
                }
            }
            for (id in progress.trail) {
                val entry = catalog.byId(id) ?: continue
                Card(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        if (id in progress.found) {
                            Text(entry.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            entry.story?.let { Text(it, style = MaterialTheme.typography.bodySmall) }
                        } else {
                            Text(Texts.NOT_FOUND_YET, style = MaterialTheme.typography.titleMedium)
                        }
                    }
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(onClick = onClose) { Text(Texts.CLOSE) }
                // Two taps, so a stray one on the trail can't wipe the collection.
                var confirming by remember { mutableStateOf(false) }
                TextButton(onClick = {
                    if (confirming) onClear()
                    confirming = !confirming
                }) { Text(if (confirming) Texts.CLEAR_CONFIRM else Texts.CLEAR_COLLECTION) }
            }
        }
    }
}

@Composable
fun BadgeDialog(onClose: () -> Unit) {
    AlertDialog(
        onDismissRequest = onClose,
        confirmButton = { TextButton(onClick = onClose) { Text(Texts.CLOSE) } },
        title = { Text("🏅 " + Texts.BADGE_TITLE) },
        text = { Text(Texts.BADGE_TEXT) },
    )
}

/** White text on a translucent black box, readable over any camera image. */
@Composable
fun StatusText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        color = Color.White,
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier
            .background(Color.Black.copy(alpha = 0.5f))
            .padding(horizontal = 12.dp, vertical = 8.dp),
    )
}
