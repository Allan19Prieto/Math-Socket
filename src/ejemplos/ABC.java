package ejemplos;

import java.util.*;

public class ABC {

    //Función para tirar el Dado
    public static int Dado(){
        return (int)(Math.random()*6+1);
    }
    //Aleatorio para llenar el tablero
    public static int Ram(){
        return (int)(Math.random()*15+0);
    }

    public static void main(String[] args) {
        //LKL para el tablero del juego
        LinkedList<String> tablero = new LinkedList<>();
        //lista par alos numeros ramd


        //Arreglo que contrendra las palabras
        String[] palabras = new String[16];

        //llenar el arreglo de palabras
        int contador = 1;
        for (int j = 0; j<=7; j++){
            palabras[j] = "Reto"+(j+1);
            if (contador == 1){
                contador ++;
                for (int k = 8; k <=13; k++){
                    palabras[k] = "Trampa"+(k-7);
                    if (contador == 2){
                        for (int n = 14; n<=15; n++){
                            palabras[n] = "Tunel"+(n-13);

                        }
                    }
                }
            }
        }
        //Solo par aver si se lleno correcta mente
        System.out.println("ejemplo lista palabras");
        for (int y=0; y<=15; y++){
            System.out.println(palabras[y]);
        }
        System.out.println(" ");

        //Llenar el tablero con las palabras aleatoriamente
        for (int t=0; t<=15; t++){
            tablero.add(palabras[Ram()]);
        }

        for (int i = 1; i <= 6; i++) {
            System.out.println("Numero de dado: "+Dado());
        }

        System.out.println(" ");

        //Mostramos el tablero
        System.out.println("Datos dentro de la lista doble enlazada");
        for (int i=0; i<=15;i++){
            System.out.println(" "+ tablero.get(i));
        }


    }

}
