package com.ljn.demo;


import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by 12390 on 2019/2/9.
 */
public class ProducerAndConsumer1 {
    private final int MAX_SIZE = 10;
    private Queue<Integer> queue = new LinkedList<>();
    class Producer extends Thread{
        @Override
        public void run() {
            while(true){
                synchronized (queue){
                    while(queue.size()>=MAX_SIZE){
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    queue.offer(1);
                    System.out.println("push and current size is " + queue.size());
                    queue.notify();
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }
    class Consumer extends Thread{
        @Override
        public void run() {
            while(true){
                synchronized (queue){
                    while(queue.isEmpty()){
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    queue.poll();
                    System.out.println("pop and current size is " + queue.size());
                    queue.notify();
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    public static void main(String[] args){
        ProducerAndConsumer1 p = new ProducerAndConsumer1();
        Producer producer = p.new Producer();
        Consumer consumer = p.new Consumer();
        producer.start();
        consumer.start();
    }
}
