package com.example.lactare.model

data class Nutriz(
    val nomeCompleto: String = "",
    val email: String = "",
    val telefone: String = "",
    val cep: String = "",
    val estado: String = "",
    val cidade: String = "",
    val bairro: String = "",
    val disponibilidade: Set<String> = emptySet(),
    val consentimento: Boolean = false
)
