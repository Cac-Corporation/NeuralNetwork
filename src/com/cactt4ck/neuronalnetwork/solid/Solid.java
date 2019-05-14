package com.cactt4ck.neuronalnetwork.solid;

import com.cactt4ck.neuronalnetwork.Vecteur;

import java.awt.*;

public abstract class Solid {

    protected final Color color;

    public Solid(Color color){
        this.color = color;
    }

    public abstract boolean isInside(Vecteur point);
    public abstract void draw(Graphics2D g2);

    public Color getColor() {
        return color;
    }
}
