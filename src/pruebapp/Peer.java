package pruebapp;
<<<<<<< HEAD

import java.io.DataInputStream;
import java.io.DataOutputStream;
=======
import javax.json.Json;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
>>>>>>> f72da933781b7dbcab713dea45c46cc276a604b4
import java.io.StringWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.json.Json;
import javax.json.JsonObject;
import javax.swing.*;


public class Peer extends Application implements Initializable {

    public String nombreJugador;
    public String puertoJugador;

    public Integer puertoOtroJugador;

    public Boolean inicioJuego;

    //public ServerThread serverThread;
    public Socket socket;
    public Socket peerSocket;
    public PeerThread peer;
    private Thread serverThread = null;
    private Thread peerThread = null;
    private ServerSocket serverSocket;
    private static DataInputStream dataEntrante;
    private static DataOutputStream dataSaliente;

    public Boolean esperandoMensaje = true;
    public String mensaje;
    public String comando;


    public int cerrar = 0;

    @FXML
    public TextField nombreServidor;
    @FXML
    public TextField puertoServidor;
    @FXML
    public Button jugar;

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    // Boton "Registrarme"
    @FXML
    public void conectarJugador(ActionEvent actionEvent) throws Exception {
<<<<<<< HEAD
=======
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        // Asignar valores del UI
>>>>>>> f72da933781b7dbcab713dea45c46cc276a604b4
        nombreJugador = nombreServidor.getText();
        //puertoJugador = puertoServidor.getText();

        sendMessage("A", "todo bien");

        prueba();

        //Pasamos el nombre al la clase jugador
        //Juego jugador = new Juego(this.inicioJuego, this.puertoJugador, this.nombreJugador);
        //jugador.inicial(this.nombreJugador, this.puertoJugador);

        //Abrimos la nueva ventana del Juego
        //Stage ventanaCliente = new Stage();
        //jugador.start(ventanaCliente);
    }

    public void prueba() {
        while (esperandoMensaje) {
        }

        jugar.setDisable(false);
        System.out.print(comando);
        System.out.print(mensaje);
    }

    @FXML
    public void iniciarJugador(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("frmpeer.fxml"));
        if (this.inicioJuego) {
            primaryStage.setTitle("Sala de espera: Primer Jugador");
        } else {
            primaryStage.setTitle("Sala de espera: Segundo Jugador");
        }
        primaryStage.setScene(new Scene(root, 500, 475));
        primaryStage.toFront();
        primaryStage.alwaysOnTopProperty();
        primaryStage.show();
        this.abrirConexion();
        if (cerrar == 1){
            primaryStage.close();
        }
    }

    public void abrirConexion() throws Exception {
        /*try {
            serverThread = new ServerThread(this.puertoJugador);
            serverThread.start();
            socket = new Socket("localhost", this.puertoOtroJugador);
            peerThread =  new PeerThread(socket);
            peerThread.start();

            if (serverThread.sendMessage()) {
                System.out.print("serverThread is null");
            }
            System.out.println("Se registro usuario en este puerto: " + this.puertoJugador);
        } catch (Exception e) {
            if (socket != null) socket.close();
        }*/
        serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String mensajeEntrante = "";
                try {
                    serverSocket = new ServerSocket(Integer.valueOf(puertoJugador));//Abriendo con el puerto para chat
                    socket = serverSocket.accept();//Obtener el socket que hizo conexion
                    dataSaliente = new DataOutputStream(socket.getOutputStream());
                    peerSocket = new Socket("localhost", puertoOtroJugador);
                    dataEntrante = new DataInputStream(peerSocket.getInputStream());
                    if (socket.isConnected()) {
                        System.out.println("Se conecto el serverSocket");
                    }
                } catch (Exception e) {
                }
            }
        });

        peerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JsonObject jsonObject = Json.createReader(dataEntrante).readObject();
                    System.out.print(jsonObject.toString());
                    if (jsonObject.containsKey("comando")) {
                        //Imprimimos en consola dependiando del mensaje
                        System.out.println("[" + jsonObject.getString("comando") + "]: " + jsonObject.getString("message"));
                        comando = jsonObject.getString("comando");
                        mensaje = jsonObject.getString("message");
                        esperandoMensaje = false;
                    }
                    esperandoMensaje = false;

                } catch (Exception e) {
                }
            }
        });
    }

    public void sendMessage(String comando, String message) {
        try {
            StringWriter stringWriter = new StringWriter();
            Json.createWriter(stringWriter).writeObject(Json.createObjectBuilder()
                    .add("comando",comando)
                    .add("message",message)
                    .build());

            dataSaliente.writeUTF(stringWriter.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
