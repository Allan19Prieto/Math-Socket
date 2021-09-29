package MathSocket;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Thread.sleep;

public class Servidor extends Application implements Initializable {

    private boolean miTurno = false;
    private String esperarConexion = "Esperando Conexion";

    private static Socket Sock;
    private static Cliente cliente;
    private Thread comenzarServidor = null;
    private Thread threadJuego = null;

    @FXML
    private TextField nombreServidor;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void comenzarJuego(ActionEvent event) throws Exception {
        comenzarServidor = new Thread(new Runnable() { //Instancia el Thread del servidor

            @Override
            public void run() {
                try {

                    ServerSocket serverSock = new ServerSocket(6066); //Crea el puerto del servidor
                    Sock = serverSock .accept(); //Acepta al cliente
                    DataInputStream in = new DataInputStream(Sock.getInputStream()); //Permite recibir mensajes del cliente
                    String jugador2 = in.readUTF(); //Recibe el nombre del jugador 2
                    String jugador1 = nombreServidor.getText(); //Recibe el nombre del jugador 1
                    System.out.println(jugador2);

                    /*Node source = (Node) event.getSource();
                    Stage stage = (Stage) source.getScene().getWindow();
                    stage.close();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Pruebacasillas.fxml"));
                    Parent root = loader.load();
                    Stage ventana2 = new Stage();
                    ventana2.setTitle("Server-Socket");
                    ventana2.setScene(new Scene(root));
                    ventana2.show();\\

                     */

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        threadJuego = new Thread(new Runnable() {
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
                ventana2.setTitle("Server-Socket"); //Titulo de la ventana
                ventana2.setScene(new Scene(root));
                ventana2.show(); //Muestra la ventana
            }
        });
        comenzarServidor.start(); //Inicia el Thread del servidor
        threadJuego.start(); //Inicia el Thread del Juego
        Cliente cliente = new Cliente(); //Instancia el Cliente
        Stage ventanaCliente = new Stage(); //Crea la ventana del cliente
        cliente.start(ventanaCliente); // Abre la ventana del cliente

    }

    @Override
    public void start(Stage ventanaPrincipal) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("servidor.fxml"));
        ventanaPrincipal.setTitle("Server-Socket");
        ventanaPrincipal.setScene(new Scene(root, 500, 475));
        ventanaPrincipal.toFront();
        ventanaPrincipal.alwaysOnTopProperty();
        ventanaPrincipal.show();
    }

    public static void main(String[] args) {
        try {
            System.out.println("Server Side: Line 16");
            launch(args);
            System.out.println("Server Side: Line 18");
            DataOutputStream out =new DataOutputStream(Sock.getOutputStream());
            System.out.println("Server Side: Line 20");
            out.writeUTF("i am fine, thank you");
            System.out.println("Server Side: Line 22");
            DataInputStream in= new DataInputStream(Sock.getInputStream());
            System.out.println("Server Side: Line 24");
            System.out.println(in.readUTF());
            System.out.println("Server Side: Line 26");
            Sock.close();
            System.out.println("Server Side: Line 28");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}