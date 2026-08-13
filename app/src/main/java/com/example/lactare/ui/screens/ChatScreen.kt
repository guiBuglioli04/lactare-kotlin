package com.example.lactare.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.lactare.ui.components.InputField
import com.example.lactare.ui.components.PageScaffold
import com.example.lactare.ui.components.SectionCard
import com.example.lactare.ui.components.TagChip

private data class Mensagem(val texto: String, val isUser: Boolean)

@Composable
@OptIn(ExperimentalLayoutApi::class)
fun ChatScreen() {
    var mensagemTexto by remember { mutableStateOf("") }
    val mensagens = remember {
        mutableStateListOf(
            Mensagem(
                "Olá! Sou a assistente Lactare. Posso orientar você sobre doação, coleta e bancos próximos.",
                isUser = false
            )
        )
    }
    val sugestoes = listOf("Como doar", "Bancos de leite", "Horários de coleta")

    PageScaffold(
        title = "Lactare Connect",
        subtitle = "Atendimento digital com resposta rápida"
    ) { innerModifier ->
        Column(
            modifier = innerModifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(mensagens) { msg ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = if (msg.isUser) Arrangement.End else Arrangement.Start
                    ) {
                        SectionCard(modifier = Modifier.fillMaxWidth(0.82f)) {
                            Text(
                                text = msg.texto,
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (msg.isUser) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = if (msg.isUser) TextAlign.End else TextAlign.Start,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }

            SectionCard(title = "Sugestões") {
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    sugestoes.forEach { sugestao ->
                        TagChip(text = sugestao, selected = false) {
                            mensagens.add(Mensagem(sugestao, isUser = true))
                        }
                    }
                }
            }

            SectionCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    InputField(
                        value = mensagemTexto,
                        onValueChange = { mensagemTexto = it },
                        label = "Digite sua mensagem",
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(Modifier.width(8.dp))
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
                Text(
                    text = "Toque em enviar para registrar sua pergunta.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }
    }
}
