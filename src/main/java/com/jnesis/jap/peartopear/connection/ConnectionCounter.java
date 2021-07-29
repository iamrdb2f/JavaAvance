package com.jnesis.jap.peartopear.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionCounter {

    private static final Logger LOG = LoggerFactory.getLogger(ConnectionCounter.class);

    private int counter;

    public void increment(){
        counter++;
        LOG.debug("Incremented new Value is "+counter);
    }

    public void decrement(){
        counter--;
        LOG.debug("Decremented new Value is "+counter);
    }
}
