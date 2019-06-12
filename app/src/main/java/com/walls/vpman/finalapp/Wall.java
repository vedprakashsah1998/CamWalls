package com.walls.vpman.finalapp;

public class Wall
{
    String original,webformatURL;

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
