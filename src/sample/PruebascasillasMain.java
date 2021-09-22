package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PruebascasillasMain extends Application {

    public void start(Stage stage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Pruebacasillas.fxml"));
            stage.setTitle("Math-Socket");
            stage.setScene(new Scene(root, 643, 484));
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String args[]) {
        launch(args);
    }
}
