package prueba.NetworkManager;

import prueba.NetworkManager.protocol.GameMessage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.UUID;

public class NetworkManager {

    private final int port;
    private final SupervisorListener listener;
    private final String serverName;
    private Supervisor supervisor;

    private final String id;
    private  ServerSocket serverSocket;
    private PeerServer peerServer;

    public NetworkManager(int port, String serverName, SupervisorListener listener) {
        //Creamos un string para el identificador
        this.id = UUID.randomUUID().toString();
        //Si el serverName esta vacio, se le asigna un id
        if (serverName == null) {
            serverName = id;
        }
        this.serverName = serverName;
        this.port = port;
        this.listener = listener;
    }

    //Iniciamos el serverSocket con el puerto asignado.
    public void setupServer() throws IOException {
        System.out.println("Setting up server capabilities for peer: " + id);
        serverSocket = new ServerSocket(port);
    }

    //Metodo para iniciar el hilo enviando los datos el id
    public void startNetworking() {
        supervisor = new Supervisor(listener, id);
        peerServer = new PeerServer(serverSocket, serverName, supervisor);
        System.out.println("Starting peer server thread for peer: " + id);
        peerServer.startAsThread();
    }

    public void addPeer(String host, int port) throws UnknownHostException {
        Peer peer = new Peer(InetAddress.getByName(host), port, id, serverName);
        supervisor.addPeer(peer);
    }

    public void broadcast(GameMessage msg) {
        supervisor.broadcast(msg);
    }

    public void disconnect() {
        GameMessage msg = new GameMessage(GameMessage.DISCONNECT);
        supervisor.broadcast(msg);
        supervisor.disconnect();
    }

    public void shutdown() {
        System.out.println("Shutting down!");
        supervisor.shutdown();
        peerServer.shutdown();
    }
    public String getId() {
        return id;
    }

}
