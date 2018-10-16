package cn.jun.test;

import java.util.concurrent.atomic.AtomicInteger;

public class Test {

    public static void main(String[] args){


        AtomicInteger integer = new AtomicInteger(1);
        integer.addAndGet(2);
        System.out.println(integer);

    }
}
