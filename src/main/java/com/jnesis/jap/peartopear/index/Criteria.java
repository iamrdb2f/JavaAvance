package com.jnesis.jap.peartopear.index;

public interface Criteria {

    /**
     * @param filename a file name
     * @return <code>true</code> if filename matches the required criteria
     */
    public boolean matches(String filename);

}

