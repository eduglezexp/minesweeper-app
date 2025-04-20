module es.ies.puerto {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires javafx.graphics;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    requires jbcrypt;
    requires java.sql;
    requires javafx.base;

    opens es.ies.puerto to javafx.fxml;
    opens es.ies.puerto.controller to javafx.fxml;
    opens es.ies.puerto.controller.abstractas to javafx.fxml; 

    opens es.ies.puerto.model.entities to com.fasterxml.jackson.databind, 
    org.junit.platform.commons, org.mockito, javafx.base;

    exports es.ies.puerto;
    exports es.ies.puerto.controller;
}
