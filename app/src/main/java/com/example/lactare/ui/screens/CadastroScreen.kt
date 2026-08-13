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
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lactare.model.Nutriz
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

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(20.dp)) {
            Stepper(currentStep = step)
            Spacer(Modifier.height(16.dp))

            when (step) {
                1 -> {
                    Text("Vamos começar! 💙", style = MaterialTheme.typography.headlineSmall)
                    Text("Conte um pouco sobre você")
                    Spacer(Modifier.height(12.dp))

                    OutlinedTextField(
                        value = nutriz.nomeCompleto,
                        onValueChange = { nutriz = nutriz.copy(nomeCompleto = it) },
                        label = { Text("Nome completo") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(8.dp))

                    OutlinedTextField(
                        value = nutriz.email,
                        onValueChange = { nutriz = nutriz.copy(email = it) },
                        label = { Text("E-mail") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(8.dp))

                    OutlinedTextField(
                        value = nutriz.telefone,
                        onValueChange = { nutriz = nutriz.copy(telefone = it) },
                        label = { Text("Telefone") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                2 -> {
                    Text("Onde você está?", style = MaterialTheme.typography.headlineSmall)
                    Text("Precisamos saber sua localização para te ajudar melhor")
                    Spacer(Modifier.height(12.dp))

                    OutlinedTextField(
                        value = nutriz.cep,
                        onValueChange = { nutriz = nutriz.copy(cep = it) },
                        label = { Text("CEP") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(8.dp))

                    Box {
                        OutlinedTextField(
                            value = nutriz.estado,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Estado") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { expandedEstado = true }
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

                    Spacer(Modifier.height(8.dp))

                    OutlinedTextField(
                        value = nutriz.cidade,
                        onValueChange = { nutriz = nutriz.copy(cidade = it) },
                        label = { Text("Cidade") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(8.dp))

                    OutlinedTextField(
                        value = nutriz.bairro,
                        onValueChange = { nutriz = nutriz.copy(bairro = it) },
                        label = { Text("Bairro") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                3 -> {
                    Text("Como você prefere contribuir?", style = MaterialTheme.typography.headlineSmall)
                    Text("Selecione uma ou mais opções")
                    Spacer(Modifier.height(12.dp))

                    val opcoes = listOf(
                        "Tenho leite excedente para doar agora",
                        "Quero ser avisada quando minha contribuição for necessária"
                    )

                    opcoes.forEach { opcao ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                                .toggleable(
                                    value = nutriz.disponibilidade.contains(opcao),
                                    onValueChange = {
                                        val nova = nutriz.disponibilidade.toMutableSet()
                                        if (nova.contains(opcao)) nova.remove(opcao) else nova.add(opcao)
                                        nutriz = nutriz.copy(disponibilidade = nova)
                                    }
                                )
                        ) {
                            Text(opcao, modifier = Modifier.padding(16.dp))
                        }
                    }
                }

                4 -> {
                    Text("Consentimento e Finalização", style = MaterialTheme.typography.headlineSmall)
                    Spacer(Modifier.height(12.dp))

                    Row {
                        Checkbox(
                            checked = nutriz.consentimento,
                            onCheckedChange = { nutriz = nutriz.copy(consentimento = it) }
                        )
                        Text("Concordo com os termos e autorizo uso dos dados para contato.")
                    }

                    Spacer(Modifier.height(12.dp))
                    Text("Resumo: ${nutriz.nomeCompleto} • ${nutriz.cidade}/${nutriz.estado}")
                }
            }

            Spacer(Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = { if (step > 1) step-- },
                    enabled = step > 1
                ) {
                    Text("Voltar")
                }

                Button(
                    onClick = {
                        if (step < 4) step++ else onGoBancos()
                    }
                ) {
                    Text(if (step < 4) "Próximo" else "Finalizar cadastro")
                }
            }

            TextButton(
                onClick = onGoDashboard,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ir para área admin (demo)")
            }
        }
    }
}