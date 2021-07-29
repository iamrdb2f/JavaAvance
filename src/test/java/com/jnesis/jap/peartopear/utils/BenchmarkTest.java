package com.jnesis.jap.peartopear.utils;

import com.jnesis.jap.peartopear.index.Index;
import com.jnesis.jap.peartopear.index.Indexer;
import com.jnesis.jap.peartopear.index.RunnableIndexer;
import com.jnesis.jap.peartopear.index.Scope;
import org.junit.Test;

import java.io.File;

public class BenchmarkTest {

    @Test
    public void testIntegration(){
        Benchmark b=new Benchmark();
        b.start();
        try {
            Thread.sleep(1000);
            b.bookmark("first");
            Thread.sleep(2000);
            b.bookmark("second");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        b.stop();

        System.out.println(b);

    }

    @Test
    public void testTime(){
        Benchmark b=new Benchmark();
        b.start();
        Index i=new Index();
        Indexer.index("C:\\apache-maven-3.6.3",i, Scope.SUBTREE);
        b.stop();
        System.out.println(b);
    }
}
