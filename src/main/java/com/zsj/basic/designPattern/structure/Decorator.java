package com.zsj.basic.designPattern.structure;

import java.math.BigDecimal;

/**
 * 装饰模式中的角色有：
 *
 * 抽象构件角色（Component）：给出一个抽象接口，以规范准备接受附加责任的对象。
 * 具体构件角色（ConcreteComponent）：定义一个将要接收附加责任的类。
 * 装饰角色（Decorator）：持有一个构件（Component）对象的实例，并定义一个与抽象构件接口一致的接口。
 * 具体装饰角色（ConcreteDecorator）：负责给构件对象“贴上”附加的责任。
 */
public class Decorator {

    public static void main(String[] args) {
        Pancake pancake = new CoarsePancake();
        Condiment egg = new Egg(pancake);
        Condiment ham = new Ham(egg);
        ham.sold();
        Condiment lettuce = new Lettuce(ham);
        lettuce.sold();

    }
}

abstract class Pancake
{
    protected String name;
    public String getName()
    {
        return this.name;
    }
    public abstract BigDecimal getPrice();
}
class CoarsePancake extends Pancake
{
    public CoarsePancake(){
        this.name = "杂粮煎饼";
    }

    @Override
    public BigDecimal getPrice()
    {
        return new BigDecimal(5);
    }
}
abstract class Condiment extends Pancake
{
    public abstract String getName();

    public void sold()
    {
        System.out.println(getName()+"："+getPrice());
    }
}
class Egg extends Condiment
{
    private Pancake pancake;
    public Egg(Pancake pancake)
    {
        this.pancake = pancake;
    }

    @Override
    public String getName()
    {
        return pancake.getName()+",加鸡蛋";
    }

    @Override
    public BigDecimal getPrice()
    {
        return pancake.getPrice().add(new BigDecimal(1.5));
    }
}

class Ham extends Condiment
{
    private Pancake pancake;
    public Ham(Pancake pancake)
    {
        this.pancake = pancake;
    }
    @Override
    public String getName()
    {
        return this.pancake.getName()+",加火腿";
    }

    @Override
    public BigDecimal getPrice()
    {
        return pancake.getPrice().add(new BigDecimal(2));
    }
}

class Lettuce extends Condiment
{
    private Pancake pancake;
    public Lettuce(Pancake pancake)
    {
        this.pancake = pancake;
    }
    @Override
    public String getName()
    {
        return this.pancake.getName()+",加生菜";
    }

    @Override
    public BigDecimal getPrice()
    {
        return pancake.getPrice().add(new BigDecimal(1));
    }
}

