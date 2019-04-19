package com.cactt4ck.neuronalnetwork;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Voiture {

    private Vecteur position;
    private double angle,  vitesse;
    private final Color couleur;
    private boolean alive;

    public Voiture(Vecteur position){
        this.position = position;
        this.angle = Math.PI/2D;
        this.vitesse = 0D;
        this.alive = true;
        this.couleur = new Color((int)(Math.random()*128D), (int)(Math.random()*128D), (int)(Math.random()*128D));
    }

    public Voiture(double x, double y){
        this(new Vecteur(x,y)); //appel de l'autre constructeur
    }

    public Vecteur getPosition() {
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
        this.position = Vecteur.add(position, new Vecteur(
                vitesse*Math.cos(angle),
                -vitesse*Math.sin(angle)
        ));
        this.freiner(0.0025D);

        if(position.getX() < 25){
            position = new Vecteur(30, position.getY());
            vitesse = 0;
            angle += Math.PI;
            alive = false;
        }
        if(position.getY() < 25){
            position = new Vecteur(position.getX(), 30);
            vitesse = 0;
            angle += Math.PI;
            alive = false;
        }
        if(position.getX() > 475) {
            position = new Vecteur(470, position.getY());
            vitesse = 0;
            angle += Math.PI;
            alive = false;
        }
        if(position.getY() > 450) {
            position = new Vecteur(position.getX(), 445);
            vitesse = 0;
            angle += Math.PI;
            alive = false;
        }
        if(Math.pow(position.getX() - 250, 2) + Math.pow(position.getY() - 237, 2) <= 2500){
            position = new Vecteur(position.getX(), 445);
            vitesse = 0;
            angle += Math.PI;
            alive = false;
        }
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
