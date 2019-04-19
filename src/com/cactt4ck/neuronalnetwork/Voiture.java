package com.cactt4ck.neuronalnetwork;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Voiture {

    private Vecteur position;
    private double angle,  vitesse;
    private final Color couleur;

    public Voiture(Vecteur position){
        this.position = position;
        this.angle = Math.PI/2D;
        this.vitesse = 0D;
        this.couleur = new Color((int)(Math.random()*128D), (int)(Math.random()*128D), (int)(Math.random()*128D));
    }

    public Voiture(double x, double y){
        this(new Vecteur(x,y)); //appel de l'autre constructeur
    }

    public Vecteur getPosition() {
        return position;
    }

    public void setPosition(Vecteur position) {
        this.position = position;
    }

    public double getVitesse() {
        return vitesse;
    }

    public void setVitesse(double vitesse) {
        this.vitesse = vitesse;
    }

    public void draw(Graphics2D g2){
        g2.setColor(couleur);
        g2.fillOval((int)position.getX() - 2, (int)position.getY() - 2,5,5);
        g2.drawLine((int)position.getX(), (int)position.getY(), (int)(position.getX() + 20D*Math.cos(angle)), (int)(position.getY() - 20D*Math.sin(angle)));
    }

    public void update(){
        this.position = Vecteur.add(position, new Vecteur(
                vitesse*Math.cos(angle),
                -vitesse*Math.sin(angle)
        ));
        this.freiner(0.0025D);

        if(position.getX() < 25)
            position = new Vecteur(25, position.getY());
        if(position.getY() < 25)
            position = new Vecteur(position.getX(), 25);
        if(position.getX() > 475)
            position = new Vecteur(475, position.getY());
        if(position.getY() > 450)
            position = new Vecteur(position.getX(), 450);
    }

    public double getAngle() {
        return angle;
    }

    public Color getCouleur() {
        return couleur;
    }

    public void accelerer(double value){
        this.vitesse += value;
    }

    public void freiner(double value){
        this.vitesse -= value;
        if(vitesse<0D)
            vitesse = 0D;
    }

    public void tourner(double angle){
        this.angle += angle;
    }
}
