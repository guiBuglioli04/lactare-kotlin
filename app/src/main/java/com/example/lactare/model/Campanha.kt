package com.example.lactare.model

enum class StatusCampanha { ATIVA, AGENDADA, CONCLUIDA }

data class Campanha(
    val id: Int,
    val nome: String,
    val status: StatusCampanha,
    val alcance: String,
    val conversao: String
)
