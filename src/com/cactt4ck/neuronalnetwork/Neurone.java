package com.cactt4ck.neuronalnetwork;

import java.io.Serializable;

public class Neurone implements Serializable {

    private static final long serialVersionUID = 5813336092091904625L;
    private final double bias;
    private final Synapse[] synapses;
    private final transient Action action; //pas de sauvegarde quand ecrit dans un fichier//
    private double somme;


    public Neurone(double bias, Synapse... synapses){ //... = infini
        this(bias, null, synapses);
    }

    public Neurone(double bias, Action action, Synapse... synapses){
        this.synapses = synapses;
        this.bias = bias;
        this.somme = 0;
        this.action = action;
    }


    public void getMessage(double input){
        this.somme += input;

        double f = 1D/(1+Math.pow(Math.E, -(bias + somme)));
        if (Math.random() <= f) {
            this.action.perform();
            broadcast(f);
            somme = 0;
        }
    }

    public void broadcast(double value){
        for(int i = 0; i< synapses.length; i++){
            synapses[i].sendMessage(value);
        }
    }

    public Synapse[] getConnexions() {
        return synapses;
    }
}
