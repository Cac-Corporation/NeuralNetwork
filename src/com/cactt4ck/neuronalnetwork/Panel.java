package com.cactt4ck.neuronalnetwork;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Panel extends JPanel {

    private ArrayList<Voiture> voitures;
    private Thread loop;
    private volatile boolean running;
    private boolean[] commands;

    public Panel(){
        super();
        voitures = new ArrayList<Voiture>();
        commands = new boolean[4];
        voitures.add(new Voiture(100,100));
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                Voiture voiture = voitures.get(0);
                if(e.getKeyCode() == KeyEvent.VK_UP)
                    commands[0] = true;
                if(e.getKeyCode() == KeyEvent.VK_DOWN)
                    commands[1] = true;
                if(e.getKeyCode() == KeyEvent.VK_LEFT)
                    commands[2] = true;
                if(e.getKeyCode() == KeyEvent.VK_RIGHT)
                    commands[3] = true;
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                    voiture.setAlive(true);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                Voiture voiture = voitures.get(0);
                if(e.getKeyCode() == KeyEvent.VK_UP)
                    commands[0] = false;
                if(e.getKeyCode() == KeyEvent.VK_DOWN)
                    commands[1] = false;
                if(e.getKeyCode() == KeyEvent.VK_LEFT)
                    commands[2] = false;
                if(e.getKeyCode() == KeyEvent.VK_RIGHT)
                    commands[3] = false;
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

        g2.fillOval(200, 187, 100, 100);

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
                    if(commands[0])
                        voitures.get(0).accelerer(0.05);
                    if(commands[1])
                        voitures.get(0).freiner(0.05);
                    if(commands[2])
                        voitures.get(0).tourner(0.05);
                    if(commands[3])
                        voitures.get(0).tourner(-0.05);
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
