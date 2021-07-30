package com.jnesis.jap.peartopear.connection;

import com.jnesis.jap.peartopear.utils.Benchmark;

public class ConnectionCounterTest {

    public static void main(String... args){
        ConnectionCounter counter=new ConnectionCounter();
        DecrementerThread thd=new DecrementerThread(counter);
        IncrementerThread thi=new IncrementerThread(counter);
        Benchmark b=new Benchmark();
        b.start();
        thd.start();
        thi.start();
        try {
            thd.join();
            thi.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        b.stop();
        System.out.println(b);
        for (String message: counter.messages){
            System.out.println(message);
        }
    }

}