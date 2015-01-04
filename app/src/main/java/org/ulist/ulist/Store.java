package org.ulist.ulist;

/**
 * Created by Omar on 30.12.2014.
 */
public class Store {
    private ItemList list;
    private String name;

    public Store(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ItemList getItems(){
        return list;
    }
}
