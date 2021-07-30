package com.jnesis.jap.peartopear.index;

import com.jnesis.jap.peartopear.utils.Benchmark;

public class RunnableIndexerTest {

    public static void main(String... args){
        Index i=new Index();
        RunnableIndexer ri=new RunnableIndexer("C:\\WORK-SRC",i,Scope.SUBTREE);
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
        ri.displayThreadsState();
        System.out.println(b.toString());
        System.out.println("Taille de l'index "+i.size());
    }

}
