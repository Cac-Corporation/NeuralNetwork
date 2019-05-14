package com.cactt4ck.neuronalnetwork;

import java.awt.*;

public class Voiture {

    private Vector position;
    private double angle,  vitesse, distanceWest, distanceEast, distanceNorth, distanceSouth;
    private final Color couleur;
    private boolean alive;

    public Voiture(Vector position){
        this.position = position;
        this.angle = Math.PI/2D;
        this.vitesse = 0D;
        this.alive = true;
        this.couleur = new Color((int)(Math.random()*128D), (int)(Math.random()*128D), (int)(Math.random()*128D));
    }

    public Voiture(double x, double y){
        this(new Vector(x,y)); //appel de l'autre constructeur
    }

    public Vector getPosition() {
        return position;
    }

    public double getVitesse() {
        return vitesse;
    }

    public void draw(Graphics2D g2){
        if(alive) {
            g2.setColor(couleur);
            g2.fillOval((int) position.getX() - 2, (int) position.getY() - 2, 5, 5);
            g2.drawLine((int) position.getX(), (int) position.getY(), (int) (position.getX() + 20D * Math.cos(angle)), (int) (position.getY() - 20D * Math.sin(angle)));
        }
    }

    public void update(){
        if(!alive)
            return;
        this.position = Vector.add(position, new Vector(
                vitesse*Math.cos(angle),
                -vitesse*Math.sin(angle)
        ));
        this.freiner(0.0025D);
    }

    public double getAngle() {
        return angle;
    }

    public Color getCouleur() {
        return couleur;
    }

    public void accelerer(double value){
        if(alive)
            this.vitesse += value;
    }

    public void freiner(double value){
        if(alive) {
            this.vitesse -= value;
            if (vitesse < 0D)
                vitesse = 0D;
        }
    }

    public void setDistanceWest(double distanceWest) {
        this.distanceWest = distanceWest;
    }

    public void setDistanceEast(double distanceEast) {
        this.distanceEast = distanceEast;
    }

    public void setDistanceNorth(double distanceNorth) {
        this.distanceNorth = distanceNorth;
    }

    public void setDistanceSouth(double distanceSouth) {
        this.distanceSouth = distanceSouth;
    }

    public double getDistanceWest() {
        return distanceWest;
    }

    public double getDistanceEast() {
        return distanceEast;
    }

    public double getDistanceNorth() {
        return distanceNorth;
    }

    public double getDistanceSouth() {
        return distanceSouth;
    }


    public void tourner(double angle){
        if(alive)
            this.angle += angle;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
