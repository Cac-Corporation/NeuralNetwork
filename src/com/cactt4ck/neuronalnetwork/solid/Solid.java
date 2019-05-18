package com.cactt4ck.neuronalnetwork.solid;

import com.cactt4ck.neuronalnetwork.Vector;

import java.awt.*;

public abstract class Solid {

    protected final Color color;
    protected final Vector position;

    public Solid(Color color, Vector position){
        this.color = color;
        this.position = position;
    }

    public abstract boolean isInside(Vector point);
    public abstract void draw(Graphics2D g2);
    public Vector getPosition() {
        return position;
    }

    public Color getColor() {
        return color;
    }
}
