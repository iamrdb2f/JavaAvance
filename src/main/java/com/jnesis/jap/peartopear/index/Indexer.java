package com.jnesis.jap.peartopear.index;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

@AutoIndexer(path={"C:","temp"},scope = Scope.ONE)
public class Indexer {

    private static final Logger LOG = LoggerFactory.getLogger(Indexer.class);

    public static void index(String path, Index index, Scope scope) {
        LOG.info("Indexing in "+scope.name()+" mode");

        if (path == null || path.length() == 0 || index == null) {
            LOG.debug("Skipping {}", path);
            return;
        }

        File f = new File(path);
        if (f.exists() && f.isDirectory()) {
            LOG.debug("Indexing {}", path);
            File[] childs = f.listFiles();
            for (int i = 0; i < childs.length; i++) {
                if (childs[i].isDirectory()){
                    switch (scope){
                        case ONE:
                            index(childs[i].getPath(), index, Scope.BASE);
                            break;
                        case SUBTREE:
                            index(childs[i].getPath(), index, Scope.SUBTREE);
                            break;
                    }
                }
                else {
                    index.addToIndex(childs[i]);
                }
            }
        }
    }



}
