module TrafficSimulation {


    requires java.desktop;
    requires gson;
    requires java.sql;

    requires javafx.controls;
    requires javafx.fxml;

    opens umbraltension.trafficsim to javafx.fxml;
    exports umbraltension.trafficsim;

}