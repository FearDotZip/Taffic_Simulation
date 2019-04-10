package umbraltension.trafficsim;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class MenuController {
    Button runButton, resetButton, applyButton;
    MainFXController main;
    TextField numAutoms;

    public MenuController(MainFXController main, Button runButton, Button resetButton, Button applyButton,
                          TextField numAutoms){
        this.main = main;
        this.runButton = runButton;
        this.resetButton = resetButton;
        this.applyButton = applyButton;
        this.numAutoms = numAutoms;
        runButton.setOnAction(new runButtonHandler());
        resetButton.setOnAction(new resetButtonHandler());
        applyButton.setOnAction(new applyButtonHandler());
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
            applyButton.setDisable(true);
            main.applySettings();
        }
    }




}
