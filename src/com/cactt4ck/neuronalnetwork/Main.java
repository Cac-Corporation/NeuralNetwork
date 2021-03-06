package com.cactt4ck.neuronalnetwork;

import javax.swing.*;

public class Main {

    public static void main(String[] args){
        /*
         *création de 3 neurones en entrée relié à 1 seul neurone en sortie
         *ces 3 neurones envoient un message propre au neurone en sortie, et lorsque ce dernier en a reçu suffisament (seuil), il envoie un message à son tour
         *
         *le poids des connexions influe sur la valeur du message transmis & le bias est l'importance d'un neurone seul
         */
        /*Neurone sortie = new Neurone(0, new Action() {
            @Override
            public void perform() {
                System.out.println("Neurone solicité");
            }
        });
        Neurone neurone1 = new Neurone(0, new Connexion(sortie, 0.7));
        Neurone neurone2 = new Neurone(0, new Connexion(sortie, 0.3));
        Neurone neurone3 = new Neurone(0, new Connexion(sortie, 0.5));
        neurone1.broadcast(0.5);
        neurone2.broadcast(0.5);
        neurone3.broadcast(0.5);*/

        Car[] cars = new Car[500];
        cars[0] = new Car(100,100);
        for (int i = 1; i< cars.length; i++){
            cars[i] = new Car(100,200);
            cars[i].addNeuralNetwork();
        }

        SwingUtilities.invokeLater(new Runnable() { // thread graphique (ewt)
            @Override
            public void run() {
                new Frame(cars);
            }
        });

    }
}
