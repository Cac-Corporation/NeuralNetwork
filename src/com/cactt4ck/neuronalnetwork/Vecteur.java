package com.cactt4ck.neuronalnetwork;

public class Vecteur {

    private final double x, y;

    public Vecteur(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public static Vecteur add(Vecteur first, Vecteur second){
        return new Vecteur(first.x + second.x, first.y + second.y);
    }
}
