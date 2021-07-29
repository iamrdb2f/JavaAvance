package com.jnesis.jap.peartopear.index;

import com.jnesis.jap.peartopear.utils.PathUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class Index {

    private static final Logger LOG = LoggerFactory.getLogger(Index.class);

    private HashMap<String,IndexEntry> indexMap=new HashMap<>();

    public void addToIndex(File f){
        LOG.info("Adding file "+f.getName());
        if (!f.exists()){
            LOG.warn("File doesn't exist");
            return;
        }
        final IndexEntry indexEntry=IndexEntry.createIndexEntry(f);
        indexMap.put(f.getName(),indexEntry);
        LOG.info("IndexEntry "+indexEntry+" has been added");
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
