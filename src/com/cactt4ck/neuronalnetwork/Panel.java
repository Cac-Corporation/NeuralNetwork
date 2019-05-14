package com.cactt4ck.neuronalnetwork;

import com.cactt4ck.neuronalnetwork.solid.Circle;
import com.cactt4ck.neuronalnetwork.solid.Rectangle;
import com.cactt4ck.neuronalnetwork.solid.Solid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Panel extends JPanel {

    private ArrayList<Voiture> voitures;
    private ArrayList<Solid> solids;
    private Thread loop;
    private volatile boolean running;
    private boolean[] commands;

    public Panel(){
        super();
        solids = new ArrayList<Solid>();
        //solids.add(new Circle(Color.BLACK, new Vector(250, 237) , 50));

        solids.add(new Rectangle(Color.BLACK, new Vector(0, 0) , new Vector(500,25)));
        solids.add(new Rectangle(Color.BLACK, new Vector(0, 0) , new Vector(25,500)));
        solids.add(new Rectangle(Color.BLACK, new Vector(475, 0) , new Vector(25,500)));
        solids.add(new Rectangle(Color.BLACK, new Vector(0, 450) , new Vector(500,25)));

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
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(0,0,this.getWidth(), this.getHeight());

        for(Solid solid : solids)
            solid.draw(g2);

        for(Voiture v : voitures)
            v.draw(g2);
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
                    for(Voiture v : voitures) {
                        v.update();
                        double dn=10000, ds=10000, dw=10000, de=10000;
                        for (Solid solid : solids){
                            if(solid.isInside(v.getPosition())) {
                                v.setAlive(false);
                                break;
                            }
                            dn = Math.min(solid.getDistanceFrom(Segment.fromVector(v.getPosition(), new Vector(10000D * Math.cos(v.getAngle()),
                                    -10000D * Math.sin(v.getAngle())))), dn);

                            ds = Math.min(solid.getDistanceFrom(Segment.fromVector(v.getPosition(), new Vector(10000D * Math.cos(v.getAngle() + Math.PI/2d),
                                    -10000D * Math.sin(v.getAngle()+ Math.PI/2d)))), ds);

                            dw = Math.min(solid.getDistanceFrom(Segment.fromVector(v.getPosition(), new Vector(10000D * Math.cos(v.getAngle() + Math.PI),
                                    -10000D * Math.sin(v.getAngle()+ Math.PI)))), dw);

                            de = Math.min(solid.getDistanceFrom(Segment.fromVector(v.getPosition(), new Vector(10000D * Math.cos(v.getAngle() - Math.PI/2d),
                                    -10000D * Math.sin(v.getAngle()- Math.PI/2d)))), de);
                        }
                        v.setDistanceNorth(dn);
                        v.setDistanceSouth(ds);
                        v.setDistanceWest(dw);
                        v.setDistanceEast(de);
                        System.out.println("N=" + v.getDistanceNorth() + "     S=" + v.getDistanceSouth() + "     W=" + v.getDistanceWest() + "     E=" + v.getDistanceEast());
                    }
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
