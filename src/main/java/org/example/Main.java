package org.example;

import org.example.Slots.NoBetException;
import org.example.Slots.SlotMachine;
import org.example.Slots.Wallet;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        File walletFile = new File("wallet.txt");
        Wallet wallet = null;
        if (walletFile.exists()) {
            System.out.println("Load previous wallet? (y/n)");
            char c = sc.next().charAt(0);
            sc.nextLine();
            if (c == 'y' || c == 'Y') {
                try {
                    BufferedReader br = new BufferedReader(new FileReader("wallet.txt"));
                    wallet = new Wallet(Double.parseDouble(br.readLine()));
                    if (wallet.getBalance() <= 0) {
                        System.out.println("Previous wallet was empty. Creating new wallet with 500€");
                    }

                } catch (Exception e) {
                    System.out.println("Error reading wallet. Creating new wallet with 500€");
                }
            }
        }
        if (wallet == null){
            wallet = new Wallet(500);
        }
        while (wallet.getBalance() > 0) {
            System.out.println("Choose a difficulty: \n" +
                    "0. Very Easy\n" +
                    "1. Easy\n" +
                    "2. Medium\n" +
                    "3. Hard\n" +
                    "4. Impossible\n" +
                    "5. Close application");
            int diff = safeReadInt(sc);
            if (diff == 5) {
                break;
            }
            try {
                System.out.println("Enter your bet (Balance: " + wallet.getBalance() + ")");
                double bet = 0.0;
                while (bet == 0.0) {
                    try {
                        bet = sc.nextDouble();
                    } catch (InputMismatchException e) {
                        System.out.println("Please enter a valid bet");
                    }
                    sc.nextLine();
                    if (bet > wallet.getBalance()) {
                        System.out.println("You don't have enough balance");
                        bet = -1.0;
                    } else if (bet < 0) {
                        System.out.println("You can't bet a negative amount");
                    }
                }
                SlotMachine sm = new SlotMachine(diff);
                wallet.withdraw(bet);
                wallet.deposit(sm.run(bet));
                System.out.println("Your balance is now " + wallet.getBalance() + "€");
            } catch (NoBetException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Press enter to continue");
            sc.nextLine();
            if (wallet.getBalance() == 0) {
                System.out.println("You're broke!");
            }
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("wallet.txt"))) {
            bw.write(Double.toString(wallet.getBalance()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static int safeReadInt(Scanner sc) {
        int result = -1;
        do {
            try {
                result = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Please enter a positive integer");
            }
            sc.nextLine();
        } while (result < 0 || result > 5);
        return result;
    }
}