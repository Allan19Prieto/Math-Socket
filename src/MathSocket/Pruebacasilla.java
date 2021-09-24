package MathSocket;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.scene.layout.Pane;

public class Pruebacasilla implements Initializable {

    @FXML
    private Funciones funciones;

    @FXML
    public Label label_jugador;
    public Button azul_inicio;
    public Button azul_0;
    public Button azul_1;
    public Button azul_2;
    public Button azul_3;
    public Button azul_4;
    public Button azul_5;
    public Button azul_6;
    public Button azul_7;
    public Button azul_8;
    public Button azul_9;
    public Button azul_10;
    public Button azul_11;
    public Button azul_12;
    public Button azul_13;
    public Button azul_final;
    public Button btn_trampa;

    @FXML
    private Label label;
    public Label label_tipo_casilla;
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

    //Solo par ala primera vuelta de sumado el indice del jugador 1
    int pase = 0;
    //cuando cae en trampa
    int pase_Trampa = 0;

    int num_lanzado ;
    int num_jugador = 0;

    int indice_jugador1 = 0;

    //Nombre de la casilla en la que se esta
    String nombre_casilla;

    @FXML
    public void handleButtonAction(javafx.event.ActionEvent actionEvent) {
        if (pase_Trampa == 0) {
            System.out.println("Lanzamiento de Dado");
            num_lanzado = fn.Lanzar_Dado();
            label.setText(num_lanzado + " casillas");
            System.out.println(num_lanzado);

        }
        //solo valida la vuelta
        if (pase_Trampa == 1){
            pase_Trampa = 0;
        }

        //solo para esta funcion
        //Ejemplo cuando le da un jugador y luego el otro
        //cambio de variables
        //indice donde se posiciona

        if (num_jugador == 0){
            num_jugador++;

            //****************
            if (pase == 0){
                //Solo sucede la primera vez
                indice_jugador1 = num_lanzado - 1;
                pase ++;

                //Funcion para el movimiento
                Movimiento_Azul(indice_jugador1);

            }else {
                // Validacion si el mayor a los numeros en lista
                if (indice_jugador1 + num_lanzado > 13){
                    indice_jugador1 = 13;
                    azul_9.setVisible(false);
                    azul_10.setVisible(false);
                    azul_11.setVisible(false);
                    azul_12.setVisible(false);
                    azul_13.setVisible(false);
                    azul_final.setVisible(true);

                }else {
                    //Suma de la variable normal
                    if (indice_jugador1 + num_lanzado < 0){
                        indice_jugador1 = 0;
                        azul_inicio.setVisible(true);
                        azul_0.setVisible(false);
                        azul_1.setVisible(false);
                        azul_2.setVisible(false);

                    }else {
                        indice_jugador1 = indice_jugador1 + (num_lanzado);

                        //Funcion para el movimiento
                        Movimiento_Azul(indice_jugador1);
                    }
                }
            }

            System.out.println("Lanzo el jugador 1");
            label_jugador.setText("Jugador 1");
            label_jugador.setStyle("-fx-background-color: Blue");
            nombre_casilla = tablero.get(indice_jugador1);
            System.out.println(nombre_casilla);

            //Validacion de las trampas
            if (nombre_casilla.equals("Trampa")){
                //Se habilita el boton de trampa
                //btn_trampa.setVisible(true);
                pase_Trampa = 1;
                num_lanzado = -3;
                num_jugador = 0;
                handleButtonAction(actionEvent);
                btn_trampa.setVisible(false);
                System.out.println("**");
            }


            //implrime la casilla en la que estoy
            label_tipo_casilla.setText(nombre_casilla);

        }else if (num_jugador == 1){
            num_jugador --;
            System.out.println("Lanzo el jugador 2");
            label_jugador.setText("Jugador 2");
            label_jugador.setStyle("-fx-background-color: Red");
            System.out.println(tablero.get(num_lanzado-1));

        }

    }
    @FXML
    public void TrampaAction(ActionEvent actionEvent) {
        //Validacion de las trampas
        /*if (nombre_casilla.equals("Trampa")) {
            //se retroceden 3 espacios
            num_lanzado = -3;
            num_jugador = 0;
            handleButtonAction(actionEvent);
            btn_trampa.setVisible(false);

        }
*/
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
                    palabras[k] = "Trampa";
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
        btn_1.setText(tablero.get(0));
        btn_2.setText(tablero.get(1));
        btn_3.setText(tablero.get(2));
        btn_4.setText(tablero.get(3));
        btn_5.setText(tablero.get(4));
        btn_6.setText(tablero.get(5));
        btn_7.setText(tablero.get(6));
        btn_8.setText(tablero.get(7));
        btn_9.setText(tablero.get(8));
        btn_10.setText(tablero.get(9));
        btn_11.setText(tablero.get(10));
        btn_12.setText(tablero.get(11));
        btn_13.setText(tablero.get(12));
        btn_14.setText(tablero.get(13));

        Tablero.setVisible(true);
    }


    //Para el movimiento del punto azul
    @FXML
    public void Movimiento_Azul(int indice_jugador1){

        //Valida posicion del jugador
        if (indice_jugador1 == 0){
            azul_inicio.setVisible(false);
            //**
            azul_0.setVisible(true);
            azul_1.setVisible(false);
            azul_2.setVisible(false);
            azul_3.setVisible(false);
            azul_5.setVisible(false);
            azul_6.setVisible(false);
            azul_7.setVisible(false);
            azul_8.setVisible(false);
            azul_9.setVisible(false);
            azul_10.setVisible(false);
            azul_11.setVisible(false);
            azul_12.setVisible(false);
            azul_13.setVisible(false);
            //azul_final.setVisible(false);
        }
        if (indice_jugador1 == 1){
            azul_inicio.setVisible(false);
            azul_0.setVisible(false);
            //**
            azul_1.setVisible(true);
            azul_2.setVisible(false);
            azul_3.setVisible(false);
            azul_5.setVisible(false);
            azul_6.setVisible(false);
            azul_7.setVisible(false);
            azul_8.setVisible(false);
            azul_9.setVisible(false);
            azul_10.setVisible(false);
            azul_11.setVisible(false);
            azul_12.setVisible(false);
            azul_13.setVisible(false);
            //azul_final.setVisible(false);
        }
        if (indice_jugador1 == 2){
            azul_inicio.setVisible(false);
            azul_0.setVisible(false);
            azul_1.setVisible(false);
            //**
            azul_2.setVisible(true);
            azul_3.setVisible(false);
            azul_5.setVisible(false);
            azul_6.setVisible(false);
            azul_7.setVisible(false);
            azul_8.setVisible(false);
            azul_9.setVisible(false);
            azul_10.setVisible(false);
            azul_11.setVisible(false);
            azul_12.setVisible(false);
            azul_13.setVisible(false);
            //azul_final.setVisible(false);
        }
        if (indice_jugador1 == 3){
            azul_inicio.setVisible(false);
            azul_0.setVisible(false);
            azul_1.setVisible(false);
            azul_2.setVisible(false);
            //**
            azul_3.setVisible(true);
            azul_5.setVisible(false);
            azul_6.setVisible(false);
            azul_7.setVisible(false);
            azul_8.setVisible(false);
            azul_9.setVisible(false);
            azul_10.setVisible(false);
            azul_11.setVisible(false);
            azul_12.setVisible(false);
            azul_13.setVisible(false);
            //azul_final.setVisible(false);
        }
        if (indice_jugador1 == 4){
            azul_inicio.setVisible(false);
            azul_0.setVisible(false);
            azul_1.setVisible(false);
            azul_2.setVisible(false);
            azul_3.setVisible(false);
            //**
            azul_4.setVisible(true);
            azul_5.setVisible(false);
            azul_6.setVisible(false);
            azul_7.setVisible(false);
            azul_8.setVisible(false);
            azul_9.setVisible(false);
            azul_10.setVisible(false);
            azul_11.setVisible(false);
            azul_12.setVisible(false);
            azul_13.setVisible(false);
            //azul_final.setVisible(false);
        }
        if (indice_jugador1 == 5){
            azul_inicio.setVisible(false);
            azul_0.setVisible(false);
            azul_1.setVisible(false);
            azul_2.setVisible(false);
            azul_3.setVisible(false);
            azul_4.setVisible(false);
            //**
            azul_5.setVisible(true);
            azul_6.setVisible(false);
            azul_7.setVisible(false);
            azul_8.setVisible(false);
            azul_9.setVisible(false);
            azul_10.setVisible(false);
            azul_11.setVisible(false);
            azul_12.setVisible(false);
            azul_13.setVisible(false);
            //azul_final.setVisible(false);
        }
        if (indice_jugador1 == 6){
            azul_inicio.setVisible(false);
            azul_inicio.setVisible(false);
            azul_0.setVisible(false);
            azul_1.setVisible(false);
            azul_2.setVisible(false);
            azul_3.setVisible(false);
            azul_4.setVisible(false);
            azul_5.setVisible(false);
            //**
            azul_6.setVisible(true);
            azul_7.setVisible(false);
            azul_8.setVisible(false);
            azul_9.setVisible(false);
            azul_10.setVisible(false);
            azul_11.setVisible(false);
            azul_12.setVisible(false);
            azul_13.setVisible(false);
            //azul_final.setVisible(false);
        }
        if (indice_jugador1 == 7){
            azul_inicio.setVisible(false);
            azul_0.setVisible(false);
            azul_1.setVisible(false);
            azul_2.setVisible(false);
            azul_3.setVisible(false);
            azul_4.setVisible(false);
            azul_5.setVisible(false);
            azul_6.setVisible(false);
            //**
            azul_7.setVisible(true);
            azul_8.setVisible(false);
            azul_9.setVisible(false);
            azul_10.setVisible(false);
            azul_11.setVisible(false);
            azul_12.setVisible(false);
            azul_13.setVisible(false);
            //azul_final.setVisible(false);
        }
        if (indice_jugador1 == 8){
            azul_inicio.setVisible(false);
            azul_0.setVisible(false);
            azul_1.setVisible(false);
            azul_2.setVisible(false);
            azul_3.setVisible(false);
            azul_4.setVisible(false);
            azul_5.setVisible(false);
            azul_6.setVisible(false);
            azul_7.setVisible(false);
            //**
            azul_8.setVisible(true);
            azul_9.setVisible(false);
            azul_10.setVisible(false);
            azul_11.setVisible(false);
            azul_12.setVisible(false);
            azul_13.setVisible(false);
            //azul_final.setVisible(false);
        }

        if (indice_jugador1 == 9){
            azul_inicio.setVisible(false);
            azul_0.setVisible(false);
            azul_1.setVisible(false);
            azul_2.setVisible(false);
            azul_3.setVisible(false);
            azul_4.setVisible(false);
            azul_5.setVisible(false);
            azul_6.setVisible(false);
            azul_7.setVisible(false);
            azul_8.setVisible(false);
            //**
            azul_9.setVisible(true);
            azul_10.setVisible(false);
            azul_11.setVisible(false);
            azul_12.setVisible(false);
            azul_13.setVisible(false);
            //azul_final.setVisible(false);

        }
        if (indice_jugador1 == 10){
            azul_inicio.setVisible(false);
            azul_0.setVisible(false);
            azul_1.setVisible(false);
            azul_2.setVisible(false);
            azul_3.setVisible(false);
            azul_4.setVisible(false);
            azul_5.setVisible(false);
            azul_6.setVisible(false);
            azul_7.setVisible(false);
            azul_8.setVisible(false);
            azul_9.setVisible(false);
            //**
            azul_10.setVisible(true);
            azul_11.setVisible(false);
            azul_12.setVisible(false);
            azul_13.setVisible(false);
            //azul_final.setVisible(false);
        }
        if (indice_jugador1 == 11){
            azul_inicio.setVisible(false);
            azul_0.setVisible(false);
            azul_1.setVisible(false);
            azul_2.setVisible(false);
            azul_3.setVisible(false);
            azul_4.setVisible(false);
            azul_5.setVisible(false);
            azul_6.setVisible(false);
            azul_7.setVisible(false);
            azul_8.setVisible(false);
            azul_9.setVisible(false);
            azul_10.setVisible(false);
            //*******
            azul_11.setVisible(true);
            azul_12.setVisible(false);
            azul_13.setVisible(false);
            //azul_final.setVisible(false);
        }
        if (indice_jugador1 == 12){
            azul_inicio.setVisible(false);
            azul_0.setVisible(false);
            azul_1.setVisible(false);
            azul_2.setVisible(false);
            azul_3.setVisible(false);
            azul_4.setVisible(false);
            azul_5.setVisible(false);
            azul_6.setVisible(false);
            azul_7.setVisible(false);
            azul_8.setVisible(false);
            azul_9.setVisible(false);
            azul_10.setVisible(false);
            azul_11.setVisible(false);
            //****
            azul_12.setVisible(true);
            azul_13.setVisible(false);
            //azul_final.setVisible(false);
        }
        if (indice_jugador1 == 13){
            azul_inicio.setVisible(false);
            azul_0.setVisible(false);
            azul_1.setVisible(false);
            azul_2.setVisible(false);
            azul_3.setVisible(false);
            azul_4.setVisible(false);
            azul_5.setVisible(false);
            azul_6.setVisible(false);
            azul_7.setVisible(false);
            azul_8.setVisible(false);
            azul_9.setVisible(false);
            azul_10.setVisible(false);
            azul_11.setVisible(false);
            azul_12.setVisible(false);
            //***
            azul_13.setVisible(true);
            //azul_final.setVisible(false);
        }

    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Todo
    }

}
