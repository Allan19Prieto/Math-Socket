package pruebapp;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import javax.json.Json;
import javax.json.JsonObject;
<<<<<<< HEAD
=======
import java.io.BufferedReader;
>>>>>>> f72da933781b7dbcab713dea45c46cc276a604b4
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class PeerThread extends Thread{

<<<<<<< HEAD
    public Boolean esperandoMensaje;
    public String mensaje;
    public String comando;

    private DataInputStream dataInputStream;

    public PeerThread(Socket socket) throws IOException {
        dataInputStream = new DataInputStream(socket.getInputStream());
=======
    private BufferedReader bufferedReader;
    private DataInputStream inputStream;
    private String jugadorNombre1;
    private String jugadorNombre2;

    @FXML
    private TextField nombreServidor;

    public PeerThread(Socket socket) throws IOException {
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //DataInputStream inputStream = new DataInputStream(socket.getInputStream());
>>>>>>> f72da933781b7dbcab713dea45c46cc276a604b4
    }

    public void run() {
        boolean flag = true;
        while (flag) {
            try{
<<<<<<< HEAD
                JsonObject jsonObject = Json.createReader(dataInputStream).readObject();
                System.out.print(jsonObject.toString());
                if (jsonObject.containsKey("comando")) {
                    //Imprimimos en consola dependiando del mensaje
                    System.out.println("[" + jsonObject.getString("comando") + "]: " + jsonObject.getString("message"));
                    this.comando = jsonObject.getString("comando");
                    this.mensaje = jsonObject.getString("message");
                    this.esperandoMensaje = false;
                }
                this.esperandoMensaje = false;
            } catch (Exception e){
=======
                JsonObject jsonObject = Json.createReader(inputStream).readObject();
                if (jsonObject.containsKey("username")) {
                    jugadorNombre1 = nombreServidor.getText();
                    jugadorNombre2 = jsonObject.getString("username");
                    System.out.println("Nombre Jugador 1: " + jugadorNombre1);
                    System.out.println("Nombre Jugador 2: " + jugadorNombre2);
                }
            }catch (Exception e){
>>>>>>> f72da933781b7dbcab713dea45c46cc276a604b4
                flag = false;
                interrupt();
            }
        }
    }
}
