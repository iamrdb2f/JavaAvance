package com.jnesis.jap.peartopear.index;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.concurrent.atomic.LongAdder;

public class CompletableFutureIndexer implements Runnable {

    private final String path;
    private final Index index;
    private final Scope scope;

    private static final Logger LOG = LoggerFactory.getLogger(CompletableFutureIndexer.class);

    public static LongAdder adder=new LongAdder();

    private List<CompletableFuture<Void>> cfs=new ArrayList<>();

    public CompletableFutureIndexer(String path, Index index, Scope scope) {
        this.path=path;
        this.index=index;
        this.scope=scope;
    }

    @Override
    public void run() {
        index(path, index, scope);
    }

    public void index(String path, Index index, Scope scope) {
        LOG.info("Indexing "+path+" in "+scope.name()+" mode");

        if (path == null || path.length() == 0 || index == null) {
            LOG.debug("Skipping {}", path);
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
                        final CompletableFutureIndexer ri=new CompletableFutureIndexer(childs[i].getPath(),index,nextScope.get());
                        CompletableFuture<Void> future=CompletableFuture.runAsync(ri);
                        cfs.add(future);
                    }
                }
                else {
                    //System.out.println(childs[i].getName());
                    adder.increment();
                    index.addToIndex(childs[i]);
                }
            }
        }

        CompletableFuture<Void> allTasks=CompletableFuture.allOf(cfs.toArray(new CompletableFuture[cfs.size()]));
        try {
            allTasks.get();
            System.out.println("Finished "+Thread.currentThread().getName());
        } catch (InterruptedException e) {
            allTasks.cancel(false);
        } catch (ExecutionException e) {
            allTasks.cancel(false);
        }
    }

}
