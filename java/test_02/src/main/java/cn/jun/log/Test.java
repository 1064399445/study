package cn.jun.log;

import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Test {

    public static void main(String[] args) throws Exception{
        HashMap<String,String> map = new HashMap<>();
        map.put("","");
        map.remove("");
        map.remove("","");
        map.get("");
        System.out.println(12^12);
        Executor executor = Executors.newFixedThreadPool(10);
        executor.execute(new Runnable() {
            @Override
            public void run() {

            }
        });

    }

}
