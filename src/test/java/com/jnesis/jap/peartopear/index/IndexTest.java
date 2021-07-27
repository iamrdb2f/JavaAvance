package com.jnesis.jap.peartopear.index;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class IndexTest {

    @Test
    public void testIndexNonExistingFile(){
        File f=new File("/inexisting_file.txt");
        Index i = new Index();
        i.addToIndex(f);
        assertEquals("Index shouldn't contain this entry",0,i.size());
    }

    @Test
    public void testIndexValidFile(){
        File f=new File("C:\\temp\\movies.csv");
        Index i = new Index();
        i.addToIndex(f);
        assertEquals("Index doesn't contain added entry",1,i.size());
    }
}