package com.example.evaluacion1.network

import com.example.evaluacion1.model.Respuesta
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface SiceApi{
    @Headers("Content-Type: application/xml")
    @GET("Usuario")
    fun getUser(): String

    @POST("/ws/wsalumnos.asmx")
    @Headers("Content-Type: text/xml")
    suspend fun Login(@Body cuerpo: RequestBody): Call<ResponseBody>

    @GET("/ws/wsalumnos.asmx")
    suspend fun getCookies(): Call<ResponseBody>
}