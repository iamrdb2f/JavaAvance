package com.jnesis.jap.peartopear.index;

import org.junit.Test;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class IndexTest {

    @Test
    public void testIndexNonExistingFile(){
        File f=new File("/inexisting_file.txt");
        Index i = new Index();
        i.addToIndex(f);
        assertThat(i.size(), is(0));
    }

    @Test
    public void testIndexValidFile(){
        File f=new File("C:\\temp\\movies.csv");
        Index i = new Index();
        i.addToIndex(f);
        assertThat(i.size(), is(1));
    }
}