package umbraltension.trafficsim;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.fxml.Initializable;

public class MainFXController implements Initializable {


    @FXML
    Pane pane;
    @FXML
    Button runButton, resetButton, applyButton;
    @FXML
    TextField numAutoms, mapSize, timeRatio;


    public PaneController paneController;
    public MenuController menuController;

    public MainFXController(){
    }

    @FXML
    public void initialize(java.net.URL a, java.util.ResourceBundle rb){
    }

    @FXML
    private void initialize(){

    }

    public void run(){
        Environment.start();
        paneController.getTimeline().play();
    }
    public void stop(){
        Environment.stop();
    }
    public void reset(){
        Environment.stop();
        Environment.init(this);
        paneController.init();
    }
    public void applySettings(){
        reset();
    }

    public void initializeControllers(){
        paneController = new PaneController(this, pane);
        menuController = new MenuController(this, runButton, resetButton, applyButton,
                numAutoms);
    }

    public Pane getPane() {
        return pane;
    }

    public Button getRunButton() {
        return runButton;
    }
}
