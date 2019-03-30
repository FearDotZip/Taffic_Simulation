package com.trafficsim;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import java.util.ArrayList;
import java.util.Map;
import javax.swing.ImageIcon;

import static com.trafficsim.devtools.toInt;

public class Painter extends Thread {

    private Environment env;
    private Graphics2D g2d;
    private ArrayList<Item> items = new ArrayList<>();
    private Map<String, String> iconPaths = Map.of("Automaton", "src/com/trafficsim/Automaton.png", "Default", "src/com/trafficsim/Default.png");

    public Painter(String name, Environment env) {
        super.setName(name);
        this.env = env;
        this.g2d = (Graphics2D)env.getGraphics();
    }

    private class Item{
        public String itemType;
        public Image img;
        public Thing thing;
        public Rectangle bounds, oldBounds, destination = null;
        public Item(String itemType, Thing thing){
            this.itemType = itemType;
            this.thing = thing;
            String imgToLoad = iconPaths.containsKey(itemType) ? itemType : "Default";
            img = loadImage(imgToLoad, toInt(thing.width), toInt(thing.height));
            bounds = new Rectangle(toInt(thing.x), toInt(thing.y), toInt(thing.width), toInt(thing.height));
            oldBounds = new Rectangle(bounds);

            if (itemType.equals("Automaton")){
                Automaton autom = (Automaton)thing;
                destination = new Rectangle(toInt(autom.getXf()), toInt(autom.getYf()), toInt(thing.width), toInt(thing.height));
            }
        }

        public String getName(){ return thing.getName(); }
    }

    public void run() {
        while(true) {
            for(Item i : items) {
                paint(i);
                env.sleep(5);
            }
        }
    }

    public Image loadImage(String itemType, int width, int height) {
        ImageIcon icon = new ImageIcon(iconPaths.get(itemType));
        Image img = icon.getImage();
        img = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return img;
    }

    private void paint(Item i) {
        i.oldBounds.grow(30,30);
        env.repaint(i.oldBounds);
        env.sleep(30);
        g2d.drawImage(i.img, toInt(i.thing.x), toInt(i.thing.y), env);
        g2d.setColor(Color.RED);
        if (i.destination != null)
            g2d.fill(i.destination);
        env.sleep(30);

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
