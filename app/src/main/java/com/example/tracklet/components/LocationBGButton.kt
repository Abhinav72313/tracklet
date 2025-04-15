package com.example.tracklet.components

import android.content.Intent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.tracklet.services.LocationService

@Composable
fun StartBGLocationButton(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Button(
        onClick = {
            val intent = Intent(context, LocationService::class.java)
            context.startService(intent)

        }
    ){
        Text("Start Background Location Tracking")
    }

}

@Composable
fun StopBGLocationButton(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Button(
        onClick = {
            val intent = Intent(context, LocationService::class.java)
            context.stopService(intent)
        }
    ) {
        Text("Stop Background Location Tracking")
    }
}


