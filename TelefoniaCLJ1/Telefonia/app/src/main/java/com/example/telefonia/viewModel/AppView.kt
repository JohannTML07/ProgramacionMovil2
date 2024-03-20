package com.example.telefonia.viewModel

import android.telephony.SmsManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AppView: ViewModel() {
    var telefono by mutableStateOf("")
    var mensaje by mutableStateOf("")

    fun actualizarTelefono(dato: String){
        telefono = dato
    }

    fun actualizarMensaje(dato: String){
        mensaje = dato
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                AppView()
            }
        }
    }

}