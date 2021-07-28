package com.jnesis.jap.peartopear.utils;

import org.junit.Test;

import java.io.File;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PathUtilsTest {

    @Test
    public void testCombine(){
        assertThat(PathUtils.combine("C:", "temp"),is("C:"+File.pathSeparator+"temp"));
    }

}