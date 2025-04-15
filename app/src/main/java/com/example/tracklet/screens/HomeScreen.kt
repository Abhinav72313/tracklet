
package com.example.tracklet.screens

import android.content.Intent
import android.location.Location
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf

import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tracklet.R
import com.example.tracklet.components.ContactPicker
import com.example.tracklet.components.StartBGLocationButton
import com.example.tracklet.components.StopBGLocationButton
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
            Box (
                modifier = Modifier.fillMaxSize(),

            ){
                Image(
                    painter = painterResource(id = R.drawable.background), // your drawable
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.matchParentSize()
                )


                Column(
                   modifier = Modifier.fillMaxWidth().height(200.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Text("Tracklet", color = Color.White ,fontWeight = FontWeight.Bold, fontSize = 50.sp)
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 180.dp) // top space above sheet
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                        )
                        .padding(16.dp) // inner padding inside column
                ) {
                    Text("This is inside bottom sheet", color = Color.Black)
                    // Add more content here

                    ContactPicker()
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

