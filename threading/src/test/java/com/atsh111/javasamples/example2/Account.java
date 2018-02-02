package com.atsh111.javasamples.example2;

/**
 * Created by atul on 2/2/2018.
 */
public class Account {
    int credits;
    int debits;
    int balance;
    public Account(int balance){
        this.balance = balance;
        this.credits = balance;
    }

    public int getCredits() {
        return credits;
    }

    public int getDebits() {
        return debits;
    }

    public synchronized void credit(int amount){
            this.credits += amount;
            this.balance += amount;
    }
    public synchronized void debit(int amount){
        this.debits+=amount;
        this.balance-=amount;
    }
    public int getBalance(){
        return this.balance;
    }
    @Override
    public String toString(){
        return ("Account: Credits-Debits = "+(credits-debits)+ " , Balance="+balance);
    }
}
