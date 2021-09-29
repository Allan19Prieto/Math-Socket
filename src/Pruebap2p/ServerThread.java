package Pruebap2p;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;

public class ServerThread extends Thread {
    private ServerSocket serverSocket;
    private Set<ServerThreadThread> serverThreadThreads = new HashSet<ServerThreadThread>();
    //Entra el numero del puerto a utilizar
    public ServerThread(String portNumb) throws IOException {
        //Convertimos el string del puerto a entero
        serverSocket = new ServerSocket(Integer.valueOf(portNumb));
    }

    public void run() {
        try {
            while (true) {
                ServerThreadThread serverThreadThread = new ServerThreadThread(serverSocket.accept(), this);
                serverThreadThreads.add(serverThreadThread);
                serverThreadThread.start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    void sendMessage(String message) {
        try {
            serverThreadThreads.forEach(t-> t.getPrintWriter().println(message));
            System.out.printf("Este mensaje" + message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Set<ServerThreadThread> getServerThreadThreads() {
        return serverThreadThreads;
    }
}
