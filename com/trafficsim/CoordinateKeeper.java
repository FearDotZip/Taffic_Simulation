package com.trafficsim;


import java.awt.*;

import static java.lang.Math.abs;
import static java.lang.Math.hypot;
import static java.lang.Math.round;

class CoordinateKeeper {
    double x, x0, xf, deltax;
    double y, y0, yf, deltay;
    double z0, z;
    int dx, dy;
    Rectangle bounds, old_bounds, target;
    int size;
    public CoordinateKeeper(double x0, double y0, double xf, double yf, double size, Environment env){
        this.x0 = x0;   this.xf = xf;
        this.y0 = y0;   this.yf = yf;
        setPosition(x0,y0);
        z0 = z;
        this.size = myRound(env.metersToPixels(size));
        bounds = new Rectangle(intx(), inty(),this.size, this.size);
        old_bounds = new Rectangle(bounds);
        target = new Rectangle(intxf(),intyf(), 5, 5);
    }

    public void displace(){
        old_bounds.setLocation(intx(), inty());
        setPosition(x+dx, y+dy);
        bounds.setLocation(intx(), inty());
    }

    public void setPosition(double x, double y){
        this.x = x;     this.y = y;
        deltax = xf-x;  deltay = yf-y;
        z = hypot(x,y);
    }
    
    public void calc_deltas() {
        int dy_sign = (deltay > 0) ? 1 : -1;
        int dx_sign = (deltax > 0) ? 1 : -1;

        if (deltax == 0) {
            dx = 0;
            dy = (deltay == 0) ? 0 : dy_sign;
            return;
        }
        if (deltay == 0) {
            dx = dx_sign;
            dy = 0;
            return;
        }

        /* Proportions Logic: deltax/deltay = ratio | let x/y = ratio |  then, x = ratio * y
           Calculate x by iterating over y, and pick the x that's closest to an integer */
        double ratio = deltax / deltay;
        double acceptable_error = 0.05;
        double localX=0, err_new, err_old = Double.MAX_VALUE;
        int xrounded;
        int x_good=dx_sign, y_good=dy_sign;
        int localY = dy_sign;
        
        while(abs(localY) <= 3){
            localX = ratio * localY;
            xrounded = (int)round(localX);
            err_new = abs(localX-xrounded);
            if(err_new < err_old && (localX <= 3)) {
                x_good = xrounded;
                y_good = localY;
                if(err_new <= acceptable_error)
                    break;
            }
            localY += dy_sign;
        }
        dx = x_good;
        dy = y_good;
    }
    

    public int intx(){return myRound(x);}
    public int inty(){return myRound(y);}
    public int intxf(){return myRound(xf);}
    public int intyf(){return myRound(yf);}

    public int myRound(double val){return (int)round(val);}
}
