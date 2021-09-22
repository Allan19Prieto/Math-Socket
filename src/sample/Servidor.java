package sample;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    private boolean miTurno = false;
    private String esperarConexion = "Esperando Conexión";




    public static void main(String[] args) {
        Socket socketCliente = null;
        ServerSocket servidor = null;
        DataInputStream in;
        DataOutputStream out;


        {
            try {

                servidor = new ServerSocket(4000);
                Main juegoServidor = new Main();


                System.out.println("Servidor Iniciado");

                while (true) {
                    socketCliente = servidor.accept();
                    System.out.println("Cliente Conectado");
                    in = new DataInputStream(socketCliente.getInputStream());
                    out = new DataOutputStream(socketCliente.getOutputStream());

                    String mensaje = in.readUTF();
                    System.out.println(mensaje);

                    out.writeUTF("Hola Cliente");

                    socketCliente.close();
                    System.out.println("Cliente Desconectado");

                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}