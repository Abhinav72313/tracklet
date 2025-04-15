
package com.example.tracklet.screens

import android.content.Intent
import android.location.Location
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf

import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.tracklet.services.LocationService
import com.example.tracklet.utils.LocationUtils
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Home(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val fusedLocatoinClient = remember {
        LocationUtils.getFusedClient(context)
    }

    var locationText by remember {
        mutableStateOf("Location not available")
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {isGranted->
            if(isGranted){
                fusedLocatoinClient.lastLocation
                    .addOnSuccessListener { location:Location? ->
                        location?.let {
                            locationText = "Location: ${it.latitude}, ${it.longitude}"
                        }?:run{
                            locationText = "Location not available"
                        }

                    }
            }else{
                locationText = "Permission denied"
            }
        }
    )

    val permission = rememberMultiplePermissionsState(permissions =
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            listOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.POST_NOTIFICATIONS
            )
        }else{
            listOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    )



    LaunchedEffect(Unit) {

        permission.launchMultiplePermissionRequest()
    }

    when {
        permission.allPermissionsGranted->{
            Column (
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment =  Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ){

                Button(
                    onClick = {
                        launcher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
                    }
                ) {
                    Text("Get Location")
                }
                Spacer(modifier=Modifier.height(20.dp))

                Text(locationText)


                Spacer(modifier=Modifier.height(20.dp))

                Button(
                    onClick = {
                        val intent = Intent(context,LocationService::class.java)
                        context.startService(intent)

                    }
                ){
                    Text("Start Background Location Tracking")
                }

                Button(
                    onClick = {
                        val intent = Intent(context,LocationService::class.java)
                        context.stopService(intent)
                    }
                ){
                    Text("Stop Background Location Tracking")
                }
            }
        }
        permission.shouldShowRationale->{
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                Button(
                    onClick = {
                        permission.launchMultiplePermissionRequest()
                    }
                ) {
                    Text("Give Permissions")
                }
            }
        }
    }


}