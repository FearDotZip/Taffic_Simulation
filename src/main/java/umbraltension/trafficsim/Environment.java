package umbraltension.trafficsim;

import java.util.HashMap;

import static umbraltension.trafficsim.devtools.toInt;
public class Environment extends javax.swing.JPanel {
	// Time
        public static int REAL_W_TIME_INCREMENT; // (real-world update period given in milliseconds)
        public static int ENV_TIME_INCREMENT; // (milliseconds)
        public static long envTime = 0; // (milliseconds since env's clock thread started creation)
        private static Thread clock;
    // Space
        public static double MAP_SIZE_METERS, METERS_PER_PIXEL;
    public static HashMap<String,Automaton> automs = new HashMap<>();
    public static ThreadGroup automThreads = new ThreadGroup("Automaton Threads");


    public static void init(Settings set){
        Environment.MAP_SIZE_METERS = set.MAP_SIZE_METERS;
        METERS_PER_PIXEL = MAP_SIZE_METERS / set.EnvJPanelSize;
        REAL_W_TIME_INCREMENT = set.REAL_W_TIME_INCREMENT;
        ENV_TIME_INCREMENT = toInt(REAL_W_TIME_INCREMENT * set.ENV_TO_REALWORLD_TIME_RATIO);
        //Generate Automatons
        automs = AutomatonGenerator.get(300);
    }

    static void start(){
        //make and start threads
        clock = new Thread(new Runnable() {
            public void run() {
                while(true){
                    sleep(REAL_W_TIME_INCREMENT);  // this much realworld time has passed so...
                    envTime += ENV_TIME_INCREMENT; //this much env time has passed.
                }
            }
        }, "clock");
        clock.start();

        //automs.values().forEach((Automaton n)-> new Thread(automThreads,n,n.getName()));
        for(Automaton autom : automs.values()){
            Thread t = new Thread(automThreads, autom, autom.getName());
        }
        Thread[] threads = new Thread[automs.size()];
        automThreads.enumerate(threads);
        clock.start();
        for(Thread t : threads){
            t.start();
        }

    }



    //Foot to pixel conversion
    static double metersToPixels(double meters){
        return (meters * (1.0 / METERS_PER_PIXEL));
    }

    //pause for a given number of milliseconds
    static void sleep(int milliseconds){
        try { Thread.sleep(milliseconds);}
        catch (InterruptedException i){System.out.println("Aut sleep call interrupted");}
    }


    static double getMAP_SIZE_METERS() {
        return MAP_SIZE_METERS;
    }

    static double getMETERS_PER_PIXEL() {
        return METERS_PER_PIXEL;
    }

    static int getREAL_W_TIME_INCREMENT() {
        return REAL_W_TIME_INCREMENT;
    }

    static int getENV_TIME_INCREMENT() { return ENV_TIME_INCREMENT; }

    static long getEnvTime() { return envTime; }

    public static String summary() {
        return "\nEnvironment{" +
                "\n\tMAP_SIZE_METERS=" + MAP_SIZE_METERS +
                "\n\tREAL_W_TIME_INCREMENT=" + REAL_W_TIME_INCREMENT +
                "\n\tENV_TIME_INCREMENT=" + ENV_TIME_INCREMENT +
                "\n\tenvTime=" + envTime +
                "\n\t1 pixel = " + METERS_PER_PIXEL + " meters" +
                "\n}\n";
    }

}

