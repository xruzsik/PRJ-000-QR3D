package com.smvit.ar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // The camera delivers 30 frames a second; rendering at the A53's 120 Hz
        // only takes CPU/GPU time away from ARCore's tracker (which kept falling
        // behind) and adds heat.
        window.attributes = window.attributes.apply { preferredRefreshRate = 60f }
        // A broken route file stops here with a message naming the character and field.
        val catalog = Catalog.load(assets)
        setContent {
            MaterialTheme {
                TrailApp(catalog)
            }
        }
    }
}

/** Start screen first; the AR screen (and camera) only after "Začať". Back returns to the start screen. */
@Composable
private fun TrailApp(catalog: Catalog) {
    var started by rememberSaveable { mutableStateOf(false) }
    if (started) {
        ArScreen(catalog, onBack = { started = false })
    } else {
        val context = LocalContext.current
        // Re-read each time the start screen shows, so it reflects finds from the AR screen.
        val progress = remember { TrailProgress(catalog.trail, CollectionStore(context).load()) }
        StartScreen(catalog, progress, onStart = { started = true })
    }
}
