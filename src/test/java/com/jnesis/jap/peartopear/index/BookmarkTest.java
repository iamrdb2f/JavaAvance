package com.jnesis.jap.peartopear.index;

import com.jnesis.jap.peartopear.bookmark.DatabaseBookmarkCollection;
import com.jnesis.jap.peartopear.bookmark.FileBookmarkCollection;
import org.junit.Test;

public class BookmarkTest {

    @Test
    public void testBookmark(){
        DatabaseBookmarkCollection db = new DatabaseBookmarkCollection();
        FileBookmarkCollection f = new FileBookmarkCollection();
        f = f.addAll(db);
        //assert not doable for now
    }

}
