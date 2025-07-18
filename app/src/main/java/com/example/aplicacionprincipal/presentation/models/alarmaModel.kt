package com.example.aplicacionprincipal.presentation.models

data class Alarma(
    val hora: String,
    val dias: List<String>,
    var activa: Boolean = false
)