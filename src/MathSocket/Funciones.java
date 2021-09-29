package MathSocket;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Funciones implements Initializable {


    //Funcion para lanzar el dado
    public static int Lanzar_Dado(){
        return (int)(Math.random()*4+1);
    }

    //Aleatorio para llenar el tablero
    public static int Ram() { return (int)(Math.random()*14+0); }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}