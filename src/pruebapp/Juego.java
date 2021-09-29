package pruebapp;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.json.Json;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Juego extends Application implements Initializable {

    public String nombreJugador;
    public String puetoJugador;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void inicial(String usename, String serverPuerto) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        //Mandamos el puerto al serverThread
        ServerThread serverThread = new ServerThread(serverPuerto);
        serverThread.start();
        new Juego().updateListenToPeers(bufferedReader, usename, serverThread);
    }

    public void updateListenToPeers(BufferedReader bufferedReader, String username, ServerThread serverThread) throws Exception{
        System.out.println("> enter (space separated) hostname:port#");
        System.out.println(" peers to receive messages from (s to skip):");
        String input = bufferedReader.readLine();
        String[] inputValues = input.split(" ");

        if (!input.equals("s")) for (int i = 0; i < inputValues.length; i++) {
            String[] address = inputValues[i].split(":");
            Socket socket = null;
            // address[0]);
            try{
                socket = new Socket(address[0], Integer.valueOf(address[1]));
                new PeerThread(socket).start();
            }catch (Exception e) {
                if (socket != null) socket.close();
                else System.out.println("invalid input. skipping to next step.");
            }
        }
        communicate(bufferedReader, username, serverThread);
    }

    @FXML
    public void communicate(BufferedReader bufferedReader, String username, ServerThread serverThread) {
        try{
            System.out.println("> you can now comunicate (e to exit, c to change)");
            boolean flag = true;
            while (flag) {
                String message = bufferedReader.readLine();
                if (message.equals("e")) {
                    break;
                } else if(message.equals("c")) {
                    //updateListenToPeers(bufferedReader, username, serverThread);
                } else {
                    StringWriter stringWriter = new StringWriter();
                    Json.createWriter(stringWriter).writeObject(Json.createObjectBuilder()
                            .add("username",username)
                            .add("message",message)
                            .build());
                    serverThread.sendMessage(stringWriter.toString());
                }
            }
            System.exit(0);
        }catch (Exception e){

        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("frmjuego.fxml"));
        primaryStage.setTitle("Partida de --> "+ nombreJugador);
        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.toFront();
        primaryStage.alwaysOnTopProperty();
        primaryStage.show();
        System.out.printf(nombreJugador);
        System.out.printf(puetoJugador);
    }

    public static void main(String[] args) {launch(args);}
}
