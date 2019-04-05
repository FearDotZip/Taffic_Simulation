package umbraltension.trafficsim;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;

public class TrafficSimulation extends Application {

    public static Settings set;
    @Override
    public void start(Stage mainStage) throws Exception{ //
        getSettings();
        Environment.init(set);
        //Environment.start();

        URL location = getClass().getClassLoader().getResource("src/main/resources/fxml/windowfxml.fxml");
        FXMLLoader fxLoader = new FXMLLoader(location);

        AnchorPane anchorPane = (AnchorPane) fxLoader.load();
        Scene scene = new Scene(anchorPane);

        mainStage.setTitle("Traffic Simulation");
        mainStage.setScene(scene);
        mainStage.show();
    }

    private void getSettings() {
        Gson gson = new Gson();
        String json = "";
        try {
            json = java.nio.file.Files.readString(Path.of("src/main/resources/settings.json"));
            TrafficSimulation.set = gson.fromJson(json, Settings.class);
        }catch (IOException e){
            System.out.println("Default settings used because settings file was not found.");
            TrafficSimulation.set  = new Settings();
        }
    }


    public static void main(String[] args){
        launch();
    }

}
