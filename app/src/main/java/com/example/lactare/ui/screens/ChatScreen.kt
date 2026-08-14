package com.example.lactare.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.lactare.ui.components.InputField
import com.example.lactare.ui.components.PageScaffold
import com.example.lactare.ui.components.SectionCard
import com.example.lactare.ui.components.TagChip
import com.example.lactare.ui.theme.AccentBlue
import com.example.lactare.ui.theme.DarkSurface
import com.example.lactare.ui.theme.DarkSurfaceElevated
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private data class Mensagem(val texto: String, val isUser: Boolean)

private val respostasBot = mapOf(
    "como doar" to "Para doar leite materno, você deve:\n\n1️⃣ Estar amamentando\n2️⃣ Não fumar ou ingerir bebidas alcoólicas\n3️⃣ Ordenhar o leite em casa com higiene\n4️⃣ Armazenar em pote esterilizado e congelar\n5️⃣ Entrar em contato com o banco mais próximo para coleta ou entrega 💙",
    "bancos de leite" to "Existem mais de 200 bancos de leite humano no Brasil! Na aba **Bancos** você encontra os mais próximos de você com endereço, horário e telefone. Todos são credenciados pela Rede BLH-BR 🏥",
    "horários de coleta" to "Os horários variam por unidade, mas em geral os bancos funcionam:\n• Seg-Sex: 7h às 17h\n• Sábado: 8h às 12h\n\nAlgumas unidades também fazem coleta domiciliar! Consulte o banco mais próximo na aba **Bancos** 📅"
)

private fun gerarResposta(mensagem: String): String {
    val lower = mensagem.lowercase()
    return when {
        respostasBot.keys.any { lower.contains(it) } ->
            respostasBot.entries.first { lower.contains(it.key) }.value
        lower.contains("obrigad") ->
            "De nada! Fico feliz em ajudar 💙 Se tiver mais dúvidas sobre doação de leite, estou aqui!"
        lower.contains("ajuda") || lower.contains("preciso") ->
            "Claro! Posso te ajudar com informações sobre **como doar**, **bancos de leite próximos** ou **horários de coleta**. O que você precisa saber? 😊"
        lower.contains("olá") || lower.contains("oi") || lower.contains("bom dia") || lower.contains("boa tarde") ->
            "Olá! Que ótimo te ver por aqui 💙 Em que posso ajudar você hoje?"
        else ->
            "Entendi! Posso te ajudar com informações sobre doação de leite humano. Tente perguntar sobre:\n\n• Como doar\n• Bancos de leite\n• Horários de coleta\n\nOu use as sugestões abaixo! 😊"
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChatScreen(
    onBack: () -> Unit = {}
) {
    var mensagemTexto by remember { mutableStateOf("") }
    val mensagens = remember {
        mutableStateListOf(
            Mensagem(
                "Olá! Sou a assistente Lactare 💙 Estou aqui para ajudar você com informações sobre doação, coleta e bancos próximos. Como posso te ajudar hoje?",
                isUser = false
            )
        )
    }
    var isTyping by remember { mutableStateOf(false) }
    val sugestoes = listOf("Como doar", "Bancos de leite", "Horários de coleta")
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    fun enviar(texto: String) {
        if (texto.isBlank()) return
        mensagens.add(Mensagem(texto, isUser = true))
        isTyping = true
        scope.launch {
            listState.animateScrollToItem(mensagens.size - 1)
            delay(1200)
            mensagens.add(Mensagem(gerarResposta(texto), isUser = false))
            isTyping = false
            listState.animateScrollToItem(mensagens.size - 1)
        }
    }

    LaunchedEffect(mensagens.size) {
        if (mensagens.isNotEmpty()) {
            listState.animateScrollToItem(mensagens.size - 1)
        }
    }

    PageScaffold(
        title = "Lactare Connect",
        subtitle = "Atendimento digital com resposta rápida",
        onBack = onBack
    ) { innerModifier ->
        Column(
            modifier = innerModifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            LazyColumn(
                state = listState,
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
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.82f)
                                .clip(
                                    RoundedCornerShape(
                                        topStart = 16.dp,
                                        topEnd = 16.dp,
                                        bottomStart = if (msg.isUser) 16.dp else 4.dp,
                                        bottomEnd = if (msg.isUser) 4.dp else 16.dp
                                    )
                                )
                                .background(
                                    if (msg.isUser) AccentBlue.copy(alpha = 0.18f)
                                    else DarkSurfaceElevated
                                )
                                .padding(horizontal = 14.dp, vertical = 10.dp)
                        ) {
                            Text(
                                text = msg.texto,
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (msg.isUser) MaterialTheme.colorScheme.onSurface
                                else MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = if (msg.isUser) TextAlign.End else TextAlign.Start,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }

                if (isTyping) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomEnd = 16.dp, bottomStart = 4.dp))
                                    .background(DarkSurface)
                                    .padding(horizontal = 14.dp, vertical = 10.dp)
                            ) {
                                Text(
                                    "Digitando...",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }

            SectionCard(title = "Sugestões rápidas") {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    sugestoes.forEach { sugestao ->
                        TagChip(text = sugestao, selected = false) {
                            mensagemTexto = ""
                            enviar(sugestao)
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
                            val texto = mensagemTexto
                            mensagemTexto = ""
                            enviar(texto)
                        },
                        enabled = mensagemTexto.isNotBlank()
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Enviar",
                            tint = if (mensagemTexto.isNotBlank()) AccentBlue
                            else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                Text(
                    text = "Toque em enviar ou escolha uma sugestão.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }
    }
}
