package MathSocket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
        final String host = "127.0.0.1";

        Socket socketCliente;
        DataInputStream in;
        DataOutputStream out;

        {
            try {
                socketCliente = new Socket(host, 4000);
                in = new DataInputStream(socketCliente.getInputStream());
                out = new DataOutputStream(socketCliente.getOutputStream());

                out.writeUTF("Hola Mundo,Soy Cliente");
                String mensaje = in.readUTF();
                System.out.println(mensaje);

                socketCliente.close();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
