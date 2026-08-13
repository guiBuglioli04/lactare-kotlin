package com.example.lactare.data.mock

import com.example.lactare.model.BancoDeLeite
import com.example.lactare.model.Campanha
import com.example.lactare.model.MensagemChat
import com.example.lactare.model.StatusCampanha

object MockData {

    val bancosDeLeite = listOf(
        BancoDeLeite(
            id = 1,
            nome = "Banco de Leite Humano Eurofarma",
            endereco = "Av. Ver. José Diniz, 3465 - Campo Belo, São Paulo - SP",
            horario = "Seg-Sex 8h-17h | Sáb 8h-12h",
            telefone = "(11) 96629-0681",
            abertoAgora = true,
            distanciaKm = 1.2
        ),
        BancoDeLeite(
            id = 2,
            nome = "Banco de Leite Hospital São Paulo",
            endereco = "Rua Napoleão de Barros, 715 - Vila Clementino, São Paulo - SP",
            horario = "Seg-Sex 7h-19h",
            telefone = "(11) 5576-4848",
            abertoAgora = true,
            distanciaKm = 3.5
        ),
        BancoDeLeite(
            id = 3,
            nome = "Banco de Leite Hospital das Clínicas",
            endereco = "Av. Dr. Enéas Carvalho de Aguiar, 255 - Cerqueira César, São Paulo - SP",
            horario = "Seg-Sex 8h-18h",
            telefone = "(11) 2661-0000",
            abertoAgora = true,
            distanciaKm = 5.1
        )
    )

    val mensagensIniciaisChat = listOf(
        MensagemChat(
            id = 1,
            isBot = true,
            texto = "Olá! Eu sou a assistente Lactare 💙 Estou aqui para ajudar você com informações sobre doação de leite humano. Como posso te ajudar hoje?"
        )
    )

    val campanhas = listOf(
        Campanha(1, "Campanha Maio Violeta", StatusCampanha.ATIVA, "15.420", "68%"),
        Campanha(2, "WhatsApp - Primeira Doação", StatusCampanha.ATIVA, "8.932", "72%"),
        Campanha(3, "Lembrete Mensal", StatusCampanha.AGENDADA, "12.543", "—"),
        Campanha(4, "Semana da Doação", StatusCampanha.CONCLUIDA, "22.145", "58%")
    )
}
