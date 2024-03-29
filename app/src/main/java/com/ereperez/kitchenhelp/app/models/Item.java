package com.ereperez.kitchenhelp.app.models;

/**
 * Constructor model with setters & getters to form the itemlist with jsondata
 */
public class Item {
    String name;
    String url;
    String id;

    public Item(String name, String url, String id) {
        this.name = name;
        this.url = url;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
