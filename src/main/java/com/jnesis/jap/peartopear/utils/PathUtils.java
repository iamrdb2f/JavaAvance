package com.jnesis.jap.peartopear.utils;

import java.io.File;

public class PathUtils {

    public static String combine(String... pathElements){
        return String.join(File.pathSeparator, pathElements);
    }
}
