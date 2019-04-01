package com.trafficsim;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class TrafficSim {

	public static void main(String[] args) {
        //Schedule a job for the event dispatch thread
        SwingUtilities.invokeLater(new Runnable() { public void run(){createAndShowGUI();}});
	}


    private static void createAndShowGUI(){
        JFrame f = new JFrame("Driver Sim");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocation(550,60);

        Environment env = new Environment(700,50,
                5, 1);
        System.out.println(env);
        Container cPane = f.getContentPane();
        cPane.add(env, BorderLayout.CENTER);
        f.pack();
        f.setVisible(true);

        env.start();
    }
	
}
