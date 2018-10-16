package cn.jun.design_model.acelue;

public class DuckTwo extends Duck {

    public DuckTwo(){
        flyBehavior = new FlyNoWay();
        quackBehavior = new QuackMute();
    }

    public void display(){
        System.out.println("我是一只真正的鸭子...");
    }

}
