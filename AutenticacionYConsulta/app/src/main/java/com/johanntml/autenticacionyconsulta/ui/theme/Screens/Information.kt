package com.johanntml.autenticacionyconsulta.ui.theme.Screens

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.evaluacion1.Screens.ViewModelLogin

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun Information(viewModel: ViewModelLogin){
    viewModel.updateU(viewModel.Estado.copy(usuario=viewModel.Estado.usuario.copy(acceso = false)))
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {

        Row (
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ){
            Text(
                text = "Datos:" +
                        "\n -Carrera: ${viewModel.Estado.estudiante.carrera}" +
                        "\n -Nombre: ${viewModel.Estado.estudiante.nombre}" +
                        "\n -Matricula: ${viewModel.Estado.estudiante.matricula}" +
                        "\n -CreditosActuales: ${viewModel.Estado.estudiante.cdtosActuales}" +
                        "\n -Semestre: ${viewModel.Estado.estudiante.semActual}" +
                        "\n -Estatus: ${viewModel.Estado.estudiante.estatus}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold)
        }
    }
}