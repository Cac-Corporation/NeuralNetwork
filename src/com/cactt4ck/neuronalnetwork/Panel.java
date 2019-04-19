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
                    voiture.accelerer(0.5);
                if(e.getKeyCode() == KeyEvent.VK_DOWN)
                    voiture.freiner(0.5);
                if(e.getKeyCode() == KeyEvent.VK_LEFT)
                    voiture.tourner(0.5);
                if(e.getKeyCode() == KeyEvent.VK_RIGHT)
                    voiture.tourner(-0.5);
            }
        });
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.start();
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
