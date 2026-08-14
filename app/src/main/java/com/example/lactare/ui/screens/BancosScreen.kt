package com.example.lactare.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.lactare.data.mock.MockData
import com.example.lactare.model.BancoDeLeite
import com.example.lactare.ui.components.DropdownField
import com.example.lactare.ui.components.InputField
import com.example.lactare.ui.components.LactareHeader
import com.example.lactare.ui.components.OpenBadge
import com.example.lactare.ui.components.PageScaffold
import com.example.lactare.ui.components.PrimaryButton
import com.example.lactare.ui.components.SectionCard
import com.example.lactare.ui.components.StatusChip

private enum class SortOption(val label: String) {
    DISTANCIA("Mais próximo"),
    NOME("Por nome (A-Z)"),
    ABERTO("Aberto agora primeiro")
}

@Composable
fun BancosScreen(
    onBack: () -> Unit = {},
    onGoCadastro: () -> Unit = {},
    onGoChat: () -> Unit = {},
    onGoDashboard: () -> Unit = {}
) {
    var busca by remember { mutableStateOf("") }
    var sortOption by remember { mutableStateOf(SortOption.DISTANCIA) }
    var bancoSelecionado by remember { mutableStateOf<BancoDeLeite?>(null) }

    val bancosFiltrados = MockData.bancosDeLeite
        .filter {
            busca.isBlank() ||
                    it.endereco.contains(busca, ignoreCase = true) ||
                    it.nome.contains(busca, ignoreCase = true)
        }
        .let { list ->
            when (sortOption) {
                SortOption.DISTANCIA -> list.sortedBy { it.distanciaKm }
                SortOption.NOME -> list.sortedBy { it.nome }
                SortOption.ABERTO -> list.sortedByDescending { it.abertoAgora }
            }
        }

    // Dialog de contato
    if (bancoSelecionado != null) {
        val banco = bancoSelecionado!!
        AlertDialog(
            onDismissRequest = { bancoSelecionado = null },
            title = { Text(banco.nome) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("📍 ${banco.endereco}", style = MaterialTheme.typography.bodyMedium)
                    Text("📞 ${banco.telefone}", style = MaterialTheme.typography.bodyMedium)
                    Text("🕐 ${banco.horario}", style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(
                        text = if (banco.abertoAgora) "✅ Aberto agora" else "🔴 Fechado no momento",
                        style = MaterialTheme.typography.bodySmall,
                        color = if (banco.abertoAgora) Color(0xFF92D8AF) else Color(0xFFFFB3BE)
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { bancoSelecionado = null }) {
                    Text("Fechar")
                }
            },
            containerColor = MaterialTheme.colorScheme.surface
        )
    }

    PageScaffold(
        title = "Bancos de leite",
        subtitle = "Encontre a unidade ideal para doação",
        onBack = onBack
    ) { innerModifier ->
        Column(
            modifier = innerModifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SectionCard {
                LactareHeader(
                    onInicio = onGoCadastro,
                    onBancos = {},
                    onCadastro = onGoCadastro,
                    onAdmin = onGoDashboard,
                    selected = "bancos"
                )

                InputField(
                    value = busca,
                    onValueChange = { busca = it },
                    label = "Buscar por nome, endereço ou bairro"
                )

                DropdownField(
                    value = sortOption.label,
                    label = "Ordenação",
                    options = SortOption.entries,
                    onSelect = { sortOption = it },
                    optionLabel = { it.label }
                )
            }

            Row(modifier = Modifier.weight(1f)) {
                LazyColumn(
                    modifier = Modifier.weight(1.25f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(bancosFiltrados) { banco ->
                        BancoCard(
                            banco = banco,
                            onContato = { bancoSelecionado = banco }
                        )
                    }

                    if (bancosFiltrados.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(0.4f))
                                    .padding(24.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "Nenhum banco encontrado para \"$busca\"",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.width(12.dp))

                SectionCard(
                    title = "Mapa da cobertura",
                    subtitle = "Visualização rápida",
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .padding(16.dp)
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Text(
                                text = "Marcadores ativos",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                            bancosFiltrados.forEach { banco ->
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(
                                                if (banco.abertoAgora) Color(0xFF213D30)
                                                else MaterialTheme.colorScheme.surfaceVariant
                                            )
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            "${banco.distanciaKm} km",
                                            style = MaterialTheme.typography.labelMedium,
                                            color = if (banco.abertoAgora) Color(0xFF92D8AF)
                                            else MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    Text(
                                        text = banco.nome.substringAfter("Banco de Leite ").take(24),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }

            PrimaryButton(
                text = "Abrir Lactare Connect",
                onClick = onGoChat,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun BancoCard(banco: BancoDeLeite, onContato: () -> Unit) {
    SectionCard(modifier = Modifier.fillMaxWidth()) {
        Text(banco.nome, style = MaterialTheme.typography.titleMedium)
        Text(
            banco.endereco,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (banco.abertoAgora) {
                OpenBadge()
            } else {
                StatusChip(
                    label = "Fechado",
                    background = Color(0xFF403137),
                    foreground = Color(0xFFFFB3BE)
                )
            }
            StatusChip(
                label = "${banco.distanciaKm} km",
                background = MaterialTheme.colorScheme.surfaceVariant,
                foreground = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Text("🕐 ${banco.horario}", style = MaterialTheme.typography.bodySmall)
        Text("📞 ${banco.telefone}", style = MaterialTheme.typography.bodySmall)

        PrimaryButton(
            text = "Ver detalhes e contato",
            onClick = onContato,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
