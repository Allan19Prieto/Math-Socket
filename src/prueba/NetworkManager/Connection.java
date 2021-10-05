package prueba.NetworkManager;

import prueba.NetworkManager.protocol.GameMessage;
import prueba.NetworkManager.protocol.RecvHandler;
import prueba.NetworkManager.protocol.SendHandler;

public class Connection {

    private final Peer peer;
    private final PeerListener listener;
    protected final RecvHandler in;
    protected final SendHandler out;

    public Connection(Peer peer, PeerListener listener, RecvHandler in, SendHandler out) {
        this.peer = peer;
        this.listener = listener;
        this.in = in;
        this.out = out;
    }

    public void requestSend(GameMessage data) {
        System.out.println("Send requested: " + data);
        out.send(data);
    }

    public void onReceiveCommand(Peer peer, String msg) {
        listener.onReceiveCommand(peer, msg);
    }

    public void onReceive(Peer peer, GameMessage message) {
        switch (message.getType()) {
            case GameMessage.HEARTBEAT:
                break;
            case GameMessage.HANDSHAKE:
                break;
            case GameMessage.DISCONNECT:
                System.out.println("Cleaning up peer connection");
                peer.disconnect();
                listener.onDisconnected(peer);
                break;
            case GameMessage.ACTION:
                break;
        }
    }

    public void onReceiveCommand(Peer peer, String operation, String action) {
        listener.onReceiveCommand(peer, operation, action);
    }

}
