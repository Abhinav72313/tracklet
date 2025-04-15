package com.example.tracklet.services

import android.Manifest
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresPermission
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationBuilderWithBuilderAccessor
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.tracklet.CHANNEL_ID
import com.example.tracklet.utils.LocationUtils
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import com.example.tracklet.R

class LocationService :Service (){

    private  val locationRequest by lazy {
        LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY,1000).setIntervalMillis(5000).build()
    }

    private val locationCallback by lazy {
        object : LocationCallback(){
            override fun onLocationAvailability(p0: LocationAvailability) {
                super.onLocationAvailability(p0)
            }

            override fun onLocationResult(location: LocationResult) {
                val latitude = location.lastLocation?.latitude.toString()
                val longitude = location.lastLocation?.longitude.toString()
                startServiceForeground(latitude,longitude)
            }
        }
    }
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        locationUpdates()
        return START_STICKY
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION])
    private fun locationUpdates(){
        val fusedLocationClient = LocationUtils.getFusedClient(this)
        fusedLocationClient.requestLocationUpdates(
            locationRequest,locationCallback,null
        )
    }


    private fun startServiceForeground(lat:String,long:String){
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Location Updates")
            .setContentText("Lat - $lat || Long - $long")
            .build()

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ){
            if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED){
                startForeground(1,notification)
            }
        }else{
            startForeground(1,notification)
        }
    }
}