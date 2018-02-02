package com.atsh111.javasamples.example2;

import org.junit.Test;

/**
 * Created by atul on 2/2/2018.
 */
public class BadTransferServiceTest {


    @Test
    public void testBadTransferoffunds(){
        class TransferTxn implements Runnable{
            AccountUnsafe from;
            AccountUnsafe to;
            BadTransferService service;
            public TransferTxn(AccountUnsafe from,AccountUnsafe to){
                this.from = from;this.to=to;
                service = new BadTransferService();
            }
            public void run() {
                for(int i=0;i<1000;i++){
                    service.transfer(from,to,5);
                }
            }
        }
        AccountUnsafe from = new AccountUnsafe(1000000);
        AccountUnsafe to = new AccountUnsafe(5000);
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
