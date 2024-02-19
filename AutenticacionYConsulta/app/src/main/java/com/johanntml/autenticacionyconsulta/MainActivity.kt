@file:OptIn(ExperimentalMaterial3Api::class)

package com.johanntml.autenticacionyconsulta

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.evaluacion1.Screens.ViewModelLogin
import com.example.evaluacion1.model.Usuario
import com.johanntml.autenticacionyconsulta.ui.theme.AutenticacionYConsultaTheme
import com.johanntml.autenticacionyconsulta.ui.theme.Screens.Information

class MainActivity : ComponentActivity() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AutenticacionYConsultaTheme {
                val viewModel : ViewModelLogin = viewModel(factory = ViewModelLogin.Factory)
                // Envolver el contenido en un tema aplicará estilos definidos en ese tema.
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "login") {
                    composable("login") {
                        loginAppPreview(viewModel, navController)
                    }
                    composable("information") {
                        Information(ViewModel = viewModel)
                    }
                }

            }
        }
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun loginAppPreview(viewModel: ViewModelLogin, navController: NavHostController){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(30.dp)
            .fillMaxSize()
    ) {
        Row (
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth(),
        ){
            Text(
                text = "Número de Control",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold)
        }
        TextField(
            value = viewModel.Usuario,
            label = { Text(text = "Ingresa el número de control") },
            onValueChange = {
                            viewModel.updateUsuario(it)
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(35.dp))
        Row (
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ){
            Text(
                text = "Contraseña",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold)
        }
        TextField(
            value = viewModel.Password,
            label = { Text(text = "Ingresa la contraseña") },
            onValueChange = {
                            viewModel.updatePasword(it)
            },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
        )
        Spacer(modifier = Modifier.height(35.dp))
        Row(
            modifier=Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                modifier = Modifier
                    .height(70.dp)
                    .width(250.dp),
                onClick = {
                    viewModel.Authentication()
                    if(viewModel.Estado.usuario.acceso){
                        navController.navigate("information")
                    }
                }) {
                Text(text = "Ingresar",
                    modifier=Modifier.width(140.dp),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center)
            }
        }
    }
}