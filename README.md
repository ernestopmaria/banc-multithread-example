# 🏦 Simulador Bancário Multithread em Java

Este é um projeto Java com JavaFX que simula o atendimento em um banco utilizando múltiplas threads.
Cada **caixa** é uma thread que atende clientes em uma **fila compartilhada**, com tempo fixo de atendimento de 10 segundos por cliente.

## 🚀 Funcionalidades

- Adicionar clientes à fila
- Adicionar e remover caixas dinamicamente
- Interface gráfica com JavaFX
- Tempo estimado de atendimento calculado em tempo real
- Threads controladas individualmente para simular concorrência

## 🧱 Tecnologias

- Java 17+
- JavaFX 21
- Maven
- Threads e sincronização com `synchronized` e `wait/notify`

## ▶️ Como executar

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/banco-multithread.git
   cd banco-multithread
