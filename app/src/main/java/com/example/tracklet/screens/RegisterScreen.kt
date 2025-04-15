package com.example.tracklet.screens

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tracklet.R

@Composable
fun RegisterScreen(modifier: Modifier = Modifier,navController: NavController,isAuthenticated:Boolean) {

   LaunchedEffect(Unit) {
      if(isAuthenticated){
         navController.navigate("home")
      }
   }

   var fullname by remember {
      mutableStateOf("")
   }

   var email by remember {
      mutableStateOf("")
   }


   var aadhar by remember {
      mutableStateOf("")
   }

   var password by remember {
      mutableStateOf("")
   }

   Column(
      modifier = Modifier.fillMaxSize(),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
   ) {
      Image(painter = painterResource(R.drawable.register), contentDescription = "Register",modifier = Modifier.size(150.dp))
      Spacer(modifier = Modifier.height(12.dp))

      Text("Register Your Account", fontSize = 28.sp, fontWeight = FontWeight.Bold)

      Spacer(modifier = Modifier.height(16.dp))

      OutlinedTextField(value=fullname, label = {
         Text("Full Name")
      }, onValueChange = {
         fullname = it
      })

      Spacer(modifier = Modifier.height(16.dp))

      OutlinedTextField(value=email, label = {
         Text("Email")
      }, onValueChange = {
         email = it
      })
      Spacer(modifier = Modifier.height(16.dp))


      OutlinedTextField(value=aadhar, label = {
         Text("Aadhar Number")
      }, onValueChange = {
         aadhar = it
      })

      Spacer(modifier = Modifier.height(16.dp))

      OutlinedTextField(value=password, label = {
         Text("Password")
      }, onValueChange = {
         password = it
      })

      Spacer(modifier = Modifier.height(16.dp))

      OutlinedTextField(value="", label = {
         Text("Confirm Password")
      }, onValueChange = {})

      Spacer(modifier = Modifier.height(18.dp))

      Button(onClick = {
         navController.navigate("login")

      }) {
        Text("Register")
      }

      Spacer(modifier = Modifier.height(14.dp))
      Text("Go back to Login",modifier = Modifier.clickable {
         navController.navigate("login")
      }, color = Color.Blue)

   }
}