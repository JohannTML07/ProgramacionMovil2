package com.example.evaluacion1.data

import android.util.Log
import com.example.evaluacion1.model.Estudiante
import com.example.evaluacion1.model.User
import com.example.evaluacion1.model.Usuario
import com.example.evaluacion1.network.SiceApi
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpException
import com.google.gson.Gson
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory

//Interface de sice repository
interface SiceRepositotory{
    suspend fun Acceso(usuario: String, password: String): Usuario
    suspend fun getCookies() : ResponseBody
    suspend fun infoMatricula(): Estudiante
}

//implementación del repositorio de la interfaz
class NetworkSiceRepository(
    private val siceApi: SiceApi
): SiceRepositotory{
    override suspend fun Acceso(usuario: String, password: String): Usuario {
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


        siceApi.getCookies()
        val xml = requestBodyXmlText.format(usuario, password)
        val respuesta = siceApi.Login(xml.toRequestBody()).string()
        Log.d("Respuesta: ", respuesta)

        //Log.d("Respuesta2: ",extracJson(respuesta, "accesoLoginResult"))
        //val objeto: Usuario = Usuario(false, "", 0, "", "")
        return Gson().fromJson(extracJson(respuesta, "accesoLoginResult"), Usuario::class.java)
    }
    override suspend fun getCookies(): ResponseBody = siceApi.getCookies()

    override suspend fun infoMatricula(): Estudiante{
        val respMatricula = """
            <?xml version="1.0" encoding="utf-8"?>
            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
              <soap:Body>
                <getAlumnoAcademicoWithLineamiento xmlns="http://tempuri.org/" />
              </soap:Body>
            </soap:Envelope>
            """.trimIndent()
        val respuesta2 = siceApi.Login(respMatricula.toRequestBody()).string()

        return Gson().fromJson(extracJson(respuesta2, "getAlumnoAcademicoWithLineamientoResult"), Estudiante::class.java)
    }

    fun extracJson(responseBody: String, tag: String):String{
        Log.d("errores", responseBody)
        val factory = XmlPullParserFactory.newInstance()
        factory.isNamespaceAware = true
        val parser = factory.newPullParser()
        parser.setInput(responseBody.reader())

        var eventType = parser.eventType
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG && parser.name == tag) {
                parser.next()
                return parser.text
            }
            eventType = parser.next()
        }
        return ""
    }
}
// = siceApi.Login()