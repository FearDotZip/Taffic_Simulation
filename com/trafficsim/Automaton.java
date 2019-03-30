package com.trafficsim;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import static java.lang.Math.round;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.lang.Math.hypot;

public class Automaton extends Thing{
    private String name;
    private Environment env;
    private Graphics2D g2d; //todo ask reddit why it works for multiple threads to have a ref to env's graphics object and be using it at the same time.
    private String report;
    private double xf, yf, distToDestination, initialDistance;

    Automaton(String name, double x0, double y0, double xf, double yf, double width,
              double height, double velocity, double theta, double accel, double accelTheta, Environment env) {
        super(x0,y0,width,height,velocity,theta,accel,accelTheta);
        this.name = name;
        this.env = env;
        this.g2d = (Graphics2D) env.getGraphics();
        this.xf = xf;
        this.yf = yf;
        this.initialDistance = hypot(xf-x, yf-y);
        this.distToDestination = initialDistance;
    }

    /* todo JESUS FUCKING CHRIST YOU FORGOT ABOUT THE LINE METHOD OF MOVING THE AUTOMS TO THEIR TARGET. DRAW LINE A
        FROM co.bounds TO TARGET, ONLY MOVE TO POINTS ON THAT LINE. HAS THE DISADVANTAGE OF NOT BEING THE WAY A
        PERSONAL (OR MACHINE?) WOULD CALCULATE IT"S NEXT STEPS
    */
    public void run(){
        brain();
    }


    void brain(){

        int brainIterations = 0;
        while(!atDestination()) {
            brainIterations++;
            env.sleep(60);
            getTrajectory(30, .5);
        }
        System.out.println(name + " Finished\n");
    }

    private boolean atDestination(){
        if (this.containsPoint(xf, yf))
            return true;
        return false;
    }



/////MEMBER_GETTERS////////////////////////////////////////////////////////////////////////////////////////////////////
    public String getName()             { return name; }
    public Environment getEnv()         { return env; }
    public Graphics2D getd2d()          { return g2d; }
    public double getXf()               { return xf; }
    public double getYf()               { return yf; }
    public double getDistToDestination(){ return distToDestination; }
    /////OBJECT_OVERRIDES//////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public int hashCode(){return java.util.Objects.hash(name, x, xf, y, yf); }
    @Override
    public String toString() {
        return "\nAutomaton{" +
                "\n\tname = " + name +
                "\n\txf = " + xf +
                "\n\tyf = " + yf +
                "\n\tvelocity= " + velocity +
                "\n}\n";
    }
}//end of class
