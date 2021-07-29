package com.jnesis.jap.peartopear.connection;

public class ConnectionCounterTest {

    public static void main(String... args){
        ConnectionCounter counter=new ConnectionCounter();
        DecrementerThread thd=new DecrementerThread(counter);
        IncrementerThread thi=new IncrementerThread(counter);
        thd.start();
        thi.start();
    }

}