package pruebapp;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import javax.json.Json;
import javax.json.JsonObject;

import java.io.BufferedReader;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class PeerThread extends Thread{


    private BufferedReader bufferedReader;
    private DataInputStream inputStream;
    private String jugadorNombre1;
    private String jugadorNombre2;

    @FXML
    private TextField nombreServidor;

    public PeerThread(Socket socket) throws IOException {
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //DataInputStream inputStream = new DataInputStream(socket.getInputStream());

    }

    public void run() {
        boolean flag = true;
        while (flag) {
            try{

                JsonObject jsonObject = Json.createReader(inputStream).readObject();
                if (jsonObject.containsKey("username")) {
                    jugadorNombre1 = nombreServidor.getText();
                    jugadorNombre2 = jsonObject.getString("username");
                    System.out.println("Nombre Jugador 1: " + jugadorNombre1);
                    System.out.println("Nombre Jugador 2: " + jugadorNombre2);
                }
            }catch (Exception e){

                flag = false;
                interrupt();
            }
        }
    }
}
