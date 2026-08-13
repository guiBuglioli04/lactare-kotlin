package com.example.lactare.ui.screens

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.unit.dp
import com.example.lactare.model.Nutriz
import com.example.lactare.ui.components.InputField
import com.example.lactare.ui.components.PageScaffold
import com.example.lactare.ui.components.PrimaryButton
import com.example.lactare.ui.components.SecondaryButton
import com.example.lactare.ui.components.SectionCard
import com.example.lactare.ui.components.Stepper

@Composable
fun CadastroScreen(
    onGoBancos: () -> Unit,
    onGoDashboard: () -> Unit
) {
    var step by remember { mutableStateOf(1) }
    var nutriz by remember { mutableStateOf(Nutriz()) }
    val estados = listOf("SP", "RJ", "MG", "PR", "RS")
    var expandedEstado by remember { mutableStateOf(false) }

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

                    Box {
                        InputField(
                            value = nutriz.estado.ifBlank { "Selecionar" },
                            onValueChange = {},
                            label = "Estado",
                            readOnly = true,
                            modifier = Modifier.clickable { expandedEstado = true }
                        )

                        DropdownMenu(
                            expanded = expandedEstado,
                            onDismissRequest = { expandedEstado = false }
                        ) {
                            estados.forEach { uf ->
                                DropdownMenuItem(
                                    text = { Text(uf) },
                                    onClick = {
                                        nutriz = nutriz.copy(estado = uf)
                                        expandedEstado = false
                                    }
                                )
                            }
                        }
                    }

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
                        "Quero ser avisada quando minha contribuição for necessária"
                    )

                    opcoes.forEach { opcao ->
                        SectionCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .toggleable(
                                    value = nutriz.disponibilidade.contains(opcao),
                                    onValueChange = {
                                        val nova = nutriz.disponibilidade.toMutableSet()
                                        if (nova.contains(opcao)) nova.remove(opcao) else nova.add(opcao)
                                        nutriz = nutriz.copy(disponibilidade = nova)
                                    }
                                )
                        ) {
                            Text(opcao, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }

                4 -> SectionCard(
                    title = "Consentimento",
                    subtitle = "Confirme os termos para finalizar"
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = nutriz.consentimento,
                            onCheckedChange = { nutriz = nutriz.copy(consentimento = it) }
                        )
                        Text(
                            text = "Concordo com os termos e autorizo uso dos dados para contato.",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(start = 6.dp)
                        )
                    }

                    Text(
                        text = "Resumo",
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(
                        text = "${nutriz.nomeCompleto.ifBlank { "Nome não informado" }} • ${nutriz.cidade.ifBlank { "Cidade" }}/${nutriz.estado.ifBlank { "UF" }}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
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
                    onClick = { if (step < 4) step++ else onGoBancos() },
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
