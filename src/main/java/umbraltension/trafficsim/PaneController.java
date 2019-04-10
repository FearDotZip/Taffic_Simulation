package umbraltension.trafficsim;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;

import static javafx.animation.Animation.INDEFINITE;

public class PaneController {

    Pane pane;
    private ArrayList<item> items = new ArrayList<>();
    private Timeline timeline;
    private double pxPerMeter;

    class item {
        public Automaton automaton;
        public Circle target;
        public Rectangle shape;
        item(Automaton a){
            this.automaton= a;
            this.shape = new Rectangle(mToPx(a.x), mToPx(a.y), mToPx(a.width), mToPx(a.height));
            shape.setFill(Color.BLACK);
            this.target = new Circle(mToPx(a.xf), mToPx(a.yf), 3, Color.RED);
        }
    }

    public PaneController(MainFXController main, Pane pane){
        this.pane = pane;
        init();
    }

    public void init(){
        pane.getChildren().clear();
        setPxPerMeter();
        getItems();
        timeline = new Timeline(new KeyFrame(Duration.millis(30), new myAnimator()));
        timeline.setCycleCount(INDEFINITE);
    }

    private void getItems(){
        items.clear();
        Environment.automs.values().forEach((autom)->items.add(new item(autom)));
        items.forEach((item n) ->{
            pane.getChildren().add(n.shape);
            pane.getChildren().add(n.target); });
    }

    private double mToPx(double meters){
        return meters * pxPerMeter;
    }

    private void setPxPerMeter(){
        pxPerMeter = pane.heightProperty().doubleValue() / Environment.mapSizeMeters;
    }


    class myAnimator implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event){
            items.forEach((item i) -> {
                i.shape.setTranslateX((i.automaton.x));
                i.shape.setTranslateY((i.automaton.y));
            });
        }
    }


    public Timeline getTimeline(){return timeline;};

}

