package com.jnesis.jap.peartopear.utils;

public class Pair<T1 extends Comparable,T2 extends Comparable> implements Comparable{

    private final T1 one;
    private final T2 two;

    private Pair(T1 one, T2 two) {
        this.one=one;
        this.two=two;
    }

    public static <T1 extends Comparable,T2 extends Comparable> Pair<T1,T2> buildPair(T1 one, T2 two){
        return new Pair<>(one,two);
    }

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof Pair)) throw new IllegalArgumentException("No a valid comparison");
        Pair po=(Pair)o;
        int c=one.compareTo(po.one);
        if (c==0){
            return two.compareTo(po.two);
        }
        else return c;
    }
}
