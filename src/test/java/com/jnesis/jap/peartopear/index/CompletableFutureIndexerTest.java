package com.jnesis.jap.peartopear.index;

import com.jnesis.jap.peartopear.utils.Benchmark;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class CompletableFutureIndexerTest {

    public static void main(String... args){
        Index i=new Index();
        CompletableFutureIndexer ri=new CompletableFutureIndexer("C:\\Program Files",i,Scope.SUBTREE);
        Thread th=new Thread(ri);

        Benchmark b=new Benchmark();
        b.start();

        th.start();
        try {
            th.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        b.stop();
        System.out.println("Les threads ont compté "+CompletableFutureIndexer.adder.sum());
        System.out.println(b.toString());
        System.out.println("Taille de l'index "+i.size());
    }

}
