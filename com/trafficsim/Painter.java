package com.trafficsim;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.ImageIcon;

import static com.trafficsim.devtools.toInt;

public class Painter extends Thread {

    private Environment env;
    private Graphics2D g2d;
    private ArrayList<Item> items = new ArrayList<>();
    private Map<String, String> iconPaths = Map.of("Automaton", "com/trafficsim/Automaton.png", "Default", "com/trafficsim/Default.png");

    public Painter(String name, Environment env) {
        super.setName(name);
        this.env = env;
        this.g2d = (Graphics2D)env.getGraphics();
    }

    private class Item{
        public String itemType;
        public Image img;
        public Thing thing;
        public Rectangle bounds, oldBounds, destination;
        public int x, y;
        public int width, height;

        public Item(String itemType, Thing thing){
            this.itemType = itemType;
            this.width =  toInt(env.metersToPixels(thing.width));
            this.height =  toInt(env.metersToPixels(thing.height));
            this.thing = thing;
            String imagePath = iconPaths.containsKey(itemType) ? itemType : "Default";
            img = loadImage(imagePath, width, height);
            bounds = new Rectangle(width, height);
            oldBounds = new Rectangle(bounds);
            destination = null;
            if (itemType.equals("Automaton")){
                Automaton autom = (Automaton)thing;
                destination = new Rectangle(toInt(autom.getXf()), toInt(autom.getYf()), 3, 3);
            }
        }

        public String getName(){ return thing.getName(); }
    }

    public void run() {
        while(true) {
            for(Item i : items) {
                sample(i);
                if (! i.oldBounds.equals(i.bounds))
                    paint(i);
            }
        }
    }

    public Image loadImage(String itemType, int width, int height) {
        ImageIcon icon = new ImageIcon(iconPaths.get(itemType));
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        icon = new ImageIcon(img);
        img = icon.getImage();
        return img;
    }

    private void paint(Item i) {
        env.repaint(5);
        env.sleep(5);
        g2d.drawImage(i.img, i.x, i.y, env);
        g2d.setColor(Color.RED);
        if (i.destination != null)
            g2d.fill(i.destination);
    }

    private void sample(Item i){
        i.oldBounds.setLocation(i.x, i.y);
        i.x = toInt(env.metersToPixels(i.thing.x));
        i.y = toInt(env.metersToPixels(i.thing.y));
        i.bounds.setLocation(i.x, i.y);
    }


    public void addAutomaton(Automaton autom) {
        items.add(new Item("Automaton", autom));
    }

    public String toString() {
        String str = super.getName() + " paints: ";
        for (Item i : items) {
            str += i.getName() + ", ";
        }
        return str + "\n";
    }

}
