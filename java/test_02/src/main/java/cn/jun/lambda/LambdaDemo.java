package cn.jun.lambda;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LambdaDemo {

    public static void main(String[] args){
        //不声明类型，不加大括号
        MathDemo math1 = (a,b) -> a-b;
        System.out.println(math1.operation(3,2));
        //声明类型，不加大括号
        MathDemo math2 = (int a,int b) -> a+b;
        System.out.println(math2.operation(3,2));
        //声明类型，加大括号
        MathDemo math3 = (int a,int b) -> { return a*b; };
        System.out.println(math3.operation(3,2));
        //单个参数可以不加括号
        SayMessage say1 = message -> System.out.println(message);
        say1.say("哈哈");

        //使用lambda实现foreach
        Map items = new HashMap<>();
        items.put("A", 10);
        items.put("B", 20);
        items.put("C", 30);
        items.put("D", 40);
        items.put("E", 50);
        items.put("F", 60);
        items.forEach((k,v)->{System.out.println(k+":"+v);});

        List list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
        list.add("E");
        list.forEach( v -> System.out.println(v));

    }

    interface MathDemo{
         int operation(int a,int b);
    }

    interface SayMessage{
        void say(String message);
    }

}
