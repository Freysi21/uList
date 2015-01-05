package org.ulist.ulist;

import java.util.List;

/**
 * Created by Omar on 30.12.2014.
 */
public class Store {
    private List<Item> list;
    private String name;
    int id;

    public Store(int id, String name) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public List<Item> getItems(){
        return list;
    }

    public int listSize() {
        return list.size();
    }
}
