package com.example.myapplication;

public class MenuListResponse {

    public String id;

    public int image;
    public String title;

    public MenuListResponse(String id, int image, String title) {
        this.id = id;
        this.image = image;
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
