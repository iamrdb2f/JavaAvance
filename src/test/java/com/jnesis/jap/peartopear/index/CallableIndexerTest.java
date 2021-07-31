package com.jnesis.jap.peartopear.index;

import com.jnesis.jap.peartopear.utils.Benchmark;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class CallableIndexerTest {

    public static void main(String... args){

        Index i=new Index();
        final CallableIndexer ri=new CallableIndexer("C:\\apache-maven-3.6.3\\lib",i,Scope.SUBTREE);

        Benchmark b=new Benchmark();

        b.start();

        Future<Integer> future=CallableIndexer.service.submit(ri);
        try {
            int filesCounted=future.get();
            System.out.println("Les threads ont compté "+filesCounted);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        CallableIndexer.service.shutdown();

        b.stop();

        System.out.println(b.toString());
        System.out.println("Taille de l'index "+i.size());
    }

}
