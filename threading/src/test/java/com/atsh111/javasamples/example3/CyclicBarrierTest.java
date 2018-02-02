package com.atsh111.javasamples.example3;

import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Using Cyclic barrier to simulate searching resumes from 2 lists which
 * can be thought of as 2 job portal feeds. The skills array is sorted
 * on priority of skills.So, the workers will search the resumes
 * on the first skill, if the required no. of count of resumes
 * if fulfilled in the first iteration, the worker thread returns
 * else it will wait for the other worker thread to complete
 * and then widen the search to include the next skill in the skill array.
 * The found resumes are pushed to the result blocking queue.
 *
 */
public class CyclicBarrierTest {


    @Test
    public void findBestResumes(){
        List<Resume> lst1 = new ArrayList<Resume>();
        List<Resume> lst2 = new ArrayList<Resume>();
        for(int i=0;i<10000;i++) {
            lst1.add(new Resume("name"+i,getRandomSkills()));
            lst2.add(new Resume("name"+i,getRandomSkills()));
        }
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        class Worker implements Runnable{
            List<Resume> resumes;
            CyclicBarrier cyclicBarrier;
            BlockingQueue<Resume> results;
            String name;
            int reqCnt;
            public Worker(List<Resume> resumes,CyclicBarrier cyclicBarrier,final BlockingQueue<Resume> results,int reqCount,String name){
                this.resumes = resumes;
                this.cyclicBarrier = cyclicBarrier;
                this.results = results;
                this.name = name;
                this.reqCnt = reqCount;
            }
            public void run() {
                search(new String[]{"java","c#","python"},0);
            }

            private void search(String[] skills,int skillInd) {
                if(skillInd >= skills.length){
                    System.out.println(name+":Not enough resumes matching the skillset");
                    return;
                }
                System.out.println(name+":Searching for skill:"+skills[skillInd]);
                int cnt=0;
                for(Resume res:resumes){
                    if(res.getSkills().contains(skills[skillInd])){
                    results.add(res);
                    cnt++;
                    }
                }
                System.out.println(name+":Thread found "+cnt+" resumes with skills"+ skills[skillInd]);
                try {
                    if(results.size()>reqCnt) {
                        System.out.println(name+":Found Required resumes");
                        cyclicBarrier.reset();
                        return;
                    }
                    cyclicBarrier.await();
                    if(skillInd+1 >= skills.length){
                        System.out.println(name+":Not enough resumes found. exiting");
                        return;
                    }

                    System.out.println("Expanding search to include "+skills[skillInd+1]);
                    search(skills,skillInd+1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }
        BlockingQueue<Resume> results = new ArrayBlockingQueue<Resume>(100000);
        int reqNoOfResumes=10000;
        Thread t1=new Thread(new Worker(lst1,cyclicBarrier,results,reqNoOfResumes,"T1"));
        Thread t2=new Thread(new Worker(lst2,cyclicBarrier,results,reqNoOfResumes,"T2"));
        try{t1.start();t2.start();t1.join();t2.join();}catch (Exception ex){}
        System.out.println(results.size());

    }

    public List<String> getRandomSkills() {
        List<String> skills = new ArrayList<String>();
        Random random = new Random();
        int x = random.nextInt(1000);
        if(x%9==0)
            skills.add("java");
        if(x%3==0)
            skills.add("c#");
        if(x%2==0)
            skills.add("python");
        return skills;
    }

    @Test
    public void testRandomSkillsGenerator(){
        for (int i = 0; i < 100; i++) {
            getRandomSkills();
        }
    }
}
class Resume{
    private String name;
    private List<String> skills;
    public Resume(String name,List<String> skills){
        this.name = name;
        this.skills = skills;
    }

    public String name(){return name;}
    public List<String> getSkills(){
        return skills;
    }

    public String toString(){
        return skills.toString();
    }
}
