package pruebapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThreadThread extends Thread {
    private ServerThread serverThread;
    private Socket socket;
    private PrintWriter printWriter;
    public ServerThreadThread(Socket socket, ServerThread serverThread) {
        this.serverThread = serverThread;
        this.socket = socket;
        System.out.println("***ServerThreadThread "+serverThread);
        System.out.println("***ServerThreadThread "+socket);
    }
    public void run() {
        try {
            //Leemos el imput del mensaje
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.printWriter = new PrintWriter(socket.getOutputStream(), true);
            while (true) serverThread.sendMessage( bufferedReader.readLine());
        }catch (Exception e) {
            serverThread.getServerThreadThreads().remove(this);
        }
    }
    //devolvemos la alectura del mensaje
    public PrintWriter getPrintWriter() {
        System.out.println("****Que es el mensaje que se retorna: "+ printWriter);
        return printWriter;

    }
}
