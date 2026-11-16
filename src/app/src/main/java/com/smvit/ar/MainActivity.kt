package com.smvit.ar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme

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
                ArScreen(catalog)
            }
        }
    }
}
