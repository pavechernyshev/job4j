package ru.job4j.tracker;

public class Item {
    private String id;
    private String name;
    private String desc;
    private long created;
    private String[] comments;

    public void setId(String id) {
        this.id = id;
    }

    public void setComments(String[] comments) {
        this.comments = comments;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getCreated() {
        return created;
    }

    public String getDesc() {
        return desc;
    }

    public String[] getComments() {
        return comments;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Item() {

    }

    public Item(String name, String desc, long created) {
        this.setName(name);
        this.setDesc(desc);
        this.setCreated(created);
    }
}
