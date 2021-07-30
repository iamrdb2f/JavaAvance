package com.jnesis.jap.peartopear.index;

import com.jnesis.jap.peartopear.utils.PathUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class Index {

    private static final Logger LOG = LoggerFactory.getLogger(Index.class);

    private ConcurrentHashMap<String,IndexEntry> indexMap=new ConcurrentHashMap<>();

    public void addToIndex(File f){
        LOG.info("Adding file "+f.getName());
        if (!f.exists()){
            LOG.warn("File doesn't exist");
            return;
        }
        IndexEntry newEntry=indexMap.compute(f.getName(), new BiFunction<String, IndexEntry, IndexEntry>() {
            @Override
            public IndexEntry apply(String s, IndexEntry indexEntry) {
                //Impossible que indexEntry existe deja etant donne que chaque thread s'applique a un rep dedie
                return IndexEntry.createIndexEntry(f);
            }
        });

        LOG.info("IndexEntry "+newEntry+" has been added");

    }

    public int size(){
        return indexMap.size();
    }

    /**
     * @param c a search criteria
     * @return the index entries matching the given criteria
     */
    public Collection<IndexEntry> find(Criteria c){
        return indexMap.values().stream().filter(v -> c.matches(v.name)).collect(Collectors.toList());
    }

    public void autoFill(){
        Reflections reflections = new Reflections("com.jnesis.jap.peartopear.index");
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(AutoIndexer.class);
        for (Class<?> clazz : annotated) {
            try {
                Method m = clazz.getDeclaredMethod("index", new Class[]{String.class, Index.class, Scope.class});
                AutoIndexer a=clazz.getAnnotation(AutoIndexer.class);
                m.invoke(clazz,PathUtils.combine(a.path()),this,a.scope());
            } catch (NoSuchMethodException e) {
                LOG.error(e.getMessage());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

    }

    static class IndexEntry {
        String path;
        long size;
        String name;

        private IndexEntry(String path, long size, String name) {
            this.path = path;
            this.size = size;
            this.name = name;
        }

        static IndexEntry createIndexEntry(File file){
            return new IndexEntry(file.getPath(),file.length(), file.getName());
        }

        @Override
        public String toString() {
            return "IndexEntry{" +
                    "path='" + path + '\'' +
                    ", size=" + size +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
