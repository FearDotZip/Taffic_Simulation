module cabbage.trafficsim{
    requires java.desktop;
    requires java.datatransfer;

    requires gson;
    //requires java.sql; //got a runtime error classNotFound because gson's constructor needed it. This fixed it.

    requires javafx.controls;
    requires javafx.fxml;

    opens umbraltension.trafficsim to javafx.fxml;

    exports umbraltension.trafficsim;

}