package org.example.Slots;

import java.util.Scanner;

public class SlotMachine {
    private volatile boolean stop;
    private int max;
    private int size;
    private final boolean random;

    public SlotMachine(Difficulty diff, boolean random) {
        this.random = random;

        size = 3;
        switch (diff) {
            case EASY:
                max = 3;
                break;
            case MEDIUM:
                max = 5;
                break;
            case HARD:
                max = 10;
                break;
            case IMPOSSIBLE:
                max = 10;
                size = 9;
        }
    }

    public double run(double bet) throws Exception {
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
                Thread.sleep(300);
            }
        }

        double payout = 0;
        System.out.println("\nfinal: ");
        System.out.println(slots[0] + " " + slots[1] + " " + slots[2]);
        if (slots[0] == slots[1] && slots[1] == slots[2]){
            System.out.println("Congratulations!");
            payout = 300;
        } else {
            System.out.println("Better luck next time!");
            payout = 0;
        }
    }
}