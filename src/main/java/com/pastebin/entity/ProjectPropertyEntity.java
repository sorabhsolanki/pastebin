package com.pastebin.entity;

import javax.persistence.*;

/**
 */
@Entity
@Table(name = "project_property")
public class ProjectPropertyEntity {

    @Id
    private int id;

    @Column
    private String name;

    @Column
    private String value;

    @Column(name = "is_active")
    private boolean active;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
