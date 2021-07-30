package com.jnesis.jap.peartopear.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionCounter {

    private static final Logger LOG = LoggerFactory.getLogger(ConnectionCounter.class);

    private final AtomicInteger counter = new AtomicInteger(0);

    public List<String> messages=new ArrayList<>();

    public int getValue() {
        return counter.get();
    }

    public void increment(){
        //La synchronization d'une file de message ici est juste necessaire pour que l'affichage a la fin
        //se fasse dans l'ordre, mais forcement cela ralentit u tout petit peu le traitement, apres votre test
        //vous pouvez l'enlever
        synchronized (messages) {
            int newValue = counter.incrementAndGet();
            messages.add("increment new Value is " + newValue);
        }
    }

    public void decrement(){
        //La synchronization d'une file de message ici est juste necessaire pour que l'affichage a la fin
        //se fasse dans l'ordre, mais forcement cela ralentit u tout petit peu le traitement, apres votre test
        //vous pouvez l'enlever
        synchronized (messages) {
            int newValue = counter.decrementAndGet();
            messages.add("decrement new Value is " + newValue);
        }
    }

    /*

    With compareAndSet instead of incrementAndGet / incrementAndSet

    public void increment() {
        while(true) {
            int existingValue = getValue();
            int newValue = existingValue + 1;
            if (update(existingValue,newValue,"increment")){
                break;
            }
        }
    }

    public void decrement() {
        while(true) {
            int existingValue = getValue();
            int newValue = existingValue - 1;
            if (update(existingValue,newValue,"decrement")){
                break;
            }
        }
    }

    public boolean update(int existingValue, int newValue, String action){
        //La synchronization d'une file de message ici est juste necessaire pour que l'affichage a la fin
        //se fasse dans l'ordre, mais forcement cela ralentit u tout petit peu le traitement, apres votre test
        //vous pouvez l'enlever
        synchronized (messages) {
            if (counter.compareAndSet(existingValue, newValue)) {
                messages.add(action+" new Value is " + newValue);
                return true;
            }
            else{
                messages.add(action+" attempt SKIPPED " + newValue);
                return false;
            }
        }
    }*/
}
