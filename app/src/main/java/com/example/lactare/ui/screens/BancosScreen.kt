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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.lactare.data.mock.MockData
import com.example.lactare.model.BancoDeLeite
import com.example.lactare.ui.components.InputField
import com.example.lactare.ui.components.LactareHeader
import com.example.lactare.ui.components.OpenBadge
import com.example.lactare.ui.components.PageScaffold
import com.example.lactare.ui.components.PrimaryButton
import com.example.lactare.ui.components.SecondaryButton
import com.example.lactare.ui.components.SectionCard
import com.example.lactare.ui.components.StatusChip

@Composable
fun BancosScreen(
    onBack: () -> Unit = {},
    onGoCadastro: () -> Unit = {},
    onGoChat: () -> Unit = {},
    onGoDashboard: () -> Unit = {}
) {
    var busca by remember { mutableStateOf("") }
    var expandedSort by remember { mutableStateOf(false) }
    var sortOption by remember { mutableStateOf("Mais próximo") }

    val bancosFiltrados = MockData.bancosDeLeite
        .filter {
            busca.isBlank() || it.endereco.contains(busca, ignoreCase = true) || it.nome.contains(busca, ignoreCase = true)
        }
        .sortedBy { it.distanciaKm }

    PageScaffold(
        title = "Bancos de leite",
        subtitle = "Encontre a unidade ideal para doação"
    ) { innerModifier ->
        Column(
            modifier = innerModifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SectionCard {
                LactareHeader(
                    onInicio = onBack,
                    onBancos = {},
                    onCadastro = onGoCadastro,
                    onAdmin = onGoDashboard,
                    selected = "bancos"
                )

                InputField(
                    value = busca,
                    onValueChange = { busca = it },
                    label = "Buscar por endereço, CEP ou bairro"
                )

                Box {
                    InputField(
                        value = "Ordenação: $sortOption",
                        onValueChange = {},
                        label = "Ordenar",
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    DropdownMenu(
                        expanded = expandedSort,
                        onDismissRequest = { expandedSort = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Mais próximo") },
                            onClick = {
                                sortOption = "Mais próximo"
                                expandedSort = false
                            }
                        )
                    }
                }

                SecondaryButton(
                    text = "Selecionar ordenação",
                    onClick = { expandedSort = true }
                )
            }

            Row(modifier = Modifier.weight(1f)) {
                LazyColumn(
                    modifier = Modifier.weight(1.25f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(bancosFiltrados) { banco ->
                        BancoCard(banco = banco)
                    }
                }

                Spacer(Modifier.width(12.dp))

                SectionCard(
                    title = "Mapa (simulado)",
                    subtitle = "Visualização rápida da cobertura",
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Marcadores ativos:\n• Eurofarma (1.2 km)\n• Hospital São Paulo (3.5 km)\n• HC (5.1 km)",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodyMedium
                        )
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
private fun BancoCard(banco: BancoDeLeite) {
    SectionCard(modifier = Modifier.fillMaxWidth()) {
        Text(banco.nome, style = MaterialTheme.typography.titleMedium)
        Text(
            "${banco.endereco}",
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

        Text("Horário: ${banco.horario}", style = MaterialTheme.typography.bodySmall)
        Text("Telefone: ${banco.telefone}", style = MaterialTheme.typography.bodySmall)

        PrimaryButton(
            text = "Entrar em contato",
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}
