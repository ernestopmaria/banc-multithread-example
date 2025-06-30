package com.banco;

import java.util.LinkedList;
import java.util.Queue;

public class FilaClientes {
    private final Queue<Cliente> fila =new LinkedList<>();

    public synchronized void adicionar(Cliente cliente){
        fila.add(cliente);
        notifyAll();
    }

    public synchronized Cliente proximo(){
        while(fila.isEmpty()){
            try{
                wait();
            }catch (InterruptedException e){
                return null;
            }
        }
        return fila.poll();
    }
    public synchronized int tamanho(){
        return fila.size();
    }
}
