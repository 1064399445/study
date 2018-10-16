package cn.jun.design_model.acelue;

public class QuackMute implements QuackBehavior{

    @Override
    public void quack() {
        System.out.println("我并不会叫...");
    }
}
