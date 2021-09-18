package ejemplos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PruebascasillasMain extends Application {

    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Pruebacasillas.fxml"));
        stage.setTitle("Math-Socket");
        stage.setScene(new Scene(root, 500, 475));
        stage.show();
    }

    public static void main(String args[]) {
        launch(args);
    }
}
