package com.cactt4ck.neuronalnetwork;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Panel extends JPanel {

    private ArrayList<Voiture> voitures;

    public Panel(){
        super();
        voitures = new ArrayList<Voiture>();
        voitures.add(new Voiture(100,100));
    }

    private void init(){

    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        final Stroke stroke = g2.getStroke();
        g2.setStroke(new BasicStroke(50));
        g2.drawRect(0,0,500,475);
        g2.setStroke(stroke);

        for(Voiture v : voitures){
            v.draw(g2);
        }
    }

}
