package com.example.lactare.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.lactare.data.mock.MockData
import com.example.lactare.model.Campanha
import com.example.lactare.ui.components.PageScaffold
import com.example.lactare.ui.components.PrimaryButton
import com.example.lactare.ui.components.SectionCard
import com.example.lactare.ui.components.SecondaryButton
import com.example.lactare.ui.components.StatusBadge
import com.example.lactare.ui.theme.AccentBlue

@Composable
fun DashboardScreen(
    onBack: () -> Unit = {},
    onGoBancos: () -> Unit = {}
) {
    var showNovaCampanhaDialog by remember { mutableStateOf(false) }

    if (showNovaCampanhaDialog) {
        AlertDialog(
            onDismissRequest = { showNovaCampanhaDialog = false },
            title = { Text("Nova campanha") },
            text = {
                Text(
                    "Em um app completo, este formulário permitiria criar uma nova campanha de engajamento com nome, público-alvo, canal e data de início.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            confirmButton = {
                TextButton(onClick = { showNovaCampanhaDialog = false }) {
                    Text("Entendido")
                }
            },
            containerColor = MaterialTheme.colorScheme.surface
        )
    }

    PageScaffold(
        title = "Dashboard administrativo",
        subtitle = "Visão executiva do Lactare Connect",
        onBack = onBack
    ) { innerModifier ->
        Column(
            modifier = innerModifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // ── Métricas ──────────────────────────────────────────────
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                MetricCard("12.543", "Nutrizes cadastradas", "+12%", Modifier.weight(1f))
                MetricCard("8.920L", "Litros doados", "+18%", Modifier.weight(1f))
                MetricCard("45.200", "Bebês beneficiados", "+24%", Modifier.weight(1f))
                MetricCard("68%", "Conversão", "+5%", Modifier.weight(1f))
            }

            // ── Linha principal: Sidebar + Campanhas ──────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(460.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Sidebar de navegação
                SectionCard(
                    modifier = Modifier
                        .width(200.dp)
                        .fillMaxSize()
                ) {
                    Text(
                        "Lactare",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = AccentBlue
                    )
                    Text(
                        "Painel principal",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.height(8.dp))

                    SidebarItem(label = "Dashboard", selected = true, onClick = {})
                    SidebarItem(label = "Nutrizes", selected = false, onClick = {})
                    SidebarItem(label = "Bancos de Leite", selected = false, onClick = onGoBancos)
                    SidebarItem(label = "Campanhas", selected = false, onClick = {})
                    SidebarItem(label = "Relatórios", selected = false, onClick = {})

                    Spacer(Modifier.weight(1f))

                    SecondaryButton(
                        text = "Ver bancos",
                        onClick = onGoBancos,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // Painel de campanhas — Column simples, sem LazyColumn aninhado
                SectionCard(
                    title = "Campanhas de engajamento",
                    subtitle = "Monitoramento de performance e conversão",
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                ) {
                    PrimaryButton(
                        text = "+ Nova campanha",
                        onClick = { showNovaCampanhaDialog = true },
                        modifier = Modifier.align(Alignment.End)
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        MockData.campanhas.forEach { campanha ->
                            CampanhaRow(campanha)
                        }
                    }
                }
            }

            // ── Resumo rápido ─────────────────────────────────────────
            SectionCard(
                title = "Resumo do período",
                subtitle = "Agosto 2026"
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ResumoItem("4 campanhas ativas", Modifier.weight(1f))
                    ResumoItem("3 bancos operando", Modifier.weight(1f))
                    ResumoItem("12.543 doadoras registradas", Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun SidebarItem(label: String, selected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(
                if (selected) AccentBlue.copy(alpha = 0.12f)
                else androidx.compose.ui.graphics.Color.Transparent
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 10.dp)
    ) {
        Text(
            text = if (selected) "• $label" else label,
            style = MaterialTheme.typography.bodyMedium,
            color = if (selected) AccentBlue else MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun ResumoItem(text: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun MetricCard(valor: String, rotulo: String, variacao: String, modifier: Modifier = Modifier) {
    SectionCard(modifier = modifier) {
        Text(
            rotulo,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            valor,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            variacao,
            style = MaterialTheme.typography.labelMedium,
            color = AccentBlue
        )
    }
}

@Composable
private fun CampanhaRow(campanha: Campanha) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
            .padding(horizontal = 14.dp, vertical = 12.dp)
    ) {
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
            Spacer(Modifier.width(12.dp))
            StatusBadge(campanha.status)
        }
    }
}
