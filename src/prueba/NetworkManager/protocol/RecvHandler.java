package prueba.NetworkManager.protocol;

import prueba.NetworkManager.Peer;

import java.io.DataInputStream;
import java.io.IOException;

public class RecvHandler implements Runnable {

    private final Peer peer;
    private final DataInputStream in;
    private boolean alive;
    private Thread th;

    public RecvHandler(Peer peer, DataInputStream dataIn) {
        this.peer = peer;
        this.in = dataIn;
        alive = true;
    }

    @Override
    public void run() {
        th = Thread.currentThread();
        try {
            while (alive && in != null) {
                int dataLength = in.readInt();
                if (dataLength < 0) {
                    throw new IOException("Unexpected data length.");
                }
                byte type = in.readByte();
                switch (type) {
                    case GameMessage.HEARTBEAT:
                        System.out.println("Received HEARTBEAT");
                        break;
                    case  GameMessage.DISCONNECT:
                        System.out.println("Received DISCONNECT");
                        peer.getConnection().onReceive(peer, new GameMessage(GameMessage.DISCONNECT));
                        break;
                    case GameMessage.ACTION:
                        System.out.println("Received action!");
                        byte[] bytes = new byte[dataLength - 1];
                        in.readFully(bytes);
                        System.out.println(bytes);
                        // TODO Create well-formed message, pass it up
                        int actionIndex = GameMessage.ACTION - 1;
                        peer.getConnection().onReceiveCommand(
                                peer,
                                GameMessage.operations[actionIndex],
                                GameMessage.actions[bytes[0]]
                        );

                }
            }
        } catch (IOException ioEx) {
            // Ignore
            alive = false;
        } catch (Exception ex) {
            ex.printStackTrace();
            alive = false;
        } finally {
            peer.disconnect();
        }
    }

    public void disconnect() {
        alive = false;
        if (th != null) {
            th.interrupt();
        }
    }
}
