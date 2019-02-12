package com.waracle.cakemgr.dto;

import java.util.Objects;

public class Cake {
    private String title;
    private String image;
    private String desc;

    public Cake() {
    }

    public Cake(String title, String desc, String image) {
        this.title = title;
        this.image = image;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "Cake{" +
                "title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cake cake = (Cake) o;
        return Objects.equals(getTitle(), cake.getTitle()) &&
                Objects.equals(getImage(), cake.getImage()) &&
                Objects.equals(getDesc(), cake.getDesc());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getTitle(), getImage(), getDesc());
    }
}
