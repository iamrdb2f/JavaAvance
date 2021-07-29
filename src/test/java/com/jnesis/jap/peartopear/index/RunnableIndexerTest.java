package com.jnesis.jap.peartopear.index;

public class RunnableIndexerTest {

    public static void main(String... args){
        Index i=new Index();
        new Thread(new RunnableIndexer("C:\\apache-maven-3.6.3",i,Scope.SUBTREE)).start();
        i.displayThreadsState();
    }

}
