package com.cactt4ck.neuronalnetwork;

import java.io.Serializable;

public class Connexion implements Serializable {

    private static final long serialVersionUID = 1002369697829466020L;
    private final Neurone output;
    private final double weight;

    public Connexion(Neurone output, double weight){
        this.output = output;
        this.weight = weight;
    }

    public void sendMessage(double value){
        this.output.getMessage(weight*value);
    }

    public Neurone getOutput() {
        return output;
    }

    public double getWeight() {
        return weight;
    }


}
