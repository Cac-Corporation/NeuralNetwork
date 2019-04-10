package com.cactt4ck.neuronalnetwork;

public class NeuroneFirst {

    public final double link1, link2, link3, seuil; //link = poids des connexions

    public NeuroneFirst(double seuil){
        this(seuil, 0.0, 0.0, 0.0);
    }

    public NeuroneFirst(double seuil, double link1, double link2, double link3){
        this.link1 = link1;
        this.link2 = link2;
        this.link3 = link3;
        this.seuil = seuil;
    }



    private double sum(int e1, int e2, int e3){
        return link1*e1 + link2*e2 + link3*e3;
    }

    public int evaluate(int e1, int e2, int e3){
        if(sum(e1, e2, e3) >= seuil){
            return 1;
        }else {
            return 0;
        }
    }

}
