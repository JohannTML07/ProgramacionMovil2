package com.example.telefonia

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.PhoneStateListener
import android.telephony.ServiceState
import android.telephony.SignalStrength
import android.telephony.SmsManager
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNodeLifecycleCallback
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.telefonia.viewModel.AppView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BroadcastReceiver: BroadcastReceiver() {
    private var listener: ServiceStateListener? = null
    private var telephonyManager: TelephonyManager? = null
    private var context: Context? = null

    override fun onReceive(context2: Context, intent: Intent){
        val action = intent.action
        context = context2

        if(action == TelephonyManager.ACTION_PHONE_STATE_CHANGED){
            telephonyManager = context2.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            var obtenerNumero = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
            var numeroReal = SharedViewModel.viewModel.telefono
            if(obtenerNumero != null && obtenerNumero == numeroReal){
                enviarMensaje(obtenerNumero)
            }

            Toast.makeText(context, "¡Receptor registrado!", Toast.LENGTH_LONG).show()
            listener = ServiceStateListener()
            telephonyManager?.listen(listener, PhoneStateListener.LISTEN_SERVICE_STATE)
            telephonyManager?.listen(listener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS)
        }
        Log.d("Llamada detectada","Se detecto")
    }

    private fun enviarMensaje(
        numero: String
    ){
        val sms = SmsManager.getDefault()
        val mensaje = SharedViewModel.viewModel.mensaje
        sms.sendTextMessage(numero, null, mensaje, null, null)
        Log.d("Mensaje Enviado",sms.toString())
    }

    private inner class ServiceStateListener: PhoneStateListener(){
        override fun onServiceStateChanged(serviceState: ServiceState) {
            super.onServiceStateChanged(serviceState)
            val conectado = serviceState.state == ServiceState.STATE_IN_SERVICE
            if (conectado) {
                Toast.makeText(context, "Conexión establecida", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Conexión perdida", Toast.LENGTH_LONG).show()
            }
        }

        override fun onSignalStrengthsChanged(signalStrength: SignalStrength) {
            super.onSignalStrengthsChanged(signalStrength)
            Toast.makeText(
                context,
                "Señal cambiada - CDMA: ${signalStrength.cdmaDbm} GSM: ${signalStrength.gsmSignalStrength}",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}