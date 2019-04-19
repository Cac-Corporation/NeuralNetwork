package com.cactt4ck.neuronalnetwork;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Panel extends JPanel {

    private ArrayList<Voiture> voitures;
    private Thread loop;
    private volatile boolean running;

    public Panel(){
        super();
        voitures = new ArrayList<Voiture>();
        voitures.add(new Voiture(100,100));
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                Voiture voiture = voitures.get(0);
                if(e.getKeyCode() == KeyEvent.VK_UP)
                    voiture.addVitesse(new Vecteur(0,-1));
                if(e.getKeyCode() == KeyEvent.VK_DOWN)
                    voiture.addVitesse(new Vecteur(0,1));
                if(e.getKeyCode() == KeyEvent.VK_LEFT)
                    voiture.addVitesse(new Vecteur(-1,0));
                if(e.getKeyCode() == KeyEvent.VK_RIGHT)
                    voiture.addVitesse(new Vecteur(1,0));
            }
        });
        this.setFocusable(true);
        this.requestFocusInWindow();
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

    public void start(){
        if(running)
            return;
        running = true;
        loop = new Thread(new Runnable() {
            @Override
            public void run() {
                while (running){
                    for(Voiture v : voitures)
                        v.update();
                    repaint();
                    try {
                        Thread.sleep(7L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        loop.start();
    }

    public void stop(){
        running = false;
    }
}
