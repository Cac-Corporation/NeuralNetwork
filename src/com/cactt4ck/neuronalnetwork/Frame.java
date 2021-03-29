package com.cactt4ck.neuronalnetwork;

import javax.swing.*;

public class Frame extends JFrame {

    public Frame(Car... cars){
        super();
        this.init(cars);
    }

    private void init(Car... cars){
        this.setSize(500,500);
        Panel panel = new Panel(cars);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setContentPane(panel);
        this.setTitle("TITRE");
        this.setVisible(true);
    }

}
