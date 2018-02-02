package com.atsh111.javasamples.example2;

/**
 * Created by atul on 2/2/2018.
 */
public class AccountUnsafe {
    private int balance;
    public AccountUnsafe(int initialBalance){
        this.balance = initialBalance;
    }

    public int getBalance(){
        return balance;
    }
    public void setBalance(int newBal){
        this.balance = newBal;
    }
    public String toString(){
        return ("Balance = "+balance);
    }
}
