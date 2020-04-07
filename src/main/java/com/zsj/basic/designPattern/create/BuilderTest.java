package com.zsj.basic.designPattern.create;

/**
 * 在建造者模式中，有如下4个角色。
 *  ■ 抽象建造者（Builder）角色：该角色用于规范产品的各个组成部分，并进行抽象，一般独立于应用程序的逻辑。
 *  ■ 具体建造者（Concrete Builder）角色：该角色实现抽象建造者中定义的所有方法，并且返回一个组建好的产品实例。
 *  ■ 产品（Product）角色：该角色是建造中的复杂对象，一个系统中会有多于一个的产品类，这些产品类并不一定有共同的接口，完全可以是不相关联的。
 *  ■ 导演者（Director）角色：该角色负责安排已有模块的顺序，然后告诉 Builder开始建造。
 */
public class BuilderTest {

    public static void main(String[] args) {
        CarDirector carDirector = new CarDirector();
        Car car = carDirector.constructCar(new ConcreteBuilder());
        System.out.println(car.toString());
    }



}
class CarDirector{

    public Car constructCar(Builder builder)
    {
        builder.buildEngine();
        builder.buildSkeleton();
        builder.buildWheel();
        return builder.buildCar();
    }
}
abstract class Builder{

    public abstract void buildWheel();
    public abstract void buildSkeleton();
    public abstract void buildEngine();

    abstract Car buildCar();
}
class ConcreteBuilder extends Builder{
    private Car car = new Car();
    @Override
    public void buildWheel() {
        car.setWheel("车轮");
    }

    @Override
    public void buildSkeleton() {
        car.setSkeleton("车身结构");
    }

    @Override
    public void buildEngine() {
        car.setEngine("发动机");
    }

    @Override
    Car buildCar() {
        return car;
    }
}

class Car
{
    private String wheel;
    private String skeleton;
    private String engine;

    public String getWheel() {
        return wheel;
    }

    public void setWheel(String wheel) {
        this.wheel = wheel;
    }

    public String getSkeleton() {
        return skeleton;
    }

    public void setSkeleton(String skeleton) {
        this.skeleton = skeleton;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    @Override
    public String toString() {
        return "Car{" +
                "wheel='" + wheel + '\'' +
                ", skeleton='" + skeleton + '\'' +
                ", engine='" + engine + '\'' +
                '}';
    }
}