package com.jnesis.jap.peartopear.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IncrementerThread extends Thread{

    private static final Logger LOG = LoggerFactory.getLogger(IncrementerThread.class);

    private final ConnectionCounter connectionCounter;

    public IncrementerThread(ConnectionCounter connectionCounter) {
        this.connectionCounter = connectionCounter;
    }

    @Override
    public void run() {
        for (int i=0;i<1000;i++) {
            connectionCounter.increment();
            LOG.debug(""+connectionCounter.getCounter());
        }
    }
}
