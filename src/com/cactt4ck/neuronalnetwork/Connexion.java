package com.cactt4ck.neuronalnetwork;

public class Connexion {

    private final Neurone output;
    private final double weight;

    public Connexion(Neurone output, double weight){
        this.output = output;
        this.weight = weight;
    }

    public void sendMessage(double value){
        this.output.getMessage(weight*value);
    }

}
