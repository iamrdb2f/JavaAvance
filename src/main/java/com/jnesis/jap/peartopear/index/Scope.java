package com.jnesis.jap.peartopear.index;

public enum Scope {
    BASE("Rep courant seulement"),ONE("inclu le 1er niveau de rep"),SUBTREE("aussi loin que possible");

    private String description;

    Scope(String description){
        this.description=description;
    }
}
