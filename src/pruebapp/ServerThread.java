package pruebapp;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonParser;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ServerThread extends Thread {
    private ServerSocket serverSocket;
    public Socket socket;
    public String portNumb;

    //Entra el numero del puerto a utilizar
    public ServerThread(String portNumb) throws IOException {
        System.out.println("***pasando por el serverThread*****");
        this.portNumb = portNumb;
        //Convertimos a Integer del puerto y se lo pasamos al serverSocket
        serverSocket = new ServerSocket(Integer.valueOf(portNumb));
    }

    public void run() {
        try {
            while (true) {
                socket = serverSocket.accept();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendMessage(String comando, String message) {
        try {
            StringWriter stringWriter = new StringWriter();
            Json.createWriter(stringWriter).writeObject(Json.createObjectBuilder()
                    .add("comando",comando)
                    .add("message",message)
                    .build());

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(stringWriter.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
