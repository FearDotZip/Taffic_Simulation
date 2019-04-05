package umbraltension.trafficsim;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.ArrayList;

public class PaneController {
    @FXML
    Pane pane;

    private ArrayList<item> items = new ArrayList<>();

    class item {
        public Automaton automaton;
        public Circle c;
        item(Automaton a, Circle c){
            this.automaton= a;
            this.c = c;
        }
    }

    ArrayList<item> getItems(){
        ArrayList<item> items = new ArrayList<>();
        for(Automaton autom : Environment.automs.values()){
            items.add(new item(autom, new Circle(autom.x0, autom.y0, 5, Color.BLUE)));
        }
        return items;
    }


    ArrayList<KeyValue> getKVS(){
        ArrayList<KeyValue> kvs = new ArrayList<>();
        for(item i : items){
            KeyValue kv1 = new KeyValue(i.c.centerXProperty(), i.automaton.xf);
            KeyValue kv2 = new KeyValue(i.c.centerYProperty(), i.automaton.yf);
            kvs.add(kv1);
            kvs.add(kv2);
        }
        return kvs;
    }


    @FXML
    public void initialize() {
        items = getItems();
        items.forEach((item n) -> pane.getChildren().add(n.c));

        EventHandler<ActionEvent> eh = new EventHandler<ActionEvent>(){ public void handle(ActionEvent event){
            System.out.println("done");
        }};

        KeyFrame kf = new KeyFrame(Duration.seconds(10), "ball movements", eh, getKVS());
        Timeline timeline = new Timeline(kf);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


}
