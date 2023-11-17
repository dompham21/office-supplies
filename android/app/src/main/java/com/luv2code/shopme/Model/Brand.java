package com.luv2code.shopme.Model;

import java.io.Serializable;

public class Brand implements Serializable {

    private Integer id;
    private String name;
    private String logo;
    private String description;

    public Brand() {
    }

    public Brand(Integer id, String name, String logo, String description) {
        this.id = id;
        this.name = name;
        this.logo = logo;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
