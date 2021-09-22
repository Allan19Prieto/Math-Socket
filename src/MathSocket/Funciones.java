package MathSocket;

public class Funciones {


    //Función para lanzar el dado
    public static int Lanzar_Dado(){
        return (int)(Math.random()*6+1);
    }

    //Aleatorio para llenar el tablero
    public static int Ram() { return (int)(Math.random()*13+0); }
}