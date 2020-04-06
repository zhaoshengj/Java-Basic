package com.zsj.basic.designPattern.create;

/**工厂模式可以分为三类：
        简单工厂模式（Simple Factory）
        工厂方法模式（Factory Method）
        抽象工厂模式（Abstract Factory）
 */

public class Factory {

    public static void main(String[] args) {
        //简单工厂
        SimpleFactory.Factory simpleFactory = new SimpleFactory.Factory();
        simpleFactory.getName(0);
        try {
            simpleFactory.getName(SimpleFactory.BM.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //工厂
        ConcreteFactor.Factory factory = new ConcreteFactor.FactoryA();
        factory.make().getName();
        //抽象工厂
        AbstractFactory.Factory factory1 = new AbstractFactory.ConcreteFactory1();
        AbstractFactory.AbstractProductA abstractProductA = factory1.CreateProductA();
        AbstractFactory.AbstractProductB abstractProductB = factory1.CreateProductB();
        abstractProductA.produceA();
        abstractProductB.produceB();
    }

}

/**
 * 优点：专门定义一个工厂类负责创建其他类的实例，最大的优点在于工厂类中包含了必要的逻辑，
 *      根据客户需要的条件动态实例化相关的类。
 * 缺点：当需要增加一种产品时，比如Factory就需要修改简单工厂类SimpleFactory（增加if-else块），这违背了开闭原则。
 */
class SimpleFactory{
    static class Factory{
        void getName(Integer sign){
            if (sign == 0){
                new BM().carName();
            }else if(sign == 1){
                new BC().carName();
            }else {
                System.out.println("不知道");
            }
        }

        //普通应用程序在运行时不应该以反射方式访问对象
        void getName(Class< ? extends Car> c) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
            Car obj = (Car) Class.forName(c.getName()).newInstance();
            obj.carName();
        }
    }

    interface Car{
        void carName();
    }

    static class BM implements Car{
        @Override
        public void carName() {
            System.out.println("宝马");
        }
    }

    static class BC implements Car{
        @Override
        public void carName() {
            System.out.println("奔驰");
        }
    }

}
class ConcreteFactor{

    interface Factory{
        Car make();
    }
    static class FactoryA implements Factory{

        @Override
        public Car make() {
          return new BM();
        }
    }
    static class FactoryB implements Factory{

        @Override
        public Car make() {
            return new BC();
        }
    }

    interface Car{
        void getName();
    }
    static class BM implements Car{

        @Override
        public void getName() {
            System.out.println("宝马");
        }
    }
    static class BC implements Car{

        @Override
        public void getName() {
            System.out.println("奔驰");
        }
    }

}
class AbstractFactory{

    public interface AbstractProductA
    {
        public void produceA();
    }

    public interface AbstractProductB
    {
        public void produceB();
    }

    public interface Factory
    {
        public AbstractProductA CreateProductA();
        public AbstractProductB CreateProductB();
    }

    public static class ConcreteFactory1 implements Factory
    {
        @Override
        public AbstractProductA CreateProductA()
        {
            return new ProductA1();
        }

        @Override
        public AbstractProductB CreateProductB()
        {
            return new ProductB1();
        }
    }
    public static class ConcreteFactory2 implements Factory
    {
        @Override
        public AbstractProductA CreateProductA()
        {
            return new ProductA2();
        }

        @Override
        public AbstractProductB CreateProductB()
        {
            return new ProductB2();
        }
    }

    public static class ProductA1 implements AbstractProductA
    {
        @Override
        public void produceA()
        {
            System.out.println("Im ProductA1!");
        }
    }
    public static class ProductA2 implements AbstractProductA
    {
        @Override
        public void produceA()
        {
            System.out.println("Im ProductA2!");
        }
    }
    public static class ProductB1 implements AbstractProductB
    {
        @Override
        public void produceB()
        {
            System.out.println("Im ProductB1!");
        }
    }
    public static class ProductB2 implements AbstractProductB
    {
        @Override
        public void produceB()
        {
            System.out.println("Im ProductB2!");
        }
    }



}

