package com.example.lactare.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.lactare.data.mock.MockData
import com.example.lactare.model.Campanha
import com.example.lactare.ui.components.PageScaffold
import com.example.lactare.ui.components.PrimaryButton
import com.example.lactare.ui.components.SectionCard
import com.example.lactare.ui.components.StatusBadge

@Composable
fun DashboardScreen(
    onBack: () -> Unit = {},
    onGoBancos: () -> Unit = {}
) {
    PageScaffold(
        title = "Dashboard administrativo",
        subtitle = "Visão executiva do Lactare Connect"
    ) { innerModifier ->
        Row(
            modifier = innerModifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SectionCard(modifier = Modifier.width(220.dp).fillMaxSize()) {
                Text("Lactare", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
                Text("Painel principal", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.height(8.dp))
                Text("• Dashboard", color = MaterialTheme.colorScheme.primary)
                Text("• Nutrizes", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("• Bancos de Leite", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("• Campanhas", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.weight(1f))
                PrimaryButton(
                    text = "Ver bancos",
                    onClick = onGoBancos,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MetricCard("12.543", "Nutrizes cadastradas", "+12%", Modifier.weight(1f))
                    MetricCard("8.920L", "Litros doados no mês", "+18%", Modifier.weight(1f))
                    MetricCard("45.200", "Bebês beneficiados", "+24%", Modifier.weight(1f))
                    MetricCard("68%", "Taxa de conversão", "+5%", Modifier.weight(1f))
                }

                SectionCard(
                    title = "Campanhas de engajamento",
                    subtitle = "Monitoramento de performance e conversão",
                    modifier = Modifier.fillMaxWidth()
                ) {
                    PrimaryButton(
                        text = "Nova campanha",
                        onClick = {},
                        modifier = Modifier.align(Alignment.End)
                    )

                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(MockData.campanhas) { campanha ->
                            CampanhaRow(campanha)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MetricCard(valor: String, rotulo: String, variacao: String, modifier: Modifier = Modifier) {
    SectionCard(modifier = modifier) {
        Text(rotulo, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(valor, style = MaterialTheme.typography.titleLarge)
        Text(variacao, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
private fun CampanhaRow(campanha: Campanha) {
    SectionCard(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(campanha.nome, style = MaterialTheme.typography.titleMedium)
                Text(
                    "Alcance: ${campanha.alcance} • Conversão: ${campanha.conversao}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            StatusBadge(campanha.status)
        }
    }
}
