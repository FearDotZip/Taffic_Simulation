package umbraltension.trafficsim;


import java.util.HashMap;
import java.util.Random;

import static java.lang.Math.random;


public class AutomatonFactory {

    private static Random rand = new Random();


    static HashMap<String, Automaton> get(int num){
        double baseSize = 2.5; //base size in meters
        double sizeRange = 2; // range of meters to add to base size.
        double maxVelocity = 35;
        double xmax = Environment.mapSizeMeters;
        double ymax = Environment.mapSizeMeters;

        HashMap<String, Automaton> automs = new HashMap<>();
        for (int i = 1; i<=num; i++){
            boolean collision = true;
            String name = "A_" + i;
            double size = baseSize + random()*sizeRange;
            //todo experiment with non-square sizes;
                double width = size;
                double height = size;
            double x0 = 0, y0 = 0;
            while(collision) {
                x0 = random() * xmax;
                y0 = random() * ymax;
                collision = false;
                for (Automaton autom : automs.values()){
                    if (autom.containsPoint(x0, y0)){
                        collision = true;
                        break;
                    }
                }
            }
            double xf = random() * xmax;
            double yf = random() * ymax;
            double velocity = .5  + random() * maxVelocity;
            Automaton automaton = new Automaton(name, x0, y0, xf, yf, width, height, velocity, 0,0);
            automs.put(name,automaton);
        }
        return automs;
    }

    static HashMap<String, Automaton> get(){
        HashMap<String, Automaton> automs = new HashMap<>();
        // moves right, down
        automs.put("A_0", new Automaton("A_0",10,10,400,10,4,4, 40,0, 0));
        // right, up
        automs.put("A_1", new Automaton("A_1",10,500,400,200,4,4,40,0, 0));
        // left, down
        automs.put("A_2", new Automaton("A_2",500,20,30,200,4, 4,40, 0, 0));
        // left, up
        automs.put("A_3", new Automaton("A_3",700,700,400,500,4, 4,40, 0, 0));
        return automs;
    }



}
