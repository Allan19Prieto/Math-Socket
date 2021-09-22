package MathSocket;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage ventanaPrincipal) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        ventanaPrincipal.setTitle("Math-Socket");
        ventanaPrincipal.setScene(new Scene(root, 500, 475));
        ventanaPrincipal.show();
    }

    public static void main(String args[]) {
        launch(args);
    }
}