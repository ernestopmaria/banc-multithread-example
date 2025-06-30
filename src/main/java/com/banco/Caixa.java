package com.banco;

public class Caixa extends Thread{
    private final int id;
    private final FilaClientes fila;
    private final ControladorUI ui;
    private boolean ativo = true;

    public Caixa (int id , FilaClientes fila, ControladorUI ui){
        this.id = id;
        this.fila = fila;
        this.ui = ui;
    }


    public void desligar(){
        ativo = false;
        interrupt();
    }

    @Override
    public void run(){
        while(ativo){
            Cliente cliente = fila.proximo();
            if(cliente!=null){
                ui.atualizarStatusCaixa(id, "Atendendo " + cliente);
                try{
                    Thread.sleep(10_000);
                }catch (InterruptedException e){
                    break;
                }
                ui.atualizarStatusCaixa(id, "disponivel ");
                ui.incrementarAtendidos();
            }
        }
        ui.atualizarStatusCaixa(id, "desligado ");
    }

}
