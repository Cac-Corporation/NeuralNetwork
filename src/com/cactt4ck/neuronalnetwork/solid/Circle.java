package com.cactt4ck.neuronalnetwork.solid;

import com.cactt4ck.neuronalnetwork.Affine;
import com.cactt4ck.neuronalnetwork.Segment;
import com.cactt4ck.neuronalnetwork.Vector;

import java.awt.*;

public class Circle extends Solid{

    private final Vector position;
    private final int radius;

    public Circle(Color color, Vector position, int radius) {
        super(color);
        this.radius = radius;
        this.position = position;
    }



    @Override
    public boolean isInside(Vector point) {
        //(x-a)^2 + (y-b)^2 = r^2//
        return Math.pow(point.getX()-position.getX(), 2) + Math.pow(point.getY()-position.getY(), 2) <= Math.pow(radius, 2);
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(color);
        g2.fillOval((int) position.getX()-radius, (int) position.getY()-radius, radius*2, radius*2);
    }

    @Override
    public Vector getIntersection(Segment segment) {
        return null;
    }

    @Override
    public double getDistanceFrom(Segment segment) {
        return 0;
    }

    public Vector getPosition() {
        return position;
    }

    public int getRadius() {
        return radius;
    }
}
