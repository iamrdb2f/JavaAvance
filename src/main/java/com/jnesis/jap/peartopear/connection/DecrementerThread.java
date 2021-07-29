package com.jnesis.jap.peartopear.connection;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DecrementerThread extends Thread{

    private static final Logger LOG = LoggerFactory.getLogger(DecrementerThread.class);

    private final ConnectionCounter connectionCounter;

    public DecrementerThread(ConnectionCounter connectionCounter) {
        this.connectionCounter = connectionCounter;
    }

    @Override
    public void run() {
        for (int i=0;i<1000;i++) {
            connectionCounter.decrement();
            LOG.debug(""+connectionCounter.getCounter());
        }
    }
}
