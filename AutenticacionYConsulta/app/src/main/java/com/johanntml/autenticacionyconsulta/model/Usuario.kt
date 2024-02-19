package com.example.evaluacion1.model

data class Usuario(
    val acceso: Boolean,
    val estatus: String,
    val tipoUsuario: Int,
    val contrasenia: String,
    val matricula: String
)