package org.example;

import org.example.Slots.Difficulty;
import org.example.Slots.SlotMachine;

public class Main {
    public static void main(String[] args) {
        try {
            SlotMachine sm = new SlotMachine(Difficulty.EASY);
            sm.run(100.0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}