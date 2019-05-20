package com.cactt4ck.neuronalnetwork;

import com.cactt4ck.neuronalnetwork.solid.Circle;
import com.cactt4ck.neuronalnetwork.solid.Rectangle;
import com.cactt4ck.neuronalnetwork.solid.Solid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;

public class Panel extends JPanel {

    private int simulationCount, maxCheckpoint;
    private double maxDistance;
    private double[][] bestScheme;
    private ArrayList<Voiture> voitures;
    private ArrayList<Solid> solids, checkpoints;
    private Thread loop;
    private volatile boolean running;
    private boolean[] commands;
    private static final long TIME_MULTIPLIER = 7L;


    public Panel(Voiture... cars){
        super();
        simulationCount = 0;
        bestScheme = null;
        solids = new ArrayList<Solid>();
        checkpoints = new ArrayList<Solid>();
        //solids.add(new Circle(Color.BLACK, new Vector(250, 237) , 50));

        solids.add(new Rectangle(Color.BLACK, new Vector(0, 0) , new Vector(500,25)));
        solids.add(new Rectangle(Color.BLACK, new Vector(0, 0) , new Vector(25,500)));
        solids.add(new Rectangle(Color.BLACK, new Vector(475, 0) , new Vector(25,500)));
        solids.add(new Rectangle(Color.BLACK, new Vector(0, 450) , new Vector(500,25)));


        solids.add(new Rectangle(Color.BLACK, new Vector(200,175), new Vector(100,25)));
        solids.add(new Rectangle(Color.BLACK, new Vector(200,250), new Vector(100,25)));
        solids.add(new Rectangle(Color.BLACK, new Vector(200,175), new Vector(25,100)));
        solids.add(new Rectangle(Color.BLACK, new Vector(275,175), new Vector(25,100)));



        checkpoints.add(new Rectangle(Color.BLUE, new Vector(25,254), new Vector(175,10)));

        checkpoints.add(new Rectangle(Color.BLUE, new Vector(200,25), new Vector(10,150)));
        checkpoints.add(new Rectangle(Color.BLUE, new Vector(210,25), new Vector(10,150)));

        checkpoints.add(new Rectangle(Color.BLUE, new Vector(300,175), new Vector(175,10)));
        checkpoints.add(new Rectangle(Color.BLUE, new Vector(300,185), new Vector(175,10)));

        checkpoints.add(new Rectangle(Color.BLUE, new Vector(289,275), new Vector(10,175)));
        checkpoints.add(new Rectangle(Color.BLUE, new Vector(279,275), new Vector(10,175)));

        checkpoints.add(new Rectangle(Color.BLUE, new Vector(25,264), new Vector(175,10)));

        voitures = new ArrayList<Voiture>();
        commands = new boolean[4];
        for (Voiture voiture : cars){
            voitures.add(voiture);
        }

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
        /*for(Solid solid : checkpoints)
            solid.draw(g2);*/
        for(Voiture v : voitures)
            v.draw(g2);
    }

    public void start(){
        if(running)
            return;
        running = true;
        loop = new Thread( () -> {
                while (running){
                    if(commands[0])
                        voitures.get(0).accelerer(0.05);
                    if(commands[1])
                        voitures.get(0).freiner(0.05);
                    if(commands[2])
                        voitures.get(0).tourner(0.05);
                    if(commands[3])
                        voitures.get(0).tourner(-0.05);
                    boolean oneAlive = false;
                    for(Voiture v : voitures) {
                        v.update();

                        if(v.equals(voitures.get(0)))
                            continue;
                        if(v.isAlive())
                            oneAlive = true;

                        double dn = -1, ds = -1, de = -1, dw = -1;
                        for (Solid solid : solids){
                            if(solid.isInside(v.getPosition())) {
                                v.setAlive(false);
                                break;
                            }
                            if (solid instanceof Rectangle){
                                Rectangle rectangle = (Rectangle)solid;
                                if(rectangle.getRatio() >= 1D && v.getPosition().getY() >= rectangle.getPosition().getY() && v.getPosition().getY() <= rectangle.getPosition().getY() + rectangle.getDimension().getY()){
                                    double cde = rectangle.getPosition().getX() - v.getPosition().getX(),
                                            cdw = v.getPosition().getX() - rectangle.getPosition().getX() - rectangle.getDimension().getX();
                                    if(cde >= 0D && (de == -1 || cde < de))
                                        de = cde;
                                    if(cdw >= 0D && (dw == -1 || cdw < dw))
                                        dw = cdw;
                                }else if(rectangle.getRatio() < 1D && v.getPosition().getX() >= rectangle.getPosition().getX() && v.getPosition().getX() <= rectangle.getPosition().getX() + rectangle.getDimension().getX()) {
                                    double cds = rectangle.getPosition().getY() - v.getPosition().getY(),
                                            cdn = v.getPosition().getY() - rectangle.getPosition().getY() - rectangle.getDimension().getY();
                                    if(cdn >= 0D && (dn == -1 || cdn < dn))
                                        dn = cdn;
                                    if(cds >= 0D && (ds == -1 || cds < ds))
                                        ds = cds;
                                }
                            }
                        }
                        v.setDistanceNorth(dn);
                        v.setDistanceSouth(ds);
                        v.setDistanceEast(de);
                        v.setDistanceWest(dw);
                        for (int i =0;i<checkpoints.size();i++){
                            if(checkpoints.get(i).isInside(v.getPosition())){
                                v.setCheckpoint(i);
                            }
                        }
                        if(System.currentTimeMillis() - v.getLastCheckpointInstant() >= 5000L/TIME_MULTIPLIER){
                            v.setAlive(false);
                        }
                    }
                    if(!oneAlive) {
                        int cMaxCheckpoint = this.maxCheckpoint;
                        double cMaxDistance = this.maxDistance;
                        double[][] cBestScheme = this.bestScheme;

                        for(Voiture v : voitures){
                            if(v.equals(voitures.get(0)))
                                continue;
                            if(v.getCheckpointCount() > cMaxCheckpoint || (v.getCheckpointCount() == cMaxCheckpoint && v.getTotalDistance() > cMaxDistance)){
                                cMaxCheckpoint = v.getCheckpointCount();
                                cMaxDistance = v.getTotalDistance();
                                cBestScheme = v.getNetwork().getScheme();
                            }
                        }
                        if(this.bestScheme == null || !Arrays.deepEquals(this.bestScheme, cBestScheme))
                            simulationCount++;
                        this.bestScheme = cBestScheme;
                        this.maxCheckpoint = cMaxCheckpoint;
                        this.maxDistance = cMaxDistance;
                        createVoitures();
                    }

                    repaint();
                    try {
                        Thread.sleep(7L/TIME_MULTIPLIER);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        );
        loop.start();
    }

    private boolean isInside(double start, double x, double end){
        return x >= start && x <= end;
    }

    private void createVoitures(){
        System.out.println("Simulation numéro : " + simulationCount + " - Max Checkpoint : " + this.maxCheckpoint);
        for(int i = 1;i<voitures.size();i++){
            voitures.get(i).setNeuralNetwork(NeuralNetwork.getMutated(this.bestScheme, 0.2D/ (double) Math.pow(this.simulationCount, 2)));
            voitures.get(i).setAlive(true);
        }
    }

    public void stop(){
        running = false;
    }
}
