package ejemplos;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;

//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;
import ejemplos.*;
import sample.Controller;

public class Pruebacasillas implements Initializable {


    Funciones fn = new Funciones();

    @FXML
    private Label label;
    @FXML
    private Button btn_4x4;
    @FXML
    private Button btn_5x5;
    @FXML
    private Button btn_6x6;

    @FXML
    public void handleButtonAction(javafx.event.ActionEvent actionEvent) {
        System.out.println("Lanzamiento de Dado");
        label.setText(fn.Lanzar_Dado() + " casillas");
    }

    @FXML
    public void btn_mostrarTablero(javafx.event.ActionEvent actionEvent){
        btn_4x4.setVisible(true);
        btn_5x5.setVisible(true);
        btn_6x6.setVisible(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Todo
    }


}
