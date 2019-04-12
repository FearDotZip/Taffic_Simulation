package umbraltension.trafficsim;

import static java.lang.Math.round;

public class tools {

    public static int toInt(double value){
        return (int)round(value);
    }

    public static void test(Object...varargs){
        if (varargs.length == 0)
            System.out.println("here");

        for(int i = 0; i<varargs.length; i++){
            System.out.println("Var "+(i+1)+": " + varargs[i]);
        }
        System.out.println();

    }

    //pause for a given number of milliseconds
    public static void sleep(int milliseconds){
        try { Thread.sleep(milliseconds);}
        catch (InterruptedException i){System.out.println("Aut sleep call interrupted");}
    }

}


