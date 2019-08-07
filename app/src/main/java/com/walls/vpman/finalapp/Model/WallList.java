package com.walls.vpman.finalapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WallList
{
    @SerializedName("largeImageURL")
    private String original;


    @SerializedName("webformatURL")
    private String webformatURL;

    public WallList(String original, String webformatURL) {
        this.original = original;
        this.webformatURL = webformatURL;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getWebformatURL() {
        return webformatURL;
    }

    public void setWebformatURL(String webformatURL) {
        this.webformatURL = webformatURL;
    }
}
