package com.cactt4ck.neuronalnetwork;

import java.util.Arrays;

public class NeuralNetwork {

    private final Neurone[] input, output;

    public NeuralNetwork(Neurone[] input, Neurone[] output){
        this.input = input;
        this.output = output;
    }

    public Neurone[] getInput() {
        return input;
    }

    public Neurone[] getOutput() {
        return output;
    }

    public double[][] getScheme(){
        double[][] scheme = new double[input.length][output.length];
        for(int i = 0; i < input.length; i++){
            for (int j = 0; j < output.length; j++){
                scheme[i][j] = input[i].getConnexions()[j].getWeight();
            }
        }
        return scheme;
    }

    public static double[][] getMutated(double[][] scheme, double factor){
        double[][] mutated = new double[scheme.length][scheme[0].length];
        for(int i = 0; i < mutated.length; i++){
            for (int j = 0; j < mutated[0].length; j++){
                mutated[i][j] = scheme[i][j] + ((Math.random()-0.5D)*factor);
            }
        }
        return mutated;
    }
}
