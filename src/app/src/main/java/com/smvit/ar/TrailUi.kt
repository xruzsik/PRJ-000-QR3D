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
import androidx.compose.material3.Button
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
import androidx.compose.ui.res.stringResource
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
    fun hint(hint: SearchHint.Hint) = when (hint) {
        SearchHint.Hint.TOO_DARK -> "Tip: je tu príliš tma. Choď na svetlejšie miesto alebo zapni viac svetla."
        SearchHint.Hint.TOO_FAST -> "Tip: hýbeš sa príliš rýchlo. Zastav a pohybuj telefónom pomalšie."
        SearchHint.Hint.NOT_ENOUGH_DETAIL -> "Tip: kamera nevidí dosť detailov. Namier ju na miesto s trávou, kameňmi či stromami, nie na hladkú stenu alebo zem."
        SearchHint.Hint.MOVE_SLOWLY -> "Tip: pomaly pohni telefónom do strán, aby kamera videla okolie. Pomôže aj viac svetla."
        SearchHint.Hint.GET_CLOSER -> "Tip: priblíž sa ku kódu, aby bol celý v zábere, a chvíľu drž telefón pokojne."
    }

    const val PHONE_HOT = "Telefón je horúci. Medzi zastávkami ho nechaj odpočívať vo vrecku, aby vychladol."

    // Start screen
    const val HOW_TO_PLAY = "Na každej zastávke nájdi QR kód a namier naň kameru. Objaví sa postavička, " +
        "povie ti svoj príbeh a pridá sa do tvojej zbierky. Keď nájdeš všetky, získaš odznak."
    fun routeInfo(lengthKm: Double?, minutes: Int?) = listOfNotNull(
        lengthKm?.let { "%.1f km".format(it).replace('.', ',') },
        minutes?.let { if (it >= 60) "približne ${it / 60} h ${it % 60} min".replace(" 0 min", "") else "približne $it min" },
    ).joinToString(" · ").ifEmpty { null }
    const val STOPS = "Zastávky"
    fun stop(number: Int, name: String?) = "$number. ${name ?: "???"}"
    const val SAFETY_TITLE = "Bezpečne na trase"
    const val SAFETY_TEXT = "Pri chôdzi sleduj chodník, nie mobil. Telefón vytiahni až pri zastávke s QR kódom " +
        "a nevychádzaj zo značeného chodníka."
    const val START = "Začať"
    const val CONTINUE = "Pokračovať"
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

/**
 * First screen (US-08, US-09): the route, its stops (names only once found),
 * a safety note, and the button that starts the camera. The AR camera runs
 * only after "Začať", so the phone can stay in the pocket between stops.
 */
@Composable
fun StartScreen(catalog: Catalog, progress: TrailProgress, onStart: () -> Unit) {
    Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            Modifier
                .safeDrawingPadding()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                catalog.routeName ?: stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
            )
            Texts.routeInfo(catalog.lengthKm, catalog.minutes)?.let {
                Text(it, style = MaterialTheme.typography.titleMedium)
            }
            Text(Texts.HOW_TO_PLAY, style = MaterialTheme.typography.bodyLarge)

            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(Texts.STOPS, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text(Texts.collectionButton(progress), style = MaterialTheme.typography.titleMedium)
                    }
                    progress.trail.forEachIndexed { i, id ->
                        val found = id in progress.found
                        Text(
                            Texts.stop(i + 1, if (found) catalog.byId(id)?.name else null) + if (found) "  ✓" else "",
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                }
            }

            Card(
                Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
            ) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("⚠️ " + Texts.SAFETY_TITLE, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(Texts.SAFETY_TEXT, style = MaterialTheme.typography.bodyMedium)
                }
            }

            Button(onClick = onStart, modifier = Modifier.fillMaxWidth()) {
                Text(if (progress.count > 0) Texts.CONTINUE else Texts.START, style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}
