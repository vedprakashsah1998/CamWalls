package com.walls.vpman.finalapp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WallArray
{
    @SerializedName("hits")
    private ArrayList<WallList> wallLists;

    public ArrayList<WallList> getWallLists() {
        return wallLists;
    }

    public void setWallLists(ArrayList<WallList> wallLists) {
        this.wallLists = wallLists;
    }
}
