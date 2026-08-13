package com.example.lactare.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.lactare.model.StatusCampanha

@Composable
fun OpenBadge(text: String = "Aberto agora") {
    Text(
        text = text,
        color = Color(0xFF166534),
        modifier = Modifier
            .background(Color(0xFFDCFCE7), RoundedCornerShape(20.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    )
}

@Composable
fun StatusBadge(status: StatusCampanha) {
    val (bg, fg, label) = when (status) {
        StatusCampanha.ATIVA -> Triple(Color(0xFFDCFCE7), Color(0xFF15803D), "ativa")
        StatusCampanha.AGENDADA -> Triple(Color(0xFFE0F2FE), Color(0xFF0C4A6E), "agendada")
        StatusCampanha.CONCLUIDA -> Triple(Color(0xFFF3F4F6), Color(0xFF4B5563), "concluída")
    }
    Text(
        text = label,
        color = fg,
        modifier = Modifier
            .background(bg, RoundedCornerShape(999.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    )
}
