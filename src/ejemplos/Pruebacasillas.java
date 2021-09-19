package ejemplos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;

//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import ejemplos.*;
import javafx.scene.layout.Pane;
import sample.Controller;

public class Pruebacasillas implements Initializable {

    @FXML
    private Label label;
    @FXML
    public Button btn_4x4;
    @FXML
    private Button btn_5x5;
    @FXML
    private Button btn_6x6;
    @FXML
    private Pane Tablero;
    @FXML
    Button btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9, btn_10, btn_11, btn_12, btn_13, btn_14;

    //Instanciamos la clase de las funciones
    Funciones fn = new Funciones();
    //LKL para el tablero del juego
    LinkedList<String> tablero = new LinkedList<>();
    //Arreglo que contrendra las palabras
    String[] palabras = new String[14];

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

    @FXML
    public void MostrarTablero(ActionEvent actionEvent) {
        //llenar el arreglo de palabras
        int contador = 1;
        for (int j = 0; j<=6; j++){
            palabras[j] = "Reto"+(j+1);
            if (contador == 1){
                contador ++;
                for (int k = 7; k <=11; k++){
                    palabras[k] = "Trampa"+(k-6);
                    if (contador == 2){
                        for (int n = 12; n<=13; n++){
                            palabras[n] = "Tunel"+(n-11);

                        }
                    }
                }
            }
        }

        //Llenar el tablero con las palabras aleatoriamente
        for (int t=0; t<=13; t++){
            tablero.add(palabras[fn.Ram()]);
        }
        //Mostar en el tablero los palabras
        btn_1.setText(tablero.get(fn.Ram()));
        btn_2.setText(tablero.get(fn.Ram()));
        btn_3.setText(tablero.get(fn.Ram()));
        btn_4.setText(tablero.get(fn.Ram()));
        btn_5.setText(tablero.get(fn.Ram()));
        btn_6.setText(tablero.get(fn.Ram()));
        btn_7.setText(tablero.get(fn.Ram()));
        btn_8.setText(tablero.get(fn.Ram()));
        btn_9.setText(tablero.get(fn.Ram()));
        btn_10.setText(tablero.get(fn.Ram()));
        btn_11.setText(tablero.get(fn.Ram()));
        btn_12.setText(tablero.get(fn.Ram()));
        btn_13.setText(tablero.get(fn.Ram()));
        btn_14.setText(tablero.get(fn.Ram()));

        Tablero.setVisible(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Todo
    }


}
