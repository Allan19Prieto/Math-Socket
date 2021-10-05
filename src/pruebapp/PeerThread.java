package pruebapp;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class PeerThread extends Thread{

    public Boolean esperandoMensaje;
    public String mensaje;
    public String comando;

    private DataInputStream dataInputStream;

    public PeerThread(Socket socket) throws IOException {
        dataInputStream = new DataInputStream(socket.getInputStream());
    }

    public void run() {
        boolean flag = true;
        while (flag) {
            try{
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
                flag = false;
                interrupt();
            }
        }
    }
}
