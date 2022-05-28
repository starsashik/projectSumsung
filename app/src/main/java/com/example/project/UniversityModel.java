package com.example.project;

import java.io.Serializable;

public class UniversityModel implements Serializable {
    private Integer src;
    private String name;
    private String uid;

    public UniversityModel(Integer src, String name, String uid) {
        this.src = src;
        this.name = name;
        this.uid = uid;
    }

    public Integer getSrc() {
        return src;
    }

    public void setSrc(Integer src) {
        this.src = src;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
