package com.example.telefonia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.telefonia.ui.theme.TelefoniaTheme
import com.example.telefonia.viewModel.AppView

class MainActivity : ComponentActivity() {
    lateinit var view:AppView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TelefoniaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppTelefono()
                    view = ViewModelProvider(this).get(AppView::class.java)
                }
            }
        }
    }
}

object SharedViewModel {
    lateinit var viewModel: AppView
}


@Composable
fun AppTelefono(
    viewModel: AppView = viewModel(factory = AppView.Factory)
) {
    SharedViewModel.viewModel = viewModel
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ){
        Row{
            Text(
                text = "Mensaje atomatizado CLJ",
                fontSize = 20.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.ExtraBold
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = viewModel.telefono,
            label = { Text(text = "Ingrese el teléfono") },
            onValueChange = { viewModel.actualizarTelefono(it) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = viewModel.mensaje,
            label = { Text(text = "Ingrese el mensaje") },
            onValueChange = { viewModel.actualizarMensaje(it) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = { /* clic del botón */ }, modifier = Modifier
            .padding(10.dp)) {
            Text("Aceptar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TelefoniaTheme {
        AppTelefono()
    }
}