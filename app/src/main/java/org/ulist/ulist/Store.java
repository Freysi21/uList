package org.ulist.ulist;

import java.util.List;

/**
 * Created by Omar on 30.12.2014.
 */
public class Store {
    private List<Item> list;
    private String name;

    public Store(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Item> getItems(){
        return list;
    }

    public int listSize() {
        return list.size();
    }
}
