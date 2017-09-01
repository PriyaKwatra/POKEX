package com.example.acer.pokex;

/**
 * Created by acer on 6.07.2017.
 */

public class BasicInfo {

    String name;
    String weight;
    ImageInfo sprites;

    public BasicInfo(String name, String weight, ImageInfo sprites) {
        this.name = name;
        this.weight = weight;
        this.sprites = sprites;
    }

    public String getName() {
        return name;
    }

    public String getWeight() {
        return weight;
    }

    public ImageInfo getSprites() {
        return sprites;
    }
}
