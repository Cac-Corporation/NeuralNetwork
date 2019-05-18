package com.cactt4ck.neuronalnetwork.solid;

import com.cactt4ck.neuronalnetwork.Vector;

import java.awt.*;

public class Rectangle extends Solid{

    private final Vector dimension;

    public Rectangle(Color color, Vector position, Vector dimension) {
        super(color, position);
        this.dimension = dimension;
    }


    @Override
    public boolean isInside(Vector point) {
        return point.getX() >= position.getX() && point.getX() <= position.getX()+dimension.getX()   &&   point.getY() >= position.getY() && point.getY() <= position.getY()+dimension.getY();
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(color);
        g2.fillRect((int) position.getX(),(int)  position.getY(),(int)  dimension.getX(),(int)  dimension.getY());
    }

    public double getRatio(){
        return dimension.getY()/dimension.getX();
    }

    public Vector getDimension() {
        return dimension;
    }
}
