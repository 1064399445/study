package cn.jun.design_model.acelue;

public class FlyNoWay implements FlyBehavior {

    @Override
    public void fly() {
        System.out.println("我是一个不会飞的鸭子...");
    }
}
