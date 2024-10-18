package ru.mpei;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        TripletDeque<String> myList = new TripletDeque<>(1000);
        for (int i = 0; i < 5; i++){
            myList.addFirst(i+"");
        }
        myList.addFirst("1");
        myList.addLast("1");



        myList.removeLastOccurrence("1");
        myList.removeLastOccurrence("1");

        myList.print();
//

//        myList.removeLastOccurrence("17");
//        myList.print();


//        myList.removeLastOccurrence("6");
//        myList.removeLastOccurrence("7");
//        myList.removeLastOccurrence("8");
//        myList.removeLastOccurrence("9");
//        myList.print();
//
//
//
//        for(int i = 0; i < 100; i++){
//            System.out.println(myList.removeLast());
//
//        }
//        myList.print();









    }
}
