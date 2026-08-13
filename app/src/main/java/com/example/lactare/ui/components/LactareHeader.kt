package com.example.lactare.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.lactare.ui.theme.LactareBlue
import com.example.lactare.ui.theme.LactareTextPrimary

@Composable
fun LactareHeader(
    onInicio: () -> Unit = {},
    onComoDoar: () -> Unit = {},
    onBancos: () -> Unit = {},
    onCadastro: () -> Unit = {},
    onAdmin: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(androidx.compose.ui.graphics.Color.White)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Lactare - banco de leite",
            color = LactareBlue,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        HeaderLink("Início", onInicio)
        HeaderLink("Como Doar", onComoDoar)
        HeaderLink("Bancos de Leite", onBancos)
        HeaderLink("Cadastro", onCadastro)
        HeaderLink("Área Admin", onAdmin)
    }
}

@Composable
private fun HeaderLink(label: String, onClick: () -> Unit) {
    Text(
        text = label,
        color = LactareTextPrimary,
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .clickable { onClick() }
    )
}
