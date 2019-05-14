package com.cactt4ck.neuronalnetwork.solid;

import com.cactt4ck.neuronalnetwork.Affine;
import com.cactt4ck.neuronalnetwork.Segment;
import com.cactt4ck.neuronalnetwork.Vector;

import java.awt.*;

public abstract class Solid {

    protected final Color color;

    public Solid(Color color){
        this.color = color;
    }

    public abstract boolean isInside(Vector point);
    public abstract void draw(Graphics2D g2);
    public abstract Vector getIntersection(Segment segment);
    public abstract double getDistanceFrom(Segment segment);

    public Color getColor() {
        return color;
    }
}
