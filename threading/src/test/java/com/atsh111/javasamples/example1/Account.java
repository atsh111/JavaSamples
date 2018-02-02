package com.atsh111.javasamples.example1;

/**
 * The invariants of the account class is credits-debits=balance.
 *The account may move from one state to the other,when we credit/debit the account but the
 invariance property must always hold true.
 As we can see the credit and debit operations are not atomic.
 Therefore when we try to credit/debit from multiple threads, the invariance does not hold.
 */
public class Account implements IAccount {
    int credits;
    int debits;
    int balance;
    public Account(int balance){
        this.balance = balance;
        this.credits=balance;
    }

    public int getCredits() {
        return credits;
    }

    public int getDebits() {
        return debits;
    }

    public void credit(int amount){
        this.credits+=amount;
        this.balance+=amount;
    }
    public void debit(int amount){
        this.debits+=amount;
        this.balance-=amount;
    }
    @Override
    public String toString(){
        return ("AccountNotThreadSafe:Credits-Debits = "+(credits-debits)+ " , Balance="+balance);
    }
}
