package com.jnesis.jap.peartopear.index;

import org.junit.Test;

import static org.junit.Assert.*;

public class IndexerTest {

    @Test
    public void testIndex(){
        Index i = new Index();
        Indexer.index("C:\\temp",i);
        assertEquals("Index should contain 4 entries",4,i.size());
    }

}