package com.example.logintwitter

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider


@Composable
fun MainScreen() {

}



@Composable
fun TwitterLoginScreen(navController: NavController) {
    val context = LocalContext.current
    val activity = context as Activity
    var loginState by remember { mutableStateOf("Desconectado") }
    val auth = FirebaseAuth.getInstance()

    val twitterAuthLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            auth.currentUser?.let {
                loginState = "Conectado como ${it.displayName}"
                navController.navigate("main_screen")
            } ?: run {
                loginState = "Error en el inicio de sesión"
            }
        } else {
            loginState = "Inicio de sesión cancelado"
        }
    }

    Box(

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text( text = "Bienvenido", 
                color = Color.White, 
                fontSize = 28.sp, 
                fontWeight = FontWeight.Bold, 
                modifier = Modifier.padding(bottom = 8.dp) 
        ) 
            Text( text = "Inicia sesión para continuar", 
                color = Color.White.copy(alpha = 0.8f), 
                fontSize = 16.sp, 
                modifier = Modifier.padding(bottom = 32.dp) 
        )
            // Botón de login
            Button(
                onClick = {
                    val provider = OAuthProvider.newBuilder("twitter.com")
                    auth.startActivityForSignInWithProvider(activity, provider.build())
                        .addOnSuccessListener {
                            loginState = "Conectado como ${it.user?.displayName}"
                            navController.navigate("main_screen")
                        }
                        .addOnFailureListener { e ->
                            loginState = "Error: ${e.message}"
                            Log.e("TwitterLogin", "Error en el inicio de sesión con Twitter", e)
                        }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFF1DA1F2)
                ),
                shape = RoundedCornerShape(28.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 4.dp,
                    pressedElevation = 8.dp
                )
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.img),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .padding(end = 8.dp)
                    )
                    Text(
                        text = "Iniciar sesión con Twitter",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

