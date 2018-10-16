package cn.jun.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreDemo {

    public static void main(String[] args){
        //定义10个许可的信号量
        Semaphore semaphore = new Semaphore(10);
        //线程池
        ExecutorService executor = Executors.newFixedThreadPool(50);
        for(int i=0;i<50;i++){
            Thread thread = new Thread( ()-> {
                try {
                    synchronized (executor){
                        //获得许可
                        semaphore.acquire();
                        System.out.println(Thread.currentThread().getName()+"获得了信号量");
                        System.out.println("当前可用的资源为:"+semaphore.availablePermits());
                    }
                    //do something
                    Thread.sleep(2000);
                    synchronized (semaphore){
                        //释放许可
                        semaphore.release();
                        System.out.println(Thread.currentThread().getName()+"释放了信号量");
                        System.out.println("当前可用的资源为:"+semaphore.availablePermits());
                    }
                } catch (Exception e){

                }
            });
            executor.submit(thread);
        }
        executor.shutdown();
    }

}
