package com.atsh111.javasamples.example2;

/**
 The invariant condition of an account should hold true for
 both the accounts involved in transfer.
 */
public class TransferService {
    public void transfer(Account from,Account to,int amount){
            from.debit(amount);
            to.credit(amount);
    }
}
