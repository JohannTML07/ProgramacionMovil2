package com.example.evaluacion1.data

import com.example.evaluacion1.network.SiceApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppConteiner{
    val siceRepositotory:SiceRepositotory
}
class DefaultAppContainer:AppConteiner{
    private val baseUrl = "https://sicenet.surguanajuato.tecnm.mx"
    private val cookies : CookiesInterceptor = CookiesInterceptor()
    private val ok : OkHttpClient = OkHttpClient.Builder().addInterceptor(cookies).build()
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .client(ok)
        .build()
    private val retrofitService:SiceApi by lazy {
        retrofit.create(SiceApi::class.java)
    }
    override val siceRepositotory: SiceRepositotory by lazy{
        NetworkSiceRepository(retrofitService)
    }

}

class CookiesInterceptor : Interceptor {

    // Variable que almacena las cookies
    private var cookies: List<String> = emptyList()

    // Método para establecer las cookies
    fun setCookies(cookies: List<String>) {
        this.cookies = cookies
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        // Agregar las cookies al encabezado de la solicitud
        if (cookies.isNotEmpty()) {
            val cookiesHeader = StringBuilder()
            for (cookie in cookies) {
                if (cookiesHeader.isNotEmpty()) {
                    cookiesHeader.append("; ")
                }
                cookiesHeader.append(cookie)
            }

            request = request.newBuilder()
                .header("Cookie", cookiesHeader.toString())
                .build()
        }

        val response = chain.proceed(request)

        // Almacenar las cookies de la respuesta para futuras solicitudes
        val receivedCookies = response.headers("Set-Cookie")
        if (receivedCookies.isNotEmpty()) {
            setCookies(receivedCookies)
        }

        return response
    }
}