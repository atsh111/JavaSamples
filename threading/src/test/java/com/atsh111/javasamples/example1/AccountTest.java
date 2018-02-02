package com.atsh111.javasamples.example1;

import com.atsh111.javasamples.example1.Account;
import org.junit.Test;

/**
 * Created by atul on 2/2/2018.
 */
public class AccountTest {


    @Test
    public void testUnsafeAccount(){
        Account account = new Account(0);
        testAccountWithMulThreads(account);
        AccountThreadSafe accountThreadSafe = new AccountThreadSafe(0);
        testAccountWithMulThreads(accountThreadSafe);
    }

    public void testAccountWithMulThreads(IAccount account){
        class Transact implements Runnable{
            IAccount account;
            public Transact(IAccount account){
                this.account = account;
            }
            public void run() {
                for(int i=0;i<100000;i++){
                    account.credit(10);
                }
            }
        }
        Thread t1 = new Thread(new Transact(account));
        Thread t2 = new Thread(new Transact(account));
        t1.start();
        t2.start();
        try{
            t1.join();
            t2.join();
        }catch (Exception ex){

        }
        System.out.println(account);
    }



}
