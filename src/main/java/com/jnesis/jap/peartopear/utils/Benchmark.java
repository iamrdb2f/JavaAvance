package com.jnesis.jap.peartopear.utils;

import java.time.Instant;
import java.util.HashMap;
import java.util.TreeMap;

public class Benchmark {

    private Instant start;
    private Instant stop;

    private TreeMap<Long,String> bookmarks=new TreeMap<>();

    public void start(){
        this.start=Instant.now();
    }

    public void stop(){
        this.stop=Instant.now();
    }

    public void bookmark(String name){
        bookmarks.put(Instant.now().toEpochMilli()-start.toEpochMilli(),name);
    }

    @Override
    public String toString() {
        return "Benchmark{" +
                "start=" + start +
                ", stop=" + stop +
                ", bookmarks=" + bookmarks +
                '}';
    }
}
