package com.jnesis.jap.peartopear.bookmark;

public interface BookmarkCollection {

    /**
     * Adds a bookmark location to the collection.
     * @param s a bookmark location
     */
    public void addBookmark(String s);

    /**
     * Adds all the bookmarks of the given collection to the current one.
     * @param collection a bookmark collection
     * @return the current colleciton with the bookmarks from <code>c</code> added
     */
    public BookmarkCollection addAll(BookmarkCollection collection);
}

