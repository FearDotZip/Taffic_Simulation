package umbraltension.trafficsim;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter.Change;
import javafx.util.Duration;

import java.util.Formatter;
import java.util.function.UnaryOperator;

public class MenuController {
    MainFXController main;
    Label clock;
    Button applyButton, resetButton, runButton;
    TextField mapSize, numAutoms, sizeRatio, timeRatio;
    Timeline clockTimeline;

    public MenuController(MainFXController main, TextField[] textFields, Button[] buttons, Label[] labels){
        this.main = main;
        mapSize = textFields[0];
        numAutoms = textFields[1];
        sizeRatio = textFields[2];
        timeRatio = textFields[3];

        applyButton = buttons[0];
        resetButton = buttons[1];
        runButton = buttons[2];

        clock = labels[0];
        clockTimeline = new Timeline(new KeyFrame(Duration.millis(100), new clockAnimator() ));
        clockTimeline.setCycleCount(Animation.INDEFINITE);

        applyButton.setOnAction(new applyButtonHandler());
        resetButton.setOnAction(new resetButtonHandler());
        runButton.setOnAction(new runButtonHandler());
    }

    class clockAnimator implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent ev){
            StringBuilder sbuild = new StringBuilder();
            Formatter f = new Formatter(sbuild);
            f.format("%06.2f", Environment.envTime / 1000.0);
            clock.textProperty().set("Clock: " + sbuild.toString() + " (s)");
        }

    }

    public void play(){
        clockTimeline.play();
    }

    class runButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent ev){
            String currText = runButton.getText();
            if(currText.equals("Run")) {
                run();
            }
            if(currText.equals("Stop")){
                runButton.setText("Resume");
                applyButton.setDisable(false);
                resetButton.setDisable(false);
                main.stop();
            }
            if(currText.equals("Resume")){
                run();
            }
        }
        void run(){
            runButton.setText("Stop");
            applyButton.setDisable(true);
            resetButton.setDisable(true);
            main.run();
        }
    }

    class resetButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent ev){
            runButton.setText("Run");
            main.reset();
        }
    }
    class applyButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent ev){
            runButton.setText("Run");
            main.applySettings();
        }
    }


    class integerFilter implements UnaryOperator<Change> {

        @Override
        public Change apply(Change ch){
            if (ch.getText().matches("[0-9]*")){
                return ch;
            }
            else
                return null;
        }
    }


    public Button getApplyButton() {
        return applyButton;
    }

    public Button getResetButton() {
        return resetButton;
    }

    public Button getRunButton() {
        return runButton;
    }

    public MainFXController getMain() {
        return main;
    }

    public TextField getMapSize() {
        return mapSize;
    }

    public TextField getNumAutoms() {
        return numAutoms;
    }

    public TextField getSizeRatio() {
        return sizeRatio;
    }

    public TextField getTimeRatio() {
        return timeRatio;
    }
}
