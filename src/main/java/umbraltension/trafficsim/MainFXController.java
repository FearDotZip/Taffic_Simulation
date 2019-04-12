package umbraltension.trafficsim;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;


public class MainFXController implements Initializable {


    @FXML
    Pane pane;
    @FXML
    Button runButton, resetButton, applyButton;
    @FXML
    TextField numAutoms, mapSize, sizeRatio, timeRatio;
    @FXML
    Label clock;


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
        paneController.play();
        menuController.play();
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
        Label[] labels = {clock};
        Button[] buttons = {applyButton, resetButton, runButton}; //keep these lists alphabetized;
        TextField[] textFields = {mapSize, numAutoms, sizeRatio, timeRatio};
        menuController = new MenuController(this, textFields, buttons, labels);
        paneController = new PaneController(this, pane);
    }

    public PaneController getPaneController() {
        return paneController;
    }

    public MenuController getMenuController() {
        return menuController;
    }

}
