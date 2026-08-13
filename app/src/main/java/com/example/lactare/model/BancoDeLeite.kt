package com.example.lactare.model

data class BancoDeLeite(
    val id: Int,
    val nome: String,
    val endereco: String,
    val horario: String,
    val telefone: String,
    val abertoAgora: Boolean,
    val distanciaKm: Double
)
