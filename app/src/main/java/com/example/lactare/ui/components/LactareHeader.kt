package com.example.lactare.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun LactareHeader(
    onInicio: () -> Unit = {},
    onComoDoar: () -> Unit = {},
    onBancos: () -> Unit = {},
    onCadastro: () -> Unit = {},
    onAdmin: () -> Unit = {},
    selected: String = ""
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Lactare",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f)
        )
        NavTextAction("Início", selected == "inicio", onInicio)
        NavTextAction("Como doar", selected == "doar", onComoDoar)
        NavTextAction("Bancos", selected == "bancos", onBancos)
        NavTextAction("Cadastro", selected == "cadastro", onCadastro)
        NavTextAction("Admin", selected == "admin", onAdmin)
    }
}
