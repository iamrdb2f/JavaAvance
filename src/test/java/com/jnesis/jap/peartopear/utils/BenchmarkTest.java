package com.jnesis.jap.peartopear.utils;

import org.junit.Test;

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

}
