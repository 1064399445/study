package cn.jun.concurrency;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {

    public static void main(String[] args){
        //定义三个人准备斗地主
        CountDownLatch countDownLatch = new CountDownLatch(3);
        Runnable runnable = ()->{
            try {
                Thread.sleep(new Random().nextInt(4000));
            }catch (InterruptedException e){};
            System.out.println(Thread.currentThread().getName()+"来了...在准备");
            countDownLatch.countDown();
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
        try {
            countDownLatch.await();
        }catch (InterruptedException e){};
        System.out.println("大家都到齐了，开始斗地主");
    }

}
