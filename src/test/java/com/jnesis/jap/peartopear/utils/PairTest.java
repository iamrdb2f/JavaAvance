package com.jnesis.jap.peartopear.utils;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PairTest {

    @Test
    public void testPairOfGreaterT1Integers(){
        Pair<Integer,Integer> ints=Pair.buildPair(4,4);
        Pair<Integer,Integer> ints2=Pair.buildPair(3,8);
        assertThat(ints.compareTo(ints2),greaterThan(0));
    }

    @Test
    public void testPairOfEqualsT1Integers(){
        Pair<Integer,Integer> ints=Pair.buildPair(3,2);
        Pair<Integer,Integer> ints2=Pair.buildPair(3,4);
        assertThat(ints.compareTo(ints2),lessThan(0));
    }

    @Test
    public void testPairOfGreaterT1Strings(){
        Pair<String,Integer> ints=Pair.buildPair("ab",4);
        Pair<String,Integer> ints2=Pair.buildPair("cd",2);
        assertThat(ints.compareTo(ints2),lessThan(0));
    }

}