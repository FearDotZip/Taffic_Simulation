package umbraltension.trafficsim;

import java.awt.geom.Point2D;
import java.util.HashMap;

import static java.lang.Math.hypot;
import static java.lang.Math.pow;
import static umbraltension.trafficsim.devtools.toInt;

public class Automaton extends Thing{
    private String name;
    private String report;
    public double xf, yf, distToDestination, initialDistance;

    Automaton(String name, double x0, double y0, double xf, double yf, double width,
              double height, double velocity, double theta, double accel, double accelTheta) {
        super(x0,y0,width,height,velocity,theta,accel,accelTheta);
        this.name = name;
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
            Environment.sleep(60);
            if (brainIterations % 20 == 0) {
                trajectory = getTrajectory(3000);
            }
            var point = trajectory.get(Environment.getEnvTime());
            x = point.x;
            y = point.y;
            brainIterations++;
        }
        System.out.println(name + " Finished\n");
    }

    private boolean atDestination(){
        if (this.containsPoint(xf, yf))
            return true;
        return false;
    }

    @Override
    public HashMap<Long, Point2D.Double> getTrajectory(double timeSpanSeconds) {
        HashMap<Long, Point2D.Double> newTrajectory = new HashMap<>();


        double time = Environment.getEnvTime() / 1000.0;
        double increment = Environment.getENV_TIME_INCREMENT() / 1000.0;
        for(double t = time; t <= time + timeSpanSeconds / 1000.0; t += increment){
            double newx = x0 + (vx * t) + (.5 * ax * pow(t,2));
            double newy = y0 + (vy * t) + (.5 * ay * pow(t,2));
            newTrajectory.put(Long.valueOf(toInt(t*1000)), new Point2D.Double(newx, newy));
        }
        return newTrajectory;
    }

/////MEMBER_GETTERS////////////////////////////////////////////////////////////////////////////////////////////////////
    public String getName()             { return name; }
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
