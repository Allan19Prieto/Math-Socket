package ejemplos;

import java.util.*;

public class ABC {

    //Función para tirar el Dado
    public static int Dado(){
        return (int)(Math.random()*6+1);
    }
    //Aleatorio para llenar el tablero
    public static int Ram(){
        return (int)(Math.random()*14+0);
    }

    public static void main(String[] args) {
        //LKL para el tablero del juego
        LinkedList<String> tablero = new LinkedList<>();


        //Arreglo que contrendra las palabras
        String[] palabras = new String[14];

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
        //Solo par aver si se lleno correcta mente
        //System.out.println("ejemplo lista palabras");
        for (int y=0; y<=13; y++){
            //System.out.println(palabras[y]);
        }
        //System.out.println(" ");

        //Llenar el tablero con las palabras aleatoriamente
        for (int t=0; t<=13; t++){
            tablero.add(palabras[Ram()]);
        }

        for (int i = 1; i <= 6; i++) {
            //System.out.println("Numero de dado: "+Dado());
        }

        //System.out.println(" ");

        //Mostramos el tablero
        //System.out.println("Datos dentro de la lista doble enlazada");
        for (int i=0; i<=13;i++){
            //System.out.println(" "+ tablero.get(i));
        }

        System.out.println("Lista de nuemros aleatorios sin repetir");
        ArrayList<Integer> num = new ArrayList<Integer>();

        int var;

        while (num.size() <= 14){
            var = Ram();
            if (!num.contains(var)) {
                num.add(var);
                System.out.println(var);
            }
        }




    }

}
