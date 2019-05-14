package com.cactt4ck.neuronalnetwork.solid;

import com.cactt4ck.neuronalnetwork.Affine;
import com.cactt4ck.neuronalnetwork.Segment;
import com.cactt4ck.neuronalnetwork.Vector;

import java.awt.*;

public class Rectangle extends Solid{

    private final Vector position, dimension;
    private final Segment[] arets;

    public Rectangle(Color color, Vector position, Vector dimension) {
        super(color);
        this.position = position;
        this.dimension = dimension;
        arets = new Segment[]{
                Segment.fromVector(position, new Vector(dimension.getX(), 0)),
                Segment.fromVector(position, new Vector(0, dimension.getY())),
                Segment.fromVector(Vector.add(position, dimension), new Vector(-dimension.getX(), 0)),
                Segment.fromVector(Vector.add(position, dimension), new Vector(0, -dimension.getY()))
        };
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

    @Override
    public Vector getIntersection(Segment segment) {
        Vector last = null;
        for (Segment segments : arets){
            Vector point = segment.getIntersectionPoint(segments);
            if(point==null)
                continue;
            if(last == null || Vector.fromPoints(point, segment.getOrigin()).getMagnitude() < Vector.fromPoints(last, segment.getOrigin()).getMagnitude()){
                last = point;
            }
        }
        return last;
    }

    @Override
    public double getDistanceFrom(Segment segment) {
        Vector inter = this.getIntersection(segment);
        if(inter == null)
            return Double.POSITIVE_INFINITY;
        return Vector.fromPoints(inter, segment.getOrigin()).getMagnitude();
    }

    public Vector getPosition() {
        return position;
    }

    public Vector getDimension() {
        return dimension;
    }
}
