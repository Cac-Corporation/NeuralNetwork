package com.cactt4ck.neuronalnetwork;

import com.cactt4ck.neuronalnetwork.solid.Rectangle;
import com.cactt4ck.neuronalnetwork.solid.Solid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;

@SuppressWarnings("all")
public class Panel extends JPanel {

    private int simulationCount, maxCheckpoint;
    private double maxDistance;
    private double[][] bestScheme;
    private final ArrayList<Car> cars;
    private final ArrayList<Solid> solids;
    private final ArrayList<Solid> checkpoints;
    private Thread loop;
    private volatile boolean running;
    private final boolean[] commands;
    private static final long TIME_MULTIPLIER = 1L;


    public Panel(final Car... cars) {
        super();
        this.simulationCount = 0;
        this.bestScheme = null;
        this.solids = new ArrayList<Solid>();
        this.checkpoints = new ArrayList<Solid>();
        //solids.add(new Circle(Color.BLACK, new Vector(250, 237) , 50));

        this.solids.add(new Rectangle(Color.BLACK, new Vector(0, 0), new Vector(500, 25)));
        this.solids.add(new Rectangle(Color.BLACK, new Vector(0, 0), new Vector(25, 500)));
        this.solids.add(new Rectangle(Color.BLACK, new Vector(475, 0), new Vector(25, 500)));
        this.solids.add(new Rectangle(Color.BLACK, new Vector(0, 450), new Vector(500, 25)));


        this.solids.add(new Rectangle(Color.BLACK, new Vector(200, 175), new Vector(100, 25)));
        this.solids.add(new Rectangle(Color.BLACK, new Vector(200, 250), new Vector(100, 25)));
        this.solids.add(new Rectangle(Color.BLACK, new Vector(200, 175), new Vector(25, 100)));
        this.solids.add(new Rectangle(Color.BLACK, new Vector(275, 175), new Vector(25, 100)));


        this.checkpoints.add(new Rectangle(Color.BLUE, new Vector(25, 254), new Vector(175, 10)));

        this.checkpoints.add(new Rectangle(Color.BLUE, new Vector(200, 25), new Vector(10, 150)));
        this.checkpoints.add(new Rectangle(Color.BLUE, new Vector(210, 25), new Vector(10, 150)));

        this.checkpoints.add(new Rectangle(Color.BLUE, new Vector(300, 175), new Vector(175, 10)));
        this.checkpoints.add(new Rectangle(Color.BLUE, new Vector(300, 185), new Vector(175, 10)));

        this.checkpoints.add(new Rectangle(Color.BLUE, new Vector(289, 275), new Vector(10, 175)));
        this.checkpoints.add(new Rectangle(Color.BLUE, new Vector(279, 275), new Vector(10, 175)));

        this.checkpoints.add(new Rectangle(Color.BLUE, new Vector(25, 264), new Vector(175, 10)));

        this.cars = new ArrayList<Car>();
        this.commands = new boolean[4];
        for (final Car car : cars) {
            this.cars.add(car);
        }

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(final KeyEvent e) {
                final Car car = Panel.this.cars.get(0);
                if (e.getKeyCode() == KeyEvent.VK_UP)
                    Panel.this.commands[0] = true;
                if (e.getKeyCode() == KeyEvent.VK_DOWN)
                    Panel.this.commands[1] = true;
                if (e.getKeyCode() == KeyEvent.VK_LEFT)
                    Panel.this.commands[2] = true;
                if (e.getKeyCode() == KeyEvent.VK_RIGHT)
                    Panel.this.commands[3] = true;
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    car.setAlive(true);
            }

            @Override
            public void keyReleased(final KeyEvent e) {
                final Car car = Panel.this.cars.get(0);
                if (e.getKeyCode() == KeyEvent.VK_UP)
                    Panel.this.commands[0] = false;
                if (e.getKeyCode() == KeyEvent.VK_DOWN)
                    Panel.this.commands[1] = false;
                if (e.getKeyCode() == KeyEvent.VK_LEFT)
                    Panel.this.commands[2] = false;
                if (e.getKeyCode() == KeyEvent.VK_RIGHT)
                    Panel.this.commands[3] = false;
            }
        });
        setFocusable(true);
        requestFocusInWindow();
        start();
    }

    @Override
    protected void paintComponent(final Graphics g){
        super.paintComponent(g);
        final Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(0,0, getWidth(), getHeight());

        for(final Solid solid : this.solids)
            solid.draw(g2);
        /*for(Solid solid : checkpoints)
            solid.draw(g2);*/
        for(final Car v : this.cars)
            v.draw(g2);
    }

    public void start(){
        if(this.running)
            return;
        this.running = true;
        this.loop = new Thread( () -> {
                while (this.running){
                    if(this.commands[0])
                        this.cars.get(0).accelerer(0.05);
                    if(this.commands[1])
                        this.cars.get(0).freiner(0.05);
                    if(this.commands[2])
                        this.cars.get(0).tourner(0.05);
                    if(this.commands[3])
                        this.cars.get(0).tourner(-0.05);
                    boolean oneAlive = false;
                    for(final Car v : this.cars) {
                        v.update();

                        if(v.equals(this.cars.get(0)))
                            continue;
                        if(v.isAlive())
                            oneAlive = true;

                        double dn = -1, ds = -1, de = -1, dw = -1;
                        for (final Solid solid : this.solids){
                            if(solid.isInside(v.getPosition())) {
                                v.setAlive(false);
                                break;
                            }
                            if (solid instanceof Rectangle){
                                final Rectangle rectangle = (Rectangle)solid;
                                if(rectangle.getRatio() >= 1D && v.getPosition().getY() >= rectangle.getPosition().getY() && v.getPosition().getY() <= rectangle.getPosition().getY() + rectangle.getDimension().getY()){
                                    final double cde = rectangle.getPosition().getX() - v.getPosition().getX();
                                    final double cdw = v.getPosition().getX() - rectangle.getPosition().getX() - rectangle.getDimension().getX();
                                    if(cde >= 0D && (de == -1 || cde < de))
                                        de = cde;
                                    if(cdw >= 0D && (dw == -1 || cdw < dw))
                                        dw = cdw;
                                }else if(rectangle.getRatio() < 1D && v.getPosition().getX() >= rectangle.getPosition().getX() && v.getPosition().getX() <= rectangle.getPosition().getX() + rectangle.getDimension().getX()) {
                                    final double cds = rectangle.getPosition().getY() - v.getPosition().getY();
                                    final double cdn = v.getPosition().getY() - rectangle.getPosition().getY() - rectangle.getDimension().getY();
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
                        for (int i = 0; i< this.checkpoints.size(); i++){
                            if(this.checkpoints.get(i).isInside(v.getPosition())){
                                v.setCheckpoint(i);
                            }
                        }
                        if(System.currentTimeMillis() - v.getLastCheckpointInstant() >= 5000L/ Panel.TIME_MULTIPLIER){
                            v.setAlive(false);
                        }
                    }
                    if(!oneAlive) {
                        int cMaxCheckpoint = maxCheckpoint;
                        double cMaxDistance = maxDistance;
                        double[][] cBestScheme = bestScheme;

                        for(final Car v : this.cars){
                            if(v.equals(this.cars.get(0)))
                                continue;
                            if(v.getCheckpointCount() > cMaxCheckpoint || (v.getCheckpointCount() == cMaxCheckpoint && v.getTotalDistance() > cMaxDistance)){
                                cMaxCheckpoint = v.getCheckpointCount();
                                cMaxDistance = v.getTotalDistance();
                                cBestScheme = v.getNetwork().getScheme();
                            }
                        }
                        if(bestScheme == null || !Arrays.deepEquals(bestScheme, cBestScheme))
                            this.simulationCount++;
                        bestScheme = cBestScheme;
                        maxCheckpoint = cMaxCheckpoint;
                        maxDistance = cMaxDistance;
                        this.createVoitures();
                    }

                    this.repaint();
                    try {
                        Thread.sleep(7L/ Panel.TIME_MULTIPLIER);
                    } catch (final InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        );
        this.loop.start();
    }

    private boolean isInside(final double start, final double x, final double end){
        return x >= start && x <= end;
    }

    private void createVoitures(){
        System.out.println("Simulation numéro : " + this.simulationCount + " - Max Checkpoint : " + maxCheckpoint);
        for(int i = 1; i< this.cars.size(); i++){
            this.cars.get(i).setNeuralNetwork(NeuralNetwork.getMutated(bestScheme, 0.2D/ Math.pow(simulationCount, 2)));
            this.cars.get(i).setAlive(true);
        }
    }

    public void stop(){
        this.running = false;
    }
}
