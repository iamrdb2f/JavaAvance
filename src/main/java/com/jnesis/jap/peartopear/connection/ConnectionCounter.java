package com.jnesis.jap.peartopear.connection;

public class ConnectionCounter {

    private int counter=0;

    public void increment(){
        counter++;
    }

    public void decrement(){
        counter--;
    }

    public int getCounter(){
        return counter;
    }
}
