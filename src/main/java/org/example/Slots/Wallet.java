package org.example.Slots;

public class Wallet {
    private double balance;

    public Wallet(){
        balance = 0;
    }

    public Wallet(double balance){
        this.balance = balance;
    }

    public double getBalance(){
        return balance;
    }
    public void deposit(double balance){
        this.balance += balance;
    }
    public void withdraw(double balance){
        this.balance -= balance;
    }
}
