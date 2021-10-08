package prueba;

import javafx.application.Application;
import javafx.stage.Stage;
import prueba.Controllers.WelcomeController;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Titulo y mandamos la nueva escena
        primaryStage.setTitle("P2P Game");
        new WelcomeController(primaryStage).displayScene();
    }

    public static void main(String[] args) throws InterruptedException {
        launch(args);
    }

}
