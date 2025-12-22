package org.example;

import org.example.Slots.Difficulty;
import org.example.Slots.SlotMachine;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose a difficulty: \n" +
                "1. Easy\n" +
                "2. Medium\n" +
                "3. Hard\n" +
                "4. Impossible");
        int diff = safeReadInt(sc);
        try {
            SlotMachine sm = new SlotMachine(diff, true);
            System.out.println(sm.run(100.0));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static int safeReadInt(Scanner sc){
        int result = -1;
        do{
            try{
                result = sc.nextInt();
            } catch(Exception e){
                System.out.println("Enter a positive integer");
            }
            sc.nextLine();
        } while (result == -1);
        return result;
    }
}