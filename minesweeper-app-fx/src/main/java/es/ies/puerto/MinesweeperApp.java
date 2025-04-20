package es.ies.puerto;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * @author eduglezexp
 * @version 1.0.0
 */

public class MinesweeperApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MinesweeperApp.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        String css = this.getClass().getResource("css/style.css").toExternalForm();
        scene.getStylesheets().add(css);
        //Image icon = new Image(getClass().getResource("/es/ies/puerto/img/icon.png").toExternalForm());
        //stage.getIcons().add(icon);
        stage.setTitle("Pantalla Princial");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    public static void main(String[] args) {
        launch();
    }
}