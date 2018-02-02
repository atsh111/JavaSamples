package com.atsh111.javasamples.example1;

/**
 Similar implementation as Account,but we make the credit and debit operations
 syncronized.
 */
public class AccountThreadSafe implements IAccount{
    int credits;
    int debits;
    int balance;
    public AccountThreadSafe(int balance){
        this.balance = balance;
    }

    public int getCredits() {
        return credits;
    }

    public int getDebits() {
        return debits;
    }

    public synchronized void credit(int amount){
        this.credits+=amount;
        this.balance+=amount;
    }
    public synchronized void debit(int amount){
        this.debits+=amount;
        this.balance-=amount;
    }
    @Override
    public String toString(){
        return ("AccountThreadSafe: Credits-Debits = "+(credits-debits)+ " , Balance="+balance);
    }
}
