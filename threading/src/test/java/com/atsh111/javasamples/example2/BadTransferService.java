package com.atsh111.javasamples.example2;

/**
 * Created by atul on 2/2/2018.
 */
public class BadTransferService {
    public void transfer(AccountUnsafe from,AccountUnsafe to,int amount){
        from.setBalance(from.getBalance()-amount);
        to.setBalance(to.getBalance()+amount);
    }
}
