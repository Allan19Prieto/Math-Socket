package MathSocket;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLDocumentController implements Initializable {
    @FXML private Button jugar;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    @FXML
    private void comenzarJuego(ActionEvent event) {
        try {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Pruebacasillas.fxml"));
            Parent root = loader.load();
            Stage ventana2 = new Stage();
            ventana2.setTitle("Math-Socket");
            ventana2.setScene(new Scene(root));
            ventana2.show();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
