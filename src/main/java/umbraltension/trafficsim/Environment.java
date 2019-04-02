package umbraltension.trafficsim;

import java.awt.Color;
import java.awt.Dimension;

import java.util.ArrayList;
import java.util.HashMap;

import static umbraltension.trafficsim.devtools.toInt;
public class Environment extends javax.swing.JPanel {
    private Settings set;
	// Time
        public final int REAL_W_TIME_INCREMENT; // (real-world update period given in milliseconds)
        public final int ENV_TIME_INCREMENT; // (milliseconds)
        public long envTime = 0; // (milliseconds since env's clock thread started creation)
        private Thread clock;
    // Space
        public final double MAP_SIZE_METERS, METERS_PER_PIXEL;
    private HashMap<String,Automaton> automs = new HashMap<>();
    private ArrayList<Painter> painters = new ArrayList<>();
    private ThreadGroup automThreads = new ThreadGroup("Automaton Threads");

    private class clockThread implements Runnable {
        public void run(){
            while(true){ tick();}
        }
        void tick(){
            sleep(REAL_W_TIME_INCREMENT);  // this much realworld time has passed so...
            envTime += ENV_TIME_INCREMENT; //this much env time has passed.
        }
    }

    Environment(Settings set){
        this.set = set;
        MAP_SIZE_METERS = set.MAP_SIZE_METERS;
        METERS_PER_PIXEL = MAP_SIZE_METERS / set.EnvJPanelSize;
        REAL_W_TIME_INCREMENT = set.REAL_W_TIME_INCREMENT;
        ENV_TIME_INCREMENT = toInt(REAL_W_TIME_INCREMENT * set.ENV_TO_REALWORLD_TIME_RATIO);
        setPreferredSize(new Dimension(set.EnvJPanelSize, set.EnvJPanelSize));
        setBackground(new Color(Integer.decode("#6AE6B1")));
    }

    void start(){
        /*I put genAutom() here rather than the constructor because there was an issue where env's Graphics object was null until the env constuctor
         was completely finished. So calling Automaton's constructor caused nullpointer exc. because it uses that graphics obj.
        */
        int itemsPerPainter = set.itemsPerPainter;

        //Generate Automatons and Painters
        automs = AutomatonGenerator.get(this);

        int div = automs.size() / itemsPerPainter;
        int mod = automs.size() % itemsPerPainter;
        int numPainters = (mod == 0) ? div : div +1;

        for(int i = 0; i<numPainters; i++){
            String name = "Painter_" + i;
            painters.add(new Painter(name, this));
        }

        //assign paintable objects (automs) to a Painter
        int painterIndex = -1;
        for (int i = 0; i<automs.size(); i++){
            String name = "A_" + i;
            if( (i % itemsPerPainter) == 0)
                painterIndex++;
            painters.get(painterIndex).addAutomaton(automs.get(name));
        }

        //make and start threads
        for(Automaton autom : automs.values()){
            Thread t = new Thread(automThreads, autom, autom.getName());
            t.start();
        }
        for(Painter painter :  painters) { painter.start();}
        clock = new Thread(new clockThread(), "clock");
        clock.start();
    }



    //Foot to pixel conversion
    double metersToPixels(double meters){
        return (meters * (1.0 / METERS_PER_PIXEL));
    }

    //pause for a given number of milliseconds
    void sleep(int milliseconds){
        try { Thread.sleep(milliseconds);}
        catch (InterruptedException i){System.out.println("Aut sleep call interrupted");}
    }


    double getMAP_SIZE_METERS() {
        return MAP_SIZE_METERS;
    }

    double getMETERS_PER_PIXEL() {
        return METERS_PER_PIXEL;
    }

    int getREAL_W_TIME_INCREMENT() {
        return REAL_W_TIME_INCREMENT;
    }

    int getENV_TIME_INCREMENT() { return ENV_TIME_INCREMENT; }

    long getEnvTime() { return envTime; }

    @Override
    public String toString() {
        return "\nEnvironment{" +
                "\n\tMAP_SIZE_METERS=" + MAP_SIZE_METERS +
                "\n\tREAL_W_TIME_INCREMENT=" + REAL_W_TIME_INCREMENT +
                "\n\tENV_TIME_INCREMENT=" + ENV_TIME_INCREMENT +
                "\n\tenvTime=" + envTime +
                "\n\t1 pixel = " + METERS_PER_PIXEL + " meters" +
                "\n}\n";
    }

    public void test(Object ... varargs){
        if (varargs.length == 0) {
            System.out.println("here");
            return;
        }
        System.out.println("test: ");
        for (int i = 0; i<varargs.length; i++) {
            System.out.println("\tv"+(i+1)+" = " + varargs[i]);
        }
    }




}

