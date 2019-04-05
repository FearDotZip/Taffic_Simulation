package umbraltension.trafficsim;


import java.util.HashMap;
import java.util.Random;

import static java.lang.Math.toRadians;

public class AutomatonGenerator {

    private static Random rand = new Random();


    static HashMap<String, Automaton> get(int num){
        int sizeRange = 7; // range (0-7) of feet to add to base automaton size of 10 feet.
        int xmax = (int)Environment.MAP_SIZE_METERS;
        int ymax = (int)Environment.MAP_SIZE_METERS;
        HashMap<String, Automaton> automs = new HashMap<>();


        for (int i = 1; i<=num; i++){
            boolean collision = true;
            String name = "A_"+i;
            int size = 10 + rand.nextInt(sizeRange);
            //todo experiment with non-square sizes;
                int width = size;
                int height = size;
            int x0 = 0, y0 = 0;
            while(collision) {
                x0 = rand.nextInt(xmax);
                y0 = rand.nextInt(ymax);
                collision = false;
                for (Automaton autom : automs.values()){
                    if (autom.containsPoint(x0, y0)){
                        collision = true;
                        break;
                    }
                }
            }
            int xf = rand.nextInt(xmax);
            int yf = rand.nextInt(ymax);
            //todo working with uniform velocity right now to reducee
            //  complexity while I get stuff up and running.
            double velocity = 4;
            double theta = toRadians(rand.nextInt(359));
            Automaton automaton = new Automaton(name, x0, y0, xf, yf, width, height, velocity, theta, 0,0);
            automs.put(name,automaton);
        }
        return automs;
    }

    static HashMap<String, Automaton> get(){
        HashMap<String, Automaton> automs = new HashMap<>();

        // moves right, down
        automs.put("A_0", new Automaton("A_0",10,10,400,100,2.5,2.5, 5, toRadians(45),0, 0));
//        // right, up
//        automs.put("A_1", new Automaton("A_1",10,500,400,200,2.5,2.5,5, toRadians(315),0, 0));
//        // left, down
//        automs.put("A_2", new Automaton("A_2",500,20,30,200,2.5,2.5, 5, toRadians(135),0, 0));
//        // left, up
//        automs.put("A_3", new Automaton("A_3",700,700,400,500,2.5,2.5, 5, toRadians(225),0, 0));
        return automs;
    }



}
