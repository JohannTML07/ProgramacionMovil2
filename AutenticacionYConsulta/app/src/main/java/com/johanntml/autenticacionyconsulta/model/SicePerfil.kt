package com.example.evaluacion1.model

//import androidx.test.espresso.Root
//import androidx.wear.compose.foundation.Element
import kotlinx.serialization.Serializable
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Serializable
data class User(
    val id_control: String,
    val Name: String,
    val pwd: String
)

@Root(name = "accesoLoginResponse",strict = false)

data class Respuesta(
    @field:Element(name = "accesoLoginResult")
    @param: Element(name = "accesoLoginResult")
    val Result: String?=null
)
