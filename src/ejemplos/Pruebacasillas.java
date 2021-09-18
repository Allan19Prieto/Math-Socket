package ejemplos;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class Pruebacasillas implements Initializable {

    @FXML
    private Label label;

    @FXML
    public void handleButtonAction(javafx.event.ActionEvent actionEvent) {
        System.out.println("Tocaste el boton de juego");
        label.setText("VAMOS A JUGAR");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Todo
    }


}
