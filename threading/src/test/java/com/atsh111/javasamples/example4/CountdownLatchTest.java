package com.atsh111.javasamples.example4;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

/**
 * Countdownlatch acts as a gatekeeper.It will wait until
 * its counter is not 0. Counter is decremented by OrderListener
 * as soon as they receive a valid order from the order queue.
 * Its similar to how limited orders are selected in a flash sale.
 */
public class CountdownLatchTest {

    @Test
    public void testCountdownLatch(){

        int MINNUMOFBIDS = 10;
        final CountDownLatch latch = new CountDownLatch(MINNUMOFBIDS);


        class OrderListener implements Runnable{
            private BlockingQueue<FlashOrder> orders;
            public OrderListener(BlockingQueue<FlashOrder> orders){
                this.orders = orders;
            }
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while(true){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    FlashOrder order = orders.poll();
                    if(order==null){continue;}
                    if(isValidOrder(order)){
                       //save bid in any bidding table
                        System.out.println(order);
                        latch.countDown();
                    }
                }
            }
            private boolean isValidOrder(FlashOrder order){
                if(order.getBidder().length() > 5)
                    return true;
                return false;
            }
        }

        ArrayBlockingQueue<FlashOrder> queue = new ArrayBlockingQueue<FlashOrder>(1000);
        for(int i=0;i<100;i++){
            queue.add(new FlashOrder("Name"+i));
        }
        try {
            for(int i=0;i<10;i++){
                OrderListener orderListener = new OrderListener(queue);
                new Thread(orderListener).start();
            }
            latch.await();
            System.out.println("Selected "+MINNUMOFBIDS+" orders");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
class FlashOrder{
    String bidder;
    public FlashOrder(String bidder){
        this.bidder = bidder;
    }
    public String getBidder(){return bidder;}
    public String toString(){return bidder;}
}
