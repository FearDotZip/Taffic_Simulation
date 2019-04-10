package umbraltension.trafficsim;

import java.util.ArrayList;
import java.util.HashMap;

import static umbraltension.trafficsim.tools.*;

public class Environment{
	// Time
        public static int realWorldUpdatePeriod = 5; // (real-world update period given in milliseconds)
        public static int envTimeIncrement; // (milliseconds)
        public static long envTime; // (milliseconds since env's clock thread started creation)
        private static Thread clock;
    // Space
        public static double mapSizeMeters, metersPerPixel;

    public static int numAutoms;
    public static HashMap<String,Automaton> automs = new HashMap<>();
    public static boolean running = false;
    public static ThreadGroup automThreads = new ThreadGroup("Automaton Threads");


    public static void init(MainFXController main){
        mapSizeMeters = Double.parseDouble(main.mapSize.getText());
        envTime = 0;
        envTimeIncrement = toInt(realWorldUpdatePeriod * Double.parseDouble(main.timeRatio.getText()));
        numAutoms = Integer.parseInt(main.numAutoms.getText());
        automs = AutomatonGenerator.get();
    }

    //make and start threads
    static void start(){
        clock = new Thread(new Runnable() {
            public void run() {
                while(true){
                    if(!running)
                        return;
                    sleep(realWorldUpdatePeriod);  // this much realworld time has passed so...
                    envTime += envTimeIncrement; //this much env time has passed.
                }
            }
        }, "clock");
        ArrayList<Thread> athreads = new ArrayList<>();
        automs.forEach((name, autom)-> athreads.add(new Thread(automThreads,autom, name)));

        running = true;
        athreads.forEach(Thread::start);
        clock.start();
    }

    static void stop(){
        running = false;
    }

    //Foot to pixel conversion
    static double metersToPixels(double meters){
        return (meters * (1.0 / metersPerPixel));
    }




    public static String summary() {
        return "\nEnvironment{" +
                "\n\tmapSizeMeters=" + mapSizeMeters +
                "\n\trealWorldUpdatePeriod=" + realWorldUpdatePeriod +
                "\n\tenvTimeIncrement=" + envTimeIncrement +
                "\n\tenvTime=" + envTime +
                "\n\t1 pixel = " + metersPerPixel + " meters" +
                "\n}\n";
    }

}

