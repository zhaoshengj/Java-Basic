package com.zsj.basic.designPattern.create;

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