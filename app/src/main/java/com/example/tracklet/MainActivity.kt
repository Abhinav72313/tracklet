package com.example.tracklet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.tracklet.screens.Home
import com.example.tracklet.screens.Location
import com.example.tracklet.screens.LoginScreen
import com.example.tracklet.screens.RegisterScreen
import com.example.tracklet.screens.Settings
import com.example.tracklet.ui.theme.TrackletTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrackletTheme(
               darkTheme = false
            ) {
                Auth()
           }
        }

    }
}

@Composable
fun Auth(modifier: Modifier = Modifier) {
    var isAuthenticated by remember {
        mutableStateOf(false)
    }

    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        if(isAuthenticated){
            navController.navigate("home"){
                popUpTo("login"){ inclusive =true}
            }
        }else{
            navController.navigate("login")
        }
    }

    NavHost(navController, startDestination = "login" ){
        composable("login") {
            LoginScreen(isAuthenticated=isAuthenticated,onLoginSuccess = {
                isAuthenticated = true
                navController.navigate("home"){
                    popUpTo("login"){ inclusive =true}
                }
            }, navController = navController)
        }
        composable("home") {
            MainScreen()
        }

        composable("register") {
           RegisterScreen(isAuthenticated=isAuthenticated, navController =navController )
        }
    }

}

data class TabItem(val title:String, val icon_selected : ImageVector, val icon_unselected : ImageVector);

@Composable
fun MainScreen(modifier: Modifier = Modifier)  {


    val items = listOf(
        TabItem("Home", icon_unselected = Icons.Outlined.Home, icon_selected = Icons.Filled.Home),
        TabItem("Location", icon_selected = Icons.Filled.Map, icon_unselected = Icons.Outlined.Map),
        TabItem("Settings", icon_unselected = Icons.Outlined.Settings,icon_selected = Icons.Filled.Settings)
    )

    var selectedItem by remember {
        mutableStateOf(0)
    }



    Scaffold (
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItem == index,
                        onClick = { selectedItem = index },
                        label = { Text(text = item.title) },
                        icon = {
                            Icon(
                                imageVector = if (selectedItem == index) item.icon_selected else item.icon_unselected,
                                contentDescription = item.title
                            )
                        }
                    )
                }

            }
        },
        modifier = Modifier
            .fillMaxSize()
            .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding())
    ) { innerPadding ->
        ContentScreen(modifier = Modifier.padding(innerPadding), selectedItem)
    }
}


@Composable
fun ContentScreen(modifier: Modifier = Modifier,selectedIndex:Int) {
    when(selectedIndex){
        0 -> Home()
        1 -> Location()
        2 -> Settings()
    }

}