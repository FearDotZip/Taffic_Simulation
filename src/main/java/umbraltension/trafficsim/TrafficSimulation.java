package umbraltension.trafficsim;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;

public class TrafficSimulation extends Application {

    @Override
    public void start(Stage mainStage){
        MainFXController mainController;
        HBox root;
        try {
            URL location = new URL("file", "localhost", "C:/Users/jeremy/Desktop/Traffic_Simulation/src/main/resources/fxml/windowfxml.fxml");//getClass().getClassLoader().getResource("src/main/resources/fxml/windowfxml.fxml");
            FXMLLoader fxLoader = new FXMLLoader(location);
            root = fxLoader.load();
            root.layout();
            mainController = fxLoader.getController();
            //1. currently necessary to do in this order
            Environment.init(mainController);
            //2.
            mainController.initializeControllers();

            mainStage.setTitle("Traffic Simulation");
            mainStage.setScene(new Scene(root));
        } catch(IOException e){
            e.printStackTrace();
            System.out.println("\n\n The FXML loading failed.");
            System.exit(-1);
        }

        mainStage.show();
    }
    public static void main(String[] args){ launch(); }

}
