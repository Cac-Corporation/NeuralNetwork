package com.cactt4ck.neuronalnetwork.solid;

import com.cactt4ck.neuronalnetwork.Vecteur;

import java.awt.*;

public class Rectangle extends Solid{

    private final Vecteur position, dimension;

    public Rectangle(Color color, Vecteur position, Vecteur dimension) {
        super(color);
        this.position = position;
        this.dimension = dimension;
    }


    @Override
    public boolean isInside(Vecteur point) {
        return point.getX() >= position.getX() && point.getX() <= position.getX()+dimension.getX()   &&   point.getY() >= position.getY() && point.getY() <= position.getY()+dimension.getY();
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(color);
        g2.fillRect((int) position.getX(),(int)  position.getY(),(int)  dimension.getX(),(int)  dimension.getY());
    }

    public Vecteur getPosition() {
        return position;
    }

    public Vecteur getDimension() {
        return dimension;
    }
}
