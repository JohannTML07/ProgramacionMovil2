package com.example.evaluacion1.data

import android.telecom.CallScreeningService.CallResponse
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.evaluacion1.Screens.UserUiState
import com.example.evaluacion1.model.Respuesta
import com.example.evaluacion1.network.SiceApi
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpException
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.awaitResponse
import retrofit2.http.Body
import java.io.IOException
import kotlin.math.log

//Interface de sice repository
interface SiceRepositotory{
    suspend fun Acceso(usuario: String, password: String): String
    suspend fun getCookies() : Call<ResponseBody>
}

//implementación del repositorio de la interfaz
class NetworkSiceRepository(
    private val siceApi: SiceApi
): SiceRepositotory{
    override suspend fun Acceso(usuario: String, password: String): String {
        Log.d("Autenticacion", usuario)
        Log.d("Autenticacion", password)
        val requestBodyXmlText =
            """
            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
               <soap:Body>
                  <accesoLogin xmlns="http://tempuri.org/">
                    <strMatricula>%s</strMatricula>
                    <strContrasenia>%s</strContrasenia>
                    <tipoUsuario>ALUMNO</tipoUsuario>
                  </accesoLogin>
               </soap:Body>
            </soap:Envelope>
            """.trimIndent()

        siceApi.getCookies().awaitResponse()
        val xml = requestBodyXmlText.format(usuario, password)
        val respuesta = siceApi.Login(xml.toRequestBody()).awaitResponse()
        Log.d("Respuesta: ", respuesta.body()?.string().toString())
        return respuesta.body()?.string().toString()
    }
    override suspend fun getCookies(): Call<ResponseBody> = siceApi.getCookies()
}
// = siceApi.Login()