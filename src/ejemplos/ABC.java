package ejemplos;

import java.util.*;

public class ABC {

    //Función para tirar el Dado
    public static int Dado(){
        return (int)(Math.random()*6+1);
    }

    public static void main(String[] args) {
        LinkedList<String> fruits = new LinkedList<>();

        fruits.add("Apple");
        fruits.add("Orange");
        fruits.add("Mango");

        for (int i = 1; i <= 6; i++) {
            System.out.println("Numero de dado: "+Dado());
        }

        System.out.println("");

        System.out.println("Datos dentro de la lista doble enlazada");
        for (int i=0; i<=2;i++){
            System.out.println(" "+ fruits.get(i));
        }

    }

}
