package com.zsj.basic.designPattern.action;

/**
 * 策略模式的角色：
 *
 * 抽象策略角色（Strategy）：策略类，通常由一个接口或者抽象类实现
 * 具体策略角色（ConcreteStrategy)：包装了相关的算法和行为
 * 环境角色(Context)：持有一个策略类的引用，最终给客户端调用
 */
public class StrategyTest {
    public static void main(String[] args) {
        TheContext context = null;
        context = new TheContext(new ConcreteStrategyA());
        context.doOperate();
        context = new TheContext(new ConcreteStrategyB());
        context.doOperate();
        context = new TheContext(new ConcreteStrategyC());
        context.doOperate();

    }
}
 interface Strategy
{
    public void operate();
}
class TheContext
{
    private Strategy strategy;

    public TheContext(Strategy strategy)
    {
        this.strategy = strategy;
    }

    public void doOperate()
    {
        this.strategy.operate();
    }
}

class ConcreteStrategyA implements Strategy
{
    @Override
    public void operate()
    {
        System.out.println("初入东吴....#####");
    }
}

class ConcreteStrategyB implements Strategy
{
    @Override
    public void operate()
    {
        System.out.println("乐而不返....#####");
    }
}

class ConcreteStrategyC implements Strategy
{
    @Override
    public void operate()
    {
        System.out.println("腹背受敌....#####");
    }
}
