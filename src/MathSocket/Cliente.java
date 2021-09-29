package MathSocket;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Cliente extends Application implements Initializable {

    @FXML
    private TextField nombreCliente;

    private static Socket sock;
    public Boolean mensajeEnviado;
    private Thread comenzarCliente = null;
    private Thread comenzarGame = null;

    public Cliente() {
        mensajeEnviado = false;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void enviarNombre(ActionEvent event) throws IOException {
        comenzarCliente = new Thread(new Runnable() {
            private boolean mensajeEnviado;

            @Override
            public void run() {
                try {
                    DataOutputStream out = new DataOutputStream(sock.getOutputStream()); //Sirve para enviar mensajes
                    System.out.println("Enviar nombre a servidor");
                    out.writeUTF(nombreCliente.getText()); //Envia el mensaje
                    this.mensajeEnviado = true;

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        comenzarCliente = new Thread(new Runnable() { //Instancia el Thread del cliente
            @Override
            public void run() {
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close(); //Cierra la ventana principal
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Pruebacasillas.fxml")); //Toma los elementos del archivo fxml
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage ventana2 = new Stage(); //Crea la ventana principal del servidor
                ventana2.setTitle("Client-Socket"); //Titulo de la ventana
                ventana2.setScene(new Scene(root));
                ventana2.show(); //Muestra la ventana
            }
        });
        comenzarCliente.start(); //Inicia el Thread del cliente
        comenzarGame.start(); //Inicia el Thread del Juego

    }


    @Override
    public void start(Stage ventanaPrincipal) throws Exception {
        sock = new Socket("localhost", 6066);
        System.out.println("Client Side: Line 1");
        Parent root = FXMLLoader.load(getClass().getResource("cliente.fxml"));
        System.out.println("Client Side: Line 2");
        ventanaPrincipal.setTitle("Client-Socket");
        System.out.println("Client Side: Line 3");
        ventanaPrincipal.setScene(new Scene(root, 500, 475));
        System.out.println("Client Side: Line 4");
        ventanaPrincipal.toFront();
        System.out.println("Client Side: Line 5");
        ventanaPrincipal.show();
        System.out.println("Client Side: Line 6");
    }

}
