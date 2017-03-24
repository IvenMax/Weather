package com.iven.app.bean;

/**
 * @author Iven
 * @date 2017/3/24 11:15
 * @Description
 */

public class User {
    private String uid;
    private String name;
    private String iconUrl;

    public String getUid() {
        return uid;
    }

    public User setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public User setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                '}';
    }
}
