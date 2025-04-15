package com.example.tracklet.screens

import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.tracklet.utils.LocationUtils
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun Location(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val fusedLocationClient = LocationUtils.getFusedClient(context)
    val cameraPositionState = rememberCameraPositionState()

    var myLoc by rememberSaveable  {
        mutableStateOf(
            LatLng(1.35, 103.87)
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {isGranted->
            if(isGranted){
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        location?.let {
                            myLoc = LatLng(it.latitude, it.longitude)
                            cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(myLoc, 15f))
                        }?:run{
                        }

                    }
            }
        }
    )


    LaunchedEffect(Unit) {
        launcher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(
            myLocationButtonEnabled = true,
        ),
        properties = MapProperties(
            isMyLocationEnabled = true,
            mapType = MapType.HYBRID
        )
    ){
        Marker(
            state = MarkerState(position = myLoc),
            title = "My Location"
        )
    }
}