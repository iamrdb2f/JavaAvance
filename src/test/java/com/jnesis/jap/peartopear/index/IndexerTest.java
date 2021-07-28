package com.jnesis.jap.peartopear.index;

import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.*;

public class IndexerTest {

    @Test
    public void testIndex(){
        Index i = new Index();
        Indexer.index("C:\\temp",i);
        //Adjust depending on your context
        assertEquals("Index should contain 4 entries",4,i.size());
    }

    @Test
    public void testCriteria(){
        Index i = new Index();
        Indexer.index("C:\\temp",i);
        Collection<Index.IndexEntry> c=i.find(new Criteria(){
            @Override
            public boolean matches(String filename) {
                return filename.startsWith("m");
            }
        });
        //Adjust depending on your context
        c.stream().forEach(e -> System.out.println(e));
        assertTrue(c.stream().anyMatch(entry -> entry.name.equals("movies.csv")));
        assertTrue(c.stream().anyMatch(entry -> entry.name.equals("movies2.csv")));
    }

}