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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.lactare.model.Nutriz
import com.example.lactare.ui.components.DropdownField
import com.example.lactare.ui.components.InputField
import com.example.lactare.ui.components.PageScaffold
import com.example.lactare.ui.components.PrimaryButton
import com.example.lactare.ui.components.SecondaryButton
import com.example.lactare.ui.components.SectionCard
import com.example.lactare.ui.components.Stepper
import com.example.lactare.ui.theme.AccentBlue
import com.example.lactare.ui.theme.BorderSubtle

@Composable
fun CadastroScreen(
    onGoBancos: () -> Unit,
    onGoDashboard: () -> Unit
) {
    var step by remember { mutableStateOf(1) }
    var nutriz by remember { mutableStateOf(Nutriz()) }

    val estados = listOf(
        "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO",
        "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI",
        "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"
    )

    PageScaffold(
        title = "Cadastro de doadora",
        subtitle = "Fluxo em 4 etapas para concluir o registro"
    ) { innerModifier ->
        Column(
            modifier = innerModifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            SectionCard {
                Stepper(currentStep = step)
            }

            when (step) {
                1 -> SectionCard(
                    title = "Dados pessoais",
                    subtitle = "Conte um pouco sobre você"
                ) {
                    InputField(
                        value = nutriz.nomeCompleto,
                        onValueChange = { nutriz = nutriz.copy(nomeCompleto = it) },
                        label = "Nome completo"
                    )
                    InputField(
                        value = nutriz.email,
                        onValueChange = { nutriz = nutriz.copy(email = it) },
                        label = "E-mail"
                    )
                    InputField(
                        value = nutriz.telefone,
                        onValueChange = { nutriz = nutriz.copy(telefone = it) },
                        label = "Telefone",
                        supportingText = "Inclua DDD para facilitar contato"
                    )
                }

                2 -> SectionCard(
                    title = "Localização",
                    subtitle = "Esses dados ajudam a conectar você ao banco mais próximo"
                ) {
                    InputField(
                        value = nutriz.cep,
                        onValueChange = { nutriz = nutriz.copy(cep = it) },
                        label = "CEP"
                    )

                    DropdownField(
                        value = nutriz.estado.ifBlank { "Selecionar estado" },
                        label = "Estado",
                        options = estados,
                        onSelect = { uf -> nutriz = nutriz.copy(estado = uf) },
                        optionLabel = { it }
                    )

                    InputField(
                        value = nutriz.cidade,
                        onValueChange = { nutriz = nutriz.copy(cidade = it) },
                        label = "Cidade"
                    )
                    InputField(
                        value = nutriz.bairro,
                        onValueChange = { nutriz = nutriz.copy(bairro = it) },
                        label = "Bairro"
                    )
                }

                3 -> SectionCard(
                    title = "Preferências de contribuição",
                    subtitle = "Selecione uma ou mais opções"
                ) {
                    val opcoes = listOf(
                        "Tenho leite excedente para doar agora",
                        "Quero ser avisada quando minha contribuição for necessária",
                        "Posso receber coleta domiciliar",
                        "Prefiro levar pessoalmente ao banco"
                    )

                    opcoes.forEach { opcao ->
                        val selected = nutriz.disponibilidade.contains(opcao)
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(
                                    if (selected) AccentBlue.copy(alpha = 0.12f)
                                    else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                                )
                                .toggleable(
                                    value = selected,
                                    onValueChange = {
                                        val nova = nutriz.disponibilidade.toMutableSet()
                                        if (nova.contains(opcao)) nova.remove(opcao) else nova.add(opcao)
                                        nutriz = nutriz.copy(disponibilidade = nova)
                                    }
                                )
                                .padding(horizontal = 16.dp, vertical = 14.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                // Caixa de seleção customizada
                                Box(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(if (selected) AccentBlue else BorderSubtle),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (selected) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.background,
                                            modifier = Modifier.size(14.dp)
                                        )
                                    }
                                }
                                Text(
                                    opcao,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = if (selected) MaterialTheme.colorScheme.onSurface
                                    else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }

                4 -> SectionCard(
                    title = "Consentimento",
                    subtitle = "Confirme os termos para finalizar"
                ) {
                    // Card de resumo
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                            .padding(16.dp)
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = "Resumo do cadastro",
                                style = MaterialTheme.typography.labelLarge,
                                color = AccentBlue
                            )
                            Text(
                                text = nutriz.nomeCompleto.ifBlank { "Nome não informado" },
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = buildString {
                                    append(nutriz.cidade.ifBlank { "Cidade" })
                                    append("/")
                                    append(nutriz.estado.ifBlank { "UF" })
                                    if (nutriz.email.isNotBlank()) append(" • ${nutriz.email}")
                                },
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            if (nutriz.disponibilidade.isNotEmpty()) {
                                Text(
                                    text = "${nutriz.disponibilidade.size} preferência(s) selecionada(s)",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(4.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (nutriz.consentimento) AccentBlue.copy(alpha = 0.1f)
                                else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                            )
                            .toggleable(
                                value = nutriz.consentimento,
                                onValueChange = { nutriz = nutriz.copy(consentimento = it) }
                            )
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = nutriz.consentimento,
                                onCheckedChange = { nutriz = nutriz.copy(consentimento = it) },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = AccentBlue,
                                    uncheckedColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    checkmarkColor = MaterialTheme.colorScheme.background
                                )
                            )
                            Text(
                                text = "Concordo com os termos e autorizo o uso dos meus dados para contato relacionado à doação de leite humano.",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(start = 6.dp)
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SecondaryButton(
                    text = "Voltar",
                    onClick = { if (step > 1) step-- },
                    enabled = step > 1,
                    modifier = Modifier.weight(1f)
                )
                PrimaryButton(
                    text = if (step < 4) "Próximo" else "Finalizar cadastro",
                    onClick = {
                        if (step < 4) step++
                        else if (nutriz.consentimento) onGoBancos()
                    },
                    enabled = if (step == 4) nutriz.consentimento else true,
                    modifier = Modifier.weight(1f)
                )
            }

            SecondaryButton(
                text = "Ir para área admin (demo)",
                onClick = onGoDashboard,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
