package com.banco;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ControladorUI {
    private final FilaClientes fila = new FilaClientes();
    private final List<Caixa> caixas = new ArrayList<>();
    private final VBox statusCaixas = new VBox(5);
    private final ListView<Cliente> listaClientes = new ListView<>();
    private final Label tempoEstimado = new Label("Tempo estimado: 0s");
    private final Label caixasAtivos = new Label("Caixas ativos: 0");
    private final Label clientesFila = new Label("Clientes na fila: 0");
    private final AtomicInteger clientesAtendidos = new AtomicInteger(0);

    private final Map<Integer, Label> statusPorCaixa = new HashMap<>();
    private int proximoCliente = 1;
    private int proximoCaixa = 1;

    public Pane criarLayout() {
        Button btnCliente = new Button("+ Adicionar Cliente");
        btnCliente.setOnAction(e -> adicionarCliente());

        Button btnAddCaixa = new Button("+ Adicionar Caixa");
        btnAddCaixa.setOnAction(e -> adicionarCaixa());

        Button btnRemCaixa = new Button("– Remover Caixa");
        btnRemCaixa.setOnAction(e -> removerCaixa());

        HBox botoes = new HBox(10, btnCliente, btnAddCaixa, btnRemCaixa);
        VBox info = new VBox(10, clientesFila, caixasAtivos, tempoEstimado);
        VBox direita = new VBox(10, statusCaixas, info);
        direita.setPadding(new Insets(10));

        HBox root = new HBox(20,
                new VBox(new Label("Clientes na fila"), listaClientes, botoes),
                direita
        );
        root.setPadding(new Insets(20));
        return root;
    }

    public void adicionarCliente() {
        Cliente cliente = new Cliente(proximoCliente++);
        fila.adicionar(cliente);
        listaClientes.getItems().add(cliente);
        atualizarInfos();
    }

    public void adicionarCaixa() {
        int id = proximoCaixa++;

        Label status = new Label("Caixa " + id + ": iniciando...");
        statusPorCaixa.put(id, status);
        statusCaixas.getChildren().add(status);

        Caixa caixa = new Caixa(id, fila, this);
        caixas.add(caixa);
        caixa.start();

        atualizarInfos();
    }

    public void removerCaixa() {
        if (!caixas.isEmpty()) {
            Caixa caixa = caixas.remove(caixas.size() - 1);
            caixa.desligar();

            long id = caixa.getId();

            Label status = statusPorCaixa.remove(id);
            if (status != null) {
                statusCaixas.getChildren().remove(status);
            }

            // ✅ Se todos os caixas foram removidos, reseta o contador
            if (caixas.isEmpty()) {
                proximoCaixa = 1;
            }

            atualizarInfos();
        }
    }

    public void atualizarStatusCaixa(int id, String status) {
        Platform.runLater(() -> {
            Label label = statusPorCaixa.get(id);
            if (label != null) {
                label.setText("Caixa " + id + " está " + status);
            }
        });
    }

    public void incrementarAtendidos() {
        Platform.runLater(() -> {
            clientesAtendidos.incrementAndGet();
            listaClientes.getItems().remove(0);
            atualizarInfos();
        });
    }

    public void atualizarInfos() {
        int filaSize = fila.tamanho();
        int caixasAtv = caixas.size();
        int estimado = caixasAtv == 0 ? 0 : (filaSize * 10) / caixasAtv;

        clientesFila.setText("Clientes na fila: " + filaSize);
        caixasAtivos.setText("Caixas ativos: " + caixasAtv);
        tempoEstimado.setText("Tempo estimado: " + estimado + " segundos");
    }
}
