package umbraltension.trafficsim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

import com.google.gson.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class TrafficSim implements Runnable{

    public static Settings set;

	public static void main(String[] args) {
	    TrafficSim simulation = new TrafficSim();
        //Schedule a job for the event dispatch thread
        SwingUtilities.invokeLater(simulation);
	}

    public void run(){
        Gson gson = new Gson();
        String json = "";
        try {
            json = java.nio.file.Files.readString(Path.of("src/main/resources/settings.json"));
            TrafficSim.set = gson.fromJson(json, Settings.class);
        }catch (IOException e){
            System.out.println("Default settings used because settings file was not found.");
            TrafficSim.set  = new Settings();
        }
        createAndShowGUI(TrafficSim.set);
    }

    private static void createAndShowGUI(Settings set){

	    JFrame f = new JFrame("Driver Sim");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocation(550,60);

        Environment env = new Environment(set); //700,50,
                //5, 1);
        System.out.println(env);
        Container cPane = f.getContentPane();
        cPane.add(env, BorderLayout.CENTER);
        f.pack();
        f.setVisible(true);

        env.start();
    }
	
}
