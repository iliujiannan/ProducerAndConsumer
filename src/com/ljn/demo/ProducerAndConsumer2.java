package com.ljn.demo;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by 12390 on 2019/2/9.
 */
public class ProducerAndConsumer2 {
    private final int MAX_SIZE = 10;
    private Queue<Integer> queue = new LinkedList<>();
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    class Producer extends Thread {
        @Override
        public void run() {
            while (true) {
                lock.lock();
                try {
                    while (queue.size() >= MAX_SIZE) {
                        try {
                            condition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    queue.offer(1);
                    System.out.println("push and current size is " + queue.size());
                    condition.signal();

                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } finally {
                    lock.unlock();
                }
            }

        }
    }

    class Consumer extends Thread {
        @Override
        public void run() {
            while (true) {
                lock.lock();
                try {
                    while (queue.isEmpty()) {
                        try {
                            condition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    queue.poll();
                    System.out.println("pop and current size is " + queue.size());
                    condition.signal();
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }finally {
                    lock.unlock();
                }

            }
        }

    }

    public static void main(String[] args) {
        ProducerAndConsumer1 p = new ProducerAndConsumer1();
        ProducerAndConsumer1.Producer producer = p.new Producer();
        ProducerAndConsumer1.Consumer consumer = p.new Consumer();
        producer.start();
        consumer.start();
    }
}
