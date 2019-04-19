package com.cactt4ck.neuronalnetwork;

import javax.swing.*;

public class Frame extends JFrame {

    public Frame(){
        super();
        this.init();
    }

    private void init(){
        this.setSize(500,500);
        Panel panel = new Panel();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setContentPane(panel);
        this.setVisible(true);
    }

}
