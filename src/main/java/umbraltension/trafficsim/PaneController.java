package umbraltension.trafficsim;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;

import java.util.ArrayList;

import static javafx.animation.Animation.INDEFINITE;

public class PaneController {

    Pane pane;
    Bounds paneBounds;
    private ArrayList<item> items = new ArrayList<>();
    private Timeline paneTimelime;
    private double pxPerMeter;
    MainFXController main;

    class item {
        public Automaton automaton;
        public Circle target;
        public Rectangle shape;
        item(Automaton a){
            automaton= a;
            shape = new Rectangle(mToPx(a.width), mToPx(a.height));
            shape.setTranslateX(mToPx(a.x));
            shape.setTranslateY(mToPx(a.y));
            shape.setFill(Color.BLACK);
            target = new Circle(mToPx(a.xf), mToPx(a.yf), 3, Color.RED);
            System.out.println(a);
        }
    }

    public PaneController(MainFXController main, Pane pane){
        this.main = main;
        this.pane = pane;
        init();
    }

    public void init(){
        paneBounds = pane.getLayoutBounds();
        pxPerMeter = Double.parseDouble(main.getMenuController().getSizeRatio().getText());

        pane.getChildren().clear();
        getItems();
        pane.getChildren().add(buildRuler());

        paneTimelime = new Timeline(new KeyFrame(Duration.millis(30), new myAnimator()));
        paneTimelime.setCycleCount(INDEFINITE);
    }
    private void getItems(){
        items.clear();
        Environment.automs.values().forEach(autom -> items.add(new item(autom)));
        for (item i : items){
            pane.getChildren().add(i.shape);
            pane.getChildren().add(i.target);
        }
    }

    private Path buildRuler(){
        MoveTo origin = new MoveTo(0,0);
        double xMax = paneBounds.getMaxX();
        double yMax = paneBounds.getMaxY();
        double tenm = mToPx(10);
        Path ruler = new Path(origin, new LineTo(xMax,0),
                origin, new LineTo(0, yMax));

        for(double i = tenm; i<=xMax; i+=tenm){
            ruler.getElements().addAll(new MoveTo(i,0), new LineTo(i, 10));
        }
        for(double i = tenm; i<=yMax; i+=tenm) {
            ruler.getElements().addAll(new MoveTo(0,i), new LineTo(10, i));
        }

        return ruler;
    }

    private double mToPx(double meters){
        return meters * pxPerMeter;
    }

    class myAnimator implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event){
            for (item i : items){
                i.shape.setTranslateX(mToPx(i.automaton.x));
                i.shape.setTranslateY(mToPx(i.automaton.y));
            }
        }
    }

    public void play(){
        paneTimelime.play();
    }
    public Timeline getPaneTimelime(){return paneTimelime;};

}

