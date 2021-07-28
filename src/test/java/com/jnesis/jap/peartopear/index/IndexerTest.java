package com.jnesis.jap.peartopear.index;

import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class IndexerTest {

    @Test
    public void testIndex(){
        Index i = new Index();
        Indexer.index("C:\\temp",i);
        //Adjust depending on your context
        assertThat(i.size(), is(4));
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
        assertThat(c.stream().anyMatch(entry -> entry.name.equals("movies.csv")), is(true));
        assertThat(c.stream().anyMatch(entry -> entry.name.equals("movies2.csv")), is(true));
    }

}