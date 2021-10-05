package pruebapp;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Principal extends Application implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void start(Stage ventanaPrincipal) throws Exception {
        // Creamos el primer jugador
        Peer jugador1 = new Peer();
        jugador1.inicioJuego = true;
        jugador1.puertoJugador = "6060";
        jugador1.puertoOtroJugador = 6061;
        jugador1.iniciarJugador(ventanaPrincipal);

        Stage ventanaSegundoJugador = new Stage();
        // Creamos el segundo jugador
        Peer jugador2 = new Peer();
        jugador2.inicioJuego = false;
        jugador2.puertoJugador = "6061";
        jugador2.puertoOtroJugador = 6060;
        jugador2.iniciarJugador(ventanaSegundoJugador);
    }

    public static void main(String args[]) {
        launch(args);
    }
}
