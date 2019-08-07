package com.walls.vpman.finalapp;


public class Wall
{

    private String original;


    private String webformatURL;
    public Wall(String original,String webformatURL)
    {
        this.original = original;
        this.webformatURL=webformatURL;
    }

    public String getOriginal() {
        return original;
    }

    public String getWebformatURL() {
        return webformatURL;
    }
}
