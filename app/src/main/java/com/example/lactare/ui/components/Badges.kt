package com.example.lactare.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.lactare.model.StatusCampanha

@Composable
fun OpenBadge(text: String = "Aberto agora") {
    StatusChip(
        label = text,
        background = Color(0xFF213D30),
        foreground = Color(0xFF92D8AF)
    )
}

@Composable
fun StatusBadge(status: StatusCampanha) {
    val (bg, fg, label) = when (status) {
        StatusCampanha.ATIVA -> Triple(Color(0xFF213D30), Color(0xFF92D8AF), "ativa")
        StatusCampanha.AGENDADA -> Triple(Color(0xFF2A3345), Color(0xFF96C6FF), "agendada")
        StatusCampanha.CONCLUIDA -> Triple(Color(0xFF303646), Color(0xFFC0CBDE), "concluída")
    }
    StatusChip(label = label, background = bg, foreground = fg)
}
