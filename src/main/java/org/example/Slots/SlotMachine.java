package org.example.Slots;

import java.util.Scanner;

public class SlotMachine {
    private volatile boolean stop; //User input
    private int max; //Amount of numbers to roll (e.g. 10 means 0 to 9)
    private boolean random; //Whether the numbers roll randomly or from 1 to 10
    private int time; //Time between rolls

    public SlotMachine(int diff) {
        random = false;
        switch (diff) {
            case 0:
                time = 500;
                max = 2;
                break;
            case 1:
                time = 400;
                max = 5;
                break;
            case 2:
                time = 300;
                max = 8;
                break;
            case 3:
                time = 250;
                max = 10;
                break;
            case 4:
                time = 50;
                random = true;
                max = 1000;
                break;
            default:
                time = 300;
                max = 10;
        }
    }

    public double run(double bet) throws NoBetException {
        if (bet < 1){
            throw new NoBetException("You must bet at least 1€");
        }
        Scanner sc = new Scanner(System.in);
        int[] slots = {0, 0, 0};
        System.out.println("Press enter to stop the slots!");
        for (int i = 0; i < slots.length; i++) {
            stop = false;

            //Thread to track user input without stopping the slot loop
            Thread inputThread = new Thread(() -> {
                sc.nextLine();
                stop = true;
            });
            inputThread.start();

            // Spin current slot
            while (!stop) {
                if (random)
                    slots[i] = (int) Math.floor(Math.random() * max);
                else
                    slots[i] = (slots[i] + 1) % max;
                System.out.print("\r" + slots[0] + " " + slots[1] + " " + slots[2]);
                try {
                    Thread.sleep(time);
                } catch (InterruptedException ignored) {}
            }
        }

        double payout = 0;
        System.out.println("\nfinal: ");
        System.out.println(slots[0] + " " + slots[1] + " " + slots[2]);
        if (slots[0] == slots[1] && slots[1] == slots[2]){
            payout = bet * max;
            System.out.println("Congratulations! You won " + payout + "€!");
        } else {
            System.out.println("Better luck next time!");
            payout = 0;
        }
        return payout;
    }
}