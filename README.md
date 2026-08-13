# Lactare - Banco de Leite (MVP)

Aplicativo Android MVP construído com **Kotlin + Jetpack Compose**, com dados totalmente mockados e fortemente tipados.

## Funcionalidades

- Cadastro em 4 etapas com stepper
- Consulta de bancos de leite (lista + mapa simulado)
- Chatbot Lactare Connect com respostas rápidas
- Dashboard administrativo com métricas e campanhas

## Estrutura de pacotes

```text
com.example.lactare/
├── data/
│   └── mock/
├── model/
├── ui/
│   ├── components/
│   ├── screens/
│   └── theme/
└── navigation/
```

## Como executar

1. Abra no Android Studio (Giraffe+ recomendado).
2. Aguarde o Gradle Sync.
3. Rode em um emulador/dispositivo Android.

## Observações

- Sem integração real com APIs/Firebase.
- Dados 100% mockados para demonstração do fluxo.
