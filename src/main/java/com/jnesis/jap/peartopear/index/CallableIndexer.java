package com.jnesis.jap.peartopear.index;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Optional;
import java.util.concurrent.*;

public class CallableIndexer implements Callable<Integer> {

    private final String path;
    private final Index index;
    private final Scope scope;

    private static final Logger LOG = LoggerFactory.getLogger(CallableIndexer.class);

    public static ExecutorService service= Executors.newCachedThreadPool();

    public CallableIndexer(String path, Index index, Scope scope) {
        this.path=path;
        this.index=index;
        this.scope=scope;
    }

    @Override
    public Integer call() throws Exception {
        return index(path, index, scope);
    }

    public int index(String path, Index index, Scope scope) {
        LOG.info("Indexing "+path+" in "+scope.name()+" mode");

        if (path == null || path.length() == 0 || index == null) {
            LOG.debug("Skipping {}", path);
            return 0;
        }

        int addFilesCount=0;

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
                        final CallableIndexer ri=new CallableIndexer(childs[i].getPath(),index,nextScope.get());
                        Future<Integer> future=service.submit(ri);
                        try {
                            addFilesCount+= future.get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else {
                    //System.out.println(childs[i].getName());
                    addFilesCount++;
                    index.addToIndex(childs[i]);
                }
            }
        }

        return addFilesCount;
    }

}
