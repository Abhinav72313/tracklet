package com.example.tracklet.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.example.tracklet.R
import com.example.tracklet.components.PasswordTextField

@Composable
fun LoginScreen(modifier: Modifier = Modifier,isAuthenticated:Boolean,onLoginSuccess:() -> Unit,navController: NavController) {

    var email by remember {
        mutableStateOf("")
    }

    var password by remember{
        mutableStateOf("")
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Image(painter = painterResource(id = R.drawable.mobile_login), contentDescription = "Login",modifier=Modifier.size(150.dp))

        Spacer(modifier=Modifier.height(16.dp))
        Text("Login To Your Account", fontSize = 28.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier=Modifier.height(18.dp))

        OutlinedTextField(value=email, onValueChange = {
            email = it
        }, label = {
            Text("Email")
        })

        Spacer(modifier=Modifier.height(16.dp))

        PasswordTextField(password, onPasswordChange = {
            password = it
        })

        Spacer(modifier=Modifier.height(16.dp))

        Button(onClick = {
            onLoginSuccess()
        }) {
            Text("Submit")
        }

        Spacer(modifier=Modifier.height(16.dp))

        Text("Register",modifier = Modifier.clickable {
            navController.navigate("register")
        }, color = Color.Blue)

    }

}