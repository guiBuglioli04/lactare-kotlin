package com.example.lactare.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.lactare.data.mock.MockData
import com.example.lactare.model.Campanha

@Composable
fun DashboardScreen(
    onBack: () -> Unit = {},
    onGoBancos: () -> Unit = {}
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxSize()) {
            // Sidebar / Menu Lateral
            Column(
                modifier = Modifier
                    .width(200.dp)
                    .fillMaxSize()
                    .background(Color(0xFF003366))
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Lactare", style = MaterialTheme.typography.titleLarge, color = Color.White)
                    Spacer(modifier = Modifier.height(24.dp))
                    Text("• Dashboard", color = Color.White)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("• Nutrizes", color = Color.LightGray)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("• Bancos de Leite", color = Color.LightGray)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("• Campanhas", color = Color.LightGray)
                }

                Button(
                    onClick = onGoBancos,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Ver Bancos")
                }
            }

            // Conteúdo Principal do Dashboard
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            ) {
                Text("Dashboard", style = MaterialTheme.typography.headlineMedium)
                Text("Visão geral do sistema Lactare Connect", style = MaterialTheme.typography.bodySmall)

                Spacer(modifier = Modifier.height(16.dp))

                // Cards de Métricas Superiores
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    MetricCard("12.543", "Nutrizes Cadastradas", "+12%", Modifier.weight(1f))
                    MetricCard("8.920L", "Litros Doados (mês)", "+18%", Modifier.weight(1f))
                    MetricCard("45.200", "Bebês Beneficiados", "+24%", Modifier.weight(1f))
                    MetricCard("68%", "Taxa de Conversão", "+5%", Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Tabela de Campanhas
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Campanhas de Engajamento", style = MaterialTheme.typography.titleMedium)
                            Button(onClick = {}) { Text("Nova Campanha") }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

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
}

@Composable
private fun MetricCard(valor: String, rotulo: String, variacao: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(rotulo, style = MaterialTheme.typography.bodySmall)
                Text(variacao, color = Color(0xFF2E7D32), style = MaterialTheme.typography.labelSmall)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(valor, style = MaterialTheme.typography.titleLarge)
        }
    }
}

@Composable
private fun CampanhaRow(campanha: Campanha) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(campanha.nome, style = MaterialTheme.typography.bodyMedium)
                Text("Status: ${campanha.status}", style = MaterialTheme.typography.bodySmall)
            }
            Text("Alcance: ${campanha.alcance}")
            Text(campanha.conversao)
        }
    }
}