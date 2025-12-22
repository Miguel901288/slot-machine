package org.example;

import org.example.Slots.NoBetException;
import org.example.Slots.SlotMachine;
import org.example.Slots.Wallet;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Wallet wallet = new Wallet(500);
        while (wallet.getBalance() > 0){
            System.out.println("Choose a difficulty: \n" +
                    "1. Easy\n" +
                    "2. Medium\n" +
                    "3. Hard\n" +
                    "4. Impossible");
            int diff = safeReadInt(sc);
            try {
                System.out.println("Enter your bet (Balance: " + wallet.getBalance() + ")");
                double bet = 0.0;
                while(bet == 0.0) {
                    try{
                        bet = sc.nextDouble();
                    } catch(InputMismatchException e){
                        System.out.println("Please enter a valid bet");
                    }
                    sc.nextLine();
                    if (bet > wallet.getBalance()) {
                        System.out.println("You don't have enough balance");
                        bet = -1.0;
                    } else if (bet < 0){
                        System.out.println("You can't bet a negative amount");
                    }
                }
                SlotMachine sm = new SlotMachine(diff, true);
                wallet.withdraw(bet);
                wallet.deposit(sm.run(bet));
                System.out.println("Your balance is now " + wallet.getBalance());
            } catch (NoBetException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Press enter to continue");
            sc.nextLine();
        }
        System.out.println("You're broke!");

    }

    private static int safeReadInt(Scanner sc){
        int result = -1;
        do{
            try{
                result = sc.nextInt();
            } catch(Exception e){
                System.out.println("Please enter a positive integer");
            }
            sc.nextLine();
        } while (result == -1);
        return result;
    }
}