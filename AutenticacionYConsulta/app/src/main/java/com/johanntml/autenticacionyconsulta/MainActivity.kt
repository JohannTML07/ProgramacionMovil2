@file:OptIn(ExperimentalMaterial3Api::class)

package com.johanntml.autenticacionyconsulta

import android.graphics.fonts.FontFamily
import android.os.Build
import android.os.Bundle
import android.os.ext.SdkExtensions
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.evaluacion1.Screens.ViewModelLogin
import com.johanntml.autenticacionyconsulta.ui.theme.AutenticacionYConsultaTheme

class MainActivity : ComponentActivity() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AutenticacionYConsultaTheme {
                val viewModel : ViewModelLogin = viewModel(factory = ViewModelLogin.Factory)
                // Envolver el contenido en un tema aplicará estilos definidos en ese tema.
                loginAppPreview(viewModel)
            }
        }
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun loginAppPreview(viewModel: ViewModelLogin){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(30.dp).fillMaxSize()
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
                modifier = Modifier.height(70.dp).width(250.dp),
                onClick = {
                            viewModel.Authentication()
                }) {
                Text(text = "Ingresar",
                    modifier=Modifier.width(140.dp),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center)
            }
        }
    }
}