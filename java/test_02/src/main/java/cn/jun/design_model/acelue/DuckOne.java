package cn.jun.design_model.acelue;

public class DuckOne extends Duck {

    public DuckOne(){
        flyBehavior = new FlyWithWings();
        quackBehavior = new QuackSqueak();
    }

    public void display(){
        System.out.println("我是一只假鸭子...");
    }

}
