package ejemplos;

import java.util.*;

public class ABC {
    public static void main(String[] args) {
        LinkedList<String> fruits = new LinkedList<>();
        fruits.add("Apple");
        fruits.add("Orange");
        fruits.add("Mango");
        String srt = fruits.get(1);
        String srt0 = fruits.listIterator(1).previous();
        String srt1 = fruits.listIterator(1).next();

        System.out.println("Elemento: "+ srt);
        System.out.println("Elemento anterior: "+ srt0);
        System.out.println("Elemento soguiente: "+srt1);
        System.out.println("LinkedList: " + fruits);
    }
}
