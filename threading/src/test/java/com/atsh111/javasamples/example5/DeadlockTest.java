package com.atsh111.javasamples.example5;

import org.apache.commons.lang.time.StopWatch;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 A simple way to simulate deadlock is have 2 methods which needs
 to acquire 2 locks in different order.
 */
public class DeadlockTest {

    @Test
    public void simulateDeadlock(){
        final BooleanLogic logic = new BooleanLogic();
        class BooleanTester implements Runnable{

            public void run() {
                for(int i=0;i<100;i++){
                    logic.setFlag1(logic.or());
                    logic.setFlag2(logic.and());
                }
            }
        }
        ExecutorService service = Executors.newFixedThreadPool(10);

        for(int i=0;i<100;i++){
            service.submit(new BooleanTester());
        }
        StopWatch watch = new StopWatch();
        watch.start();
        int waittime=10;
        try {
            service.awaitTermination(waittime, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        watch.split();
        if(watch.getSplitTime()>= waittime)
            System.out.println("Timed Out");
        else
            System.out.println("NO Deadlock");
    }

}
class BooleanLogic{
   Boolean flag1;
   Boolean flag2;

   public void setFlag1(boolean newval){flag1=newval;}
    public void setFlag2(boolean newval){flag2=newval;}
   public boolean or(){
       synchronized (flag2){
           if(flag2)
               return true;
           else{
               synchronized (flag1){
                   if(flag1)
                       return true;
                   else
                       return false;
               }
           }
       }
   }

   public boolean and(){
       synchronized (flag1) {
           if(!flag1)return false;
           synchronized (flag2) {
                if(!flag2)return false;
                return true;
           }
       }
   }
}
