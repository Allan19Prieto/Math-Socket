package pruebapp;

import javax.json.Json;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import MathSocket.Cliente;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class Peer extends Application implements Initializable {

    public Stage primaryStage = new Stage();

    public String nombreJugador;
    public String puertoJugador;

    public int cerrar = 0;

    @FXML
    public TextField nombreServidor;
    @FXML
    public TextField puertoServidor;

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {

    }

    @FXML
    public void conectarJugador(ActionEvent actionEvent) throws Exception {
        nombreJugador = nombreServidor.getText();
        puertoJugador = puertoServidor.getText();

        //Pasamos el nombre al la clase jugador
        Juego jugador = new Juego();
        jugador.nombreJugador = nombreJugador;
        jugador.puetoJugador = puertoJugador;

        //Abrimos la nueva ventana del Juego
        Stage ventanaCliente = new Stage();
        jugador.start(ventanaCliente);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("frmpeer.fxml"));
        primaryStage.setTitle("Sala de espera");
        primaryStage.setScene(new Scene(root, 500, 475));
        primaryStage.toFront();
        primaryStage.alwaysOnTopProperty();
        primaryStage.show();
        if (cerrar == 1){
            primaryStage.close();
        }
    }

    public static void main(String[] args) {launch(args);}
}
