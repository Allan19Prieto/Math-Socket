package prueba.NetworkManager.protocol;

import java.io.DataOutputStream;
import java.io.IOException;

public class GameMessage {

    public final static byte HEARTBEAT = 0;
    public final static byte HANDSHAKE = 1;
    public final static byte ACTION = 2;
    public final static byte DISCONNECT = 3;
    public final static byte QUERY_PEERS = 4;

    public final static String[] operations = {
            "Heartbeat",
            "Handshake",
            "Action",
            "Disconnect"
    };

    public final static byte ACTION_ROCK = 0;
    public final static byte ACTION_PAPER = 1;
    public final static byte ACTION_SCISSORS = 2;

    public final static String[] actions = {
            "Rock",
            "Paper",
            "Scissors"
    };

    private byte type;
    private byte[] data;
    private int length;

    public GameMessage(byte type) {
        this.type = type;
        this.length = 1;
    }

    public GameMessage(byte type, byte[] data) {
        this.type = type;
        this.data = data;
        this.length = 1 + data.length;
    }

    public void send(DataOutputStream out) throws IOException {

        out.writeInt(length);
        out.writeByte(type);
        if (data != null && data.length > 0) {
            out.write(data);
        }
        out.flush();
    }

    public byte getType() {
        return type;
    }

}
