package MathSocket;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Cliente extends Application implements Initializable {

    @FXML
    private TextField nombreCliente;

    private static Socket sock;
    public Boolean mensajeEnviado;

    public Cliente() {
        mensajeEnviado = false;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void enviarNombre(ActionEvent event) throws IOException {
        try {
            DataOutputStream out = new DataOutputStream(sock.getOutputStream());
            //DataInputStream in = new DataInputStream(Sock.getInputStream());
            System.out.println("Enviar nombre a servidor");
            out.writeUTF(nombreCliente.getText());
            this.mensajeEnviado = true;
            /*Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Pruebacasillas.fxml"));
            Parent root = loader.load();
            Stage ventana2 = new Stage();
            ventana2.setTitle("Client-Socket");
            ventana2.setScene(new Scene(root));
            ventana2.show();*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void start(Stage ventanaPrincipal) throws Exception {
        sock = new Socket("localhost", 6066);
        System.out.println("Client Side: Line 1");
        Parent root = FXMLLoader.load(getClass().getResource("cliente.fxml"));
        System.out.println("Client Side: Line 2");
        ventanaPrincipal.setTitle("Client-Socket");
        System.out.println("Client Side: Line 3");
        ventanaPrincipal.setScene(new Scene(root, 500, 475));
        System.out.println("Client Side: Line 4");
        ventanaPrincipal.toFront();
        System.out.println("Client Side: Line 5");
        ventanaPrincipal.show();
        System.out.println("Client Side: Line 6");
    }

}
