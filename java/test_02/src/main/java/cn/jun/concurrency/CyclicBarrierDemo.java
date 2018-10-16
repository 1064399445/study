package cn.jun.concurrency;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {

    public static class Dou implements Runnable{
        private int sum = 0;
        @Override
        public void run() {
            sum++;
            System.out.println("大家都到齐了，开始第"+sum+"局斗地主");
        }
    }

    public static void main(String[] args){
        //定义三个人准备斗地主
        CyclicBarrier barrier = new CyclicBarrier(3,new Dou());
        Runnable runnable = () -> {
            while(true){
                try {
                    Thread.sleep(new Random().nextInt(4000));
                }catch (InterruptedException e){};
                System.out.println(Thread.currentThread().getName()+"来了...在准备");
                try {
                    barrier.await();
                }catch (BrokenBarrierException|InterruptedException e){};
            }
        };
        //刘备
        Thread liu = new Thread(runnable,"刘备");
        //张飞
        Thread fei = new Thread(runnable,"张飞");
        //关羽
        Thread yu = new Thread(runnable,"关羽");
        //开始约会
        liu.start();
        fei.start();
        yu.start();
    }

}
