package com.jnesis.jap.peartopear.index;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
                    Optional<Scope> nextScope= Optional.empty();
                    switch (scope){
                        case ONE:
                            nextScope=Optional.of(Scope.BASE);
                            break;
                        case SUBTREE:
                            nextScope=Optional.of(Scope.SUBTREE);
                            break;
                    }
                    if (nextScope.isPresent()){
                        final RunnableIndexer ri=new RunnableIndexer(childs[i].getPath(),index,nextScope.get());
                        Thread th=new Thread(ri);
                        registerThread(th);
                        th.start();
                        try {
                            th.join();
                        }
                        catch (InterruptedException ie){
                            ie.printStackTrace();
                        }
                    }
                }
                else {
                    //System.out.println(childs[i].getName());
                    index.addToIndex(childs[i]);
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
        System.out.println("--DISPLAY THREADS");
        for (int i=0;i<threads.size();i++){
            final Thread th=threads.get(i);
            System.out.println(th.getName()+" "+th.getState());
        }
        System.out.println("--END DISPLAY THREADS");
    }
}
