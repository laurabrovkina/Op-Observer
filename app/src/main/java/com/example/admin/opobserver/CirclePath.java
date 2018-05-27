package com.example.admin.opobserver;

import android.graphics.Path;

public class CirclePath {

    public int color;
    public float strokeWidth;
    public Path path;
    public int type;

    public CirclePath(int color, float strokeWidth, Path path,int type) {
        this.color = color;
        this.strokeWidth = strokeWidth;
        this.path = path;
        this.type=type;
    }
}