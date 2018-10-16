package cn.jun.design_model.acelue;

public class Test {

    public static void main(String[] args){
        //定义duckOne
        Duck duckOne = new DuckOne();
        duckOne.setFlyBehavior(new FlyNoWay());
        duckOne.setQuackBehavior(new QuackMute());
        //定义duckTwo
        Duck duckTwo = new DuckTwo();
        duckTwo.setFlyBehavior(new FlyWithWings());
        duckTwo.setQuackBehavior(new QuackSqueak());
        //duckOne------
        duckOne.display();
        duckOne.performFly();
        duckOne.performQuack();
        //duckTwo------
        duckTwo.display();
        duckTwo.performFly();
        duckTwo.performQuack();
    }

}
