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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class Mensagem(val texto: String, val isUser: Boolean)

@Composable
fun ChatScreen() {
    var mensagemTexto by remember { mutableStateOf("") }
    val mensagens = remember {
        mutableStateListOf(
            Mensagem("Olá! Eu sou a assistente Lactare 💙 Estou aqui para ajudar você com informações sobre doação de leite humano. Como posso te ajudar hoje?", isUser = false)
        )
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header do Chat
            Text("Lactare Connect", style = MaterialTheme.typography.titleLarge)
            Text("• Respondemos em menos de 1 minuto", style = MaterialTheme.typography.bodySmall)

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de Mensagens
            LazyColumn(
                modifier = Modifier
                    .weight(1f) // Funciona perfeitamente aqui por ser filho direto do ColumnScope
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(mensagens) { msg ->
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = if (msg.isUser) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
                        ),
                        modifier = Modifier.align(if (msg.isUser) Alignment.End else Alignment.Start)
                    ) {
                        Text(
                            text = msg.texto,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
            }

            // Sugestões de Respostas Rápidas
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(onClick = { mensagens.add(Mensagem("Como doar?", isUser = true)) }) {
                    Text("Como doar")
                }
                OutlinedButton(onClick = { mensagens.add(Mensagem("Encontrar banco de leite", isUser = true)) }) {
                    Text("Bancos de leite")
                }
            }

            // Input de Mensagem
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = mensagemTexto,
                    onValueChange = { mensagemTexto = it },
                    placeholder = { Text("Digite sua mensagem...") },
                    modifier = Modifier.weight(1f) // Funciona perfeitamente aqui por ser filho direto do RowScope
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = {
                        if (mensagemTexto.isNotBlank()) {
                            mensagens.add(Mensagem(mensagemTexto, isUser = true))
                            mensagemTexto = ""
                        }
                    }
                ) {
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Enviar")
                }
            }
        }
    }
}