package com.jnesis.jap.peartopear.index;

public class RunnableIndexerTest {

    public static void main(String... args){
        new Thread(new RunnableIndexer("C:\\apache-maven-3.6.3",new Index(),Scope.SUBTREE)).start();
    }

}
