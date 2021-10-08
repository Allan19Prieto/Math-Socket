package prueba.Controllers;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class Funciones implements Initializable {


    // Aleatorio para lanzar el dado
    public static int Lanzar_Dado(){ return (int)(Math.random()*2+1); }

    //Aleatorio para llenar el tablero
    public static int Ram() { return (int)(Math.random()*14+0); }

    public static int randomTunel(){ return (int)(Math.random()*3*1); }

    public static int randomReto(){ return (int)(Math.random()*50*1); }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}