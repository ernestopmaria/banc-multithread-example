package com.banco;

public class Cliente {
    private final int id;

    public Cliente(int id){
        this.id= id;
    }

    public int getId(){
        return id;
    }

    @Override
    public String toString(){
        return "Client " + id;
    }
}
