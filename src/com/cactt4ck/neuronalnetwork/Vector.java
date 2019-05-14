package com.cactt4ck.neuronalnetwork;

public class Vector {

    private final double x, y;

    public Vector(double x, double y){
        this.x = x;
        this.y = y;
    }

    public static Vector fromPoints(final Vector a, final Vector b) {
        return Vector.substract(b, a);
    }

    public static Vector fromPolar(final double module, final double angle) {
        return new Vector(
                module * Math.cos(angle),
                module * Math.sin(angle)
        );
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Vector getOpposite(){
        return new Vector(-x, -y);
    }

    public static Vector add(Vector first, Vector second){
        return new Vector(first.x + second.x, first.y + second.y);
    }

    public static Vector substract(Vector first, Vector second){
        return Vector.add(first, second.getOpposite());
    }

    public double getMagnitude() {
        return Math.sqrt(Math.pow(this.x, 2d) + Math.pow(this.y, 2d));
    }

    public double getAngle() {
        return Math.atan2(this.y, this.x);
    }
}
