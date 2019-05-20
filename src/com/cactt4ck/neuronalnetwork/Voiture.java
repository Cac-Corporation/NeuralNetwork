package com.cactt4ck.neuronalnetwork;

import java.awt.*;

public class Voiture {

    private Vector position;
    private double angle,  vitesse, distanceWest, distanceEast, distanceNorth, distanceSouth;
    private final Color couleur;
    private boolean alive;
    private NeuralNetwork network;
    private long birthInstant, deathInstant, lastCheckpointInstant;
    private double totalDistance;
    private int checkpoint, checkpointCount;

    public Voiture(Vector position){
        this.position = position;
        this.angle = Math.PI/2D;
        this.vitesse = 0D;
        this.alive = true;
        this.couleur = new Color((int)(Math.random()*128D), (int)(Math.random()*128D), (int)(Math.random()*128D));
        this.network = null;
        this.setAlive(true);
    }

    public Voiture(double x, double y){
        this(new Vector(x,y)); //appel de l'autre constructeur
    }

    public NeuralNetwork getNetwork() {
        return network;
    }

    public void setNeuralNetwork(double[][] scheme){
        Neurone accelerate = new Neurone(1F, () -> this.accelerer(0.025D)),
                decelerate = new Neurone(1F, () -> this.freiner(0.025D)),
                left = new Neurone(1F, () -> this.tourner(0.05)),
                right = new Neurone(1F, () -> this.tourner(-0.05));
        Neurone speedInput = new Neurone(1F,
                        new Connexion(accelerate, scheme == null ? Math.random() : scheme[0][0]), //condition ternaire if null math.random sinon scheme
                        new Connexion(decelerate, scheme == null ? Math.random() : scheme[0][1]),
                        new Connexion(left, scheme == null ? Math.random() : scheme[0][2]),
                        new Connexion(right, scheme == null ? Math.random() : scheme[0][3])),

                northInput = new Neurone(1F,
                        new Connexion(accelerate, scheme == null ? Math.random() : scheme[1][0]),
                        new Connexion(decelerate, scheme == null ? Math.random() : scheme[1][1]),
                        new Connexion(left, scheme == null ? Math.random() : scheme[1][2]),
                        new Connexion(right, scheme == null ? Math.random() : scheme[1][3])),

                southInput = new Neurone(1F,
                        new Connexion(accelerate, scheme == null ? Math.random() : scheme[2][0]),
                        new Connexion(decelerate, scheme == null ? Math.random() : scheme[2][1]),
                        new Connexion(left, scheme == null ? Math.random() : scheme[2][2]),
                        new Connexion(right, scheme == null ? Math.random() : scheme[2][3])),

                eastInput = new Neurone(1F,
                        new Connexion(accelerate, scheme == null ? Math.random() : scheme[3][0]),
                        new Connexion(decelerate, scheme == null ? Math.random() : scheme[3][1]),
                        new Connexion(left, scheme == null ? Math.random() : scheme[3][2]),
                        new Connexion(right, scheme == null ? Math.random() : scheme[3][3])),

                westInput = new Neurone(1F,
                        new Connexion(accelerate, scheme == null ? Math.random() : scheme[4][0]),
                        new Connexion(decelerate, scheme == null ? Math.random() : scheme[4][1]),
                        new Connexion(left, scheme == null ? Math.random() : scheme[4][2]),
                        new Connexion(right, scheme == null ? Math.random() : scheme[4][3]));

        this.network = new NeuralNetwork(new Neurone[]{
                speedInput, northInput, southInput, eastInput, westInput
        }, new Neurone[]{
                accelerate, decelerate, left, right
        });
    }

    public void addNeuralNetwork(){
        this.setNeuralNetwork(null);
    }

    public long getLastCheckpointInstant() {
        return lastCheckpointInstant;
    }

    public Vector getPosition() {
        return position;
    }

    public double getVitesse() {
        return vitesse;
    }

    public int getCheckpoint() {
        return checkpoint;
    }

    public int getCheckpointCount() {
        return checkpointCount;
    }

    public void setCheckpoint(int checkpoint) {
        if((checkpoint == 0 && this.checkpoint == 7) || checkpoint == this.checkpoint + 1) {
            this.checkpoint = checkpoint;
            this.lastCheckpointInstant = System.currentTimeMillis();
            this.checkpointCount++;
        }else if(checkpoint != this.checkpoint){
            this.setAlive(false);
            return;
        }
    }

    public void draw(Graphics2D g2){
        if(alive) {
            g2.setColor(couleur);
            g2.fillOval((int) position.getX() - 2, (int) position.getY() - 2, 5, 5);
            g2.drawLine((int) position.getX(), (int) position.getY(), (int) (position.getX() + 20D * Math.cos(angle)), (int) (position.getY() - 20D * Math.sin(angle)));
        }
    }

    public boolean hasNeuralNetwork(){
        return this.network != null;
    }

    public void update(){
        if(!alive)
            return;
        this.position = Vector.add(position, new Vector(
                vitesse*Math.cos(angle),
                -vitesse*Math.sin(angle)
        ));
        this.freiner(0.0025D);
        this.totalDistance += vitesse;

        if(this.network != null){
            this.network.getInput()[0].broadcast(this.vitesse);
            this.network.getInput()[1].broadcast(80D/this.distanceNorth - 1D);
            this.network.getInput()[2].broadcast(80D/this.distanceSouth - 1D);
            this.network.getInput()[3].broadcast(80D/this.distanceEast - 1D);
            this.network.getInput()[4].broadcast(80D/this.distanceWest - 1D);
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

    public long getBirthInstant() {
        return birthInstant;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public long getDeathInstant() {
        return deathInstant;
    }

    public void tourner(double angle){
        if(alive)
            this.angle = (this.angle + angle)%(2D*Math.PI);
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
        if(alive){  // récupérer la meilleure voiture en terme de temps et de distance parcourue
            birthInstant = lastCheckpointInstant = System.currentTimeMillis() ;
            deathInstant = -1L;
            this.totalDistance = 0D;
            this.checkpoint = 0;
            this.checkpointCount = 0;
        }else {
            deathInstant = System.currentTimeMillis();
            this.position = new Vector(100,200);
            this.vitesse =0;
            this.angle = Math.PI/2D;
        }
    }
}
