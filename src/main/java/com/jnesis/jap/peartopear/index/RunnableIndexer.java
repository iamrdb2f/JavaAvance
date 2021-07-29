package com.jnesis.jap.peartopear.index;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@AutoIndexer(path={"C:","temp"},scope = Scope.ONE)
public class RunnableIndexer implements Runnable{

    private final String path;
    private final Index index;
    private final Scope scope;

    private List<Thread> threads=new ArrayList<>();

    private static final Logger LOG = LoggerFactory.getLogger(RunnableIndexer.class);

    public RunnableIndexer (String path, Index index, Scope scope) {
        this.path=path;
        this.index=index;
        this.scope=scope;
    }

    @Override
    public void run() {
        registerThread(Thread.currentThread());
        index(path, index, scope);
        displayThreadsState();
    }

    public void index(String path, Index index, Scope scope) {
        LOG.info("Indexing "+path+" in "+scope.name()+" mode");

        if (path == null || path.length() == 0 || index == null) {
            LOG.debug("Skipping {}", path);
            return;
        }

        File f = new File(path);
        if (f.exists() && f.isDirectory()) {
            LOG.debug("New Directory found {}", path);
            File[] childs = f.listFiles();
            for (int i = 0; i < childs.length; i++) {
                if (childs[i].isDirectory()){
                    switch (scope){
                        case ONE:
                            final RunnableIndexer riOne=new RunnableIndexer(childs[i].getPath(),index,Scope.BASE);
                            new Thread(riOne).start();
                            break;
                        case SUBTREE:
                            final RunnableIndexer riTree=new RunnableIndexer(childs[i].getPath(),index,Scope.SUBTREE);
                            new Thread(riTree).start();
                            break;
                    }
                }
                else {
                    System.out.println(childs[i].getName());
                    //index.addToIndex(childs[i]);
                }
            }
        }
    }

    public void registerThread(Thread th){
        threads.add(th);
    }

    public void displayThreadsState(){
        /* Ne pas utiliser d'iterator ou de forEach vous optiendrez une concurrentModificationException (voir plus loin).
        Sauf a copier la liste au prealable comme ci dessous
        List<Thread> copy = new ArrayList<>(threads);
        copy.forEach(th-> System.out.println(th.getName()+" "+th.getState()));*/
        for (int i=0;i<threads.size();i++){
            final Thread th=threads.get(i);
            System.out.println(th.getName()+" "+th.getState());
        }
    }
}
