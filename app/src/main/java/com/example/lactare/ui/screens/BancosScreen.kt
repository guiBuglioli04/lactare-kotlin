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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lactare.data.mock.MockData
import com.example.lactare.model.BancoDeLeite
import com.example.lactare.ui.components.LactareHeader
import com.example.lactare.ui.components.OpenBadge

@Composable
fun BancosScreen(
    onBack: () -> Unit = {}, // <-- Parâmetro adicionado aqui
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

    Column(modifier = Modifier.fillMaxSize()) {
        LactareHeader(
            onBancos = {},
            onCadastro = onGoCadastro,
            onAdmin = onGoDashboard
        )

        Row(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                OutlinedTextField(
                    value = busca,
                    onValueChange = { busca = it },
                    label = { Text("Buscar por CEP ou bairro") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                Box {
                    OutlinedTextField(
                        value = "Ordenar por: $sortOption",
                        onValueChange = {},
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

                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .padding(end = 40.dp)
                            .background(androidx.compose.ui.graphics.Color.Transparent)
                    )
                }

                Spacer(Modifier.height(10.dp))
                Button(onClick = { expandedSort = true }) {
                    Text("Selecionar ordenação")
                }

                Spacer(Modifier.height(10.dp))

                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(bancosFiltrados) { banco ->
                        BancoCard(banco = banco)
                    }
                }
            }

            Spacer(Modifier.width(12.dp))

            Card(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Mapa Interativo", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(androidx.compose.ui.graphics.Color(0xFFE5F3FF))
                    ) {
                        Text(
                            "Simulação de mapa com marcadores\n• Eurofarma (1.2 km) selecionado",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }

        Button(
            onClick = onGoChat,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Abrir Lactare Connect")
        }
    }
}

@Composable
private fun BancoCard(banco: BancoDeLeite) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(banco.nome, style = MaterialTheme.typography.titleMedium)
            Text("Endereço: ${banco.endereco}")
            Text("Horário: ${banco.horario}")
            Text("Tel: ${banco.telefone}")
            if (banco.abertoAgora) OpenBadge()
            Text("Distância: ${banco.distanciaKm} km")
            Button(onClick = {}) { Text("Entrar em contato") }
        }
    }
}