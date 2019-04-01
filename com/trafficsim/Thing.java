package com.trafficsim;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.pow;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;

public class Thing implements Runnable {
    public String name = "a thing";
    public double width, height, halfWidth, halfHeight;
    public double x0, y0, x, y;
    public double velocity, vx, vy, theta;
    public double accel, ax, ay, accelTheta;
    public HashMap<Long, Point2D.Double> trajectory = new HashMap<>();

    public Thing(){}

    public Thing(double x0, double y0, double width, double height, double velocity, double theta, double accel, double accelTheta) {
        this.width = width;             this.height = height;
        this.halfWidth = width / 2.0;   this.halfHeight = height / 2.0;
        this.x0 = x0;   this.x = x0;
        this.y0 = y0;   this.y = y0;
        this.velocity = velocity;
        this.theta = theta;
        vx = velocity * cos(theta);
        vy = velocity * sin(theta);
        this.accel = accel;
        ax = accel * cos(accelTheta);
        ay = accel * sin(accelTheta);
    }

    public void run(){}

    public HashMap<Long, Point2D.Double> getTrajectory(double timespan) {
        HashMap<Long, Point2D.Double> newTrajectory = new HashMap<>();
        for(long t = 0; t <= timespan; t += 100){
            double newx = x0 + (vx * t) + (.5 * ax * pow(t,2));
            double newy = y0 + (vy * t) + (.5 * ay * pow(t,2));
            newTrajectory.put(t, new Point2D.Double(newx, newy));
        }
        return newTrajectory;
    }

    public HashMap<Long, Point2D.Double> getTrajectory() { return trajectory; }


    public boolean containsPoint(double xcoord, double ycoord){
        boolean insideXBounds = (x-halfWidth <= xcoord) && (xcoord <= x+halfWidth);
        boolean insideYBounds = (y-halfHeight<= ycoord) && (ycoord <= y+halfHeight);
        if (insideXBounds && insideYBounds)
            return true;
        return false;
    }


    public String getName(){return name;}
    public void setName(String name){this.name = name;}
}
