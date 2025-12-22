package org.example.Slots;

public enum Difficulty {
    EASY(1),
    MEDIUM(2),
    HARD(3),
    IMPOSSIBLE(4);

    private int value;

    Difficulty(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
