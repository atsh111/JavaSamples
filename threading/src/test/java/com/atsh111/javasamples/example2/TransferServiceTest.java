package com.atsh111.javasamples.example2;

import org.junit.Test;

import java.util.concurrent.ExecutorService;

/**
 * Created by atul on 2/2/2018.
 */
public class TransferServiceTest {


    @Test
    public void testTransferoffunds(){
        class TransferTxn implements Runnable{
            Account from;
            Account to;
            TransferService service;
            public TransferTxn(Account from,Account to){
                this.from = from;this.to=to;
                service = new TransferService();
            }
            public void run() {
                for(int i=0;i<1000;i++){
                    service.transfer(from,to,5);
                }
            }
        }
        Account from = new Account(1000000);
        Account to = new Account(5000);
        Thread t1 = new Thread(new TransferTxn(from,to));
        Thread t2 = new Thread(new TransferTxn(to,from));

        try {
            t1.start();
            t2.start();
            t1.join();
            t2.join();
            System.out.println(from);
            System.out.println(to);
        }
        catch (Exception ex){}


    }
}
