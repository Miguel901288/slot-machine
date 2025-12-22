package org.example.Slots;

import java.util.Scanner;

public class SlotMachine {
    private volatile boolean stop;
    private int max;
    private final boolean random;

    public SlotMachine(int diff, boolean random) {
        this.random = random;

        switch (diff) {
            case 0:
                max = 2;
                break;
            case 1:
                max = 3;
                break;
            case 2:
                max = 5;
                break;
            case 3:
                max = 10;
                break;
            case 4:
                max = 999;
                break;
            default:
                max = 10;
        }
    }

    public double run(double bet) throws NoBetException {
        if (bet < 1){
            throw new NoBetException("You must bet at least 1€");
        }
        Scanner sc = new Scanner(System.in);
        int[] slots = {0, 0, 0};
        for (int i = 0; i < slots.length; i++) {
            stop = false;

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
                    slots[i] = (slots[i] + 1) % 10;
                System.out.print("\r" + slots[0] + " " + slots[1] + " " + slots[2]);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {}
            }
        }

        double payout = 0;
        System.out.println("\nfinal: ");
        System.out.println(slots[0] + " " + slots[1] + " " + slots[2]);
        if (slots[0] == slots[1] && slots[1] == slots[2]){
            payout = bet * max * 3;
            System.out.println("Congratulations! You won " + payout + "€!");
        } else {
            System.out.println("Better luck next time!");
            payout = 0;
        }
        return payout;
    }
}