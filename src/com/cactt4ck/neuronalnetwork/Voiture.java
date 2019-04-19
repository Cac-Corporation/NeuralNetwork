package com.cactt4ck.neuronalnetwork;

import java.awt.*;

public class Voiture {

    private Vecteur position, vitesse;
    private final Color couleur;

    public Voiture(Vecteur position){
        this.position = position;
        this.vitesse = new Vecteur(0D,0D);
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

    public Vecteur getVitesse() {
        return vitesse;
    }

    public void setVitesse(Vecteur vitesse) {
        this.vitesse = vitesse;
    }

    public void draw(Graphics2D g2){
        g2.setColor(couleur);
        g2.fillRect((int)position.getX() - 2, (int)position.getY() - 2,4,4);
    }

    public void update(){

    }
}
