package com.zsj.basic.designPattern.structure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 运用共享技术有效地支持大量细粒度的对象。又名“蝇量模式”。
 *
 * 享元模式采用一个共享来避免大量拥有相同内容对象的开销。这种开销最常见、最直观的就是内存的消耗。享元对象能做到共享的关键是区分内蕴状态（Internal State）和外蕴状态（External State）。
 *  一个内蕴状态是存储在享元对象内部的，并且是不会随环境的改变而有所不同。因此，一个享元可以具有内蕴状态并且可以共享。
 *  一个外蕴状态是随环境的改变而改变的、不可以共享的。享元对象的外蕴状态必须由客户端保存，并在享元对象被创建之后，在需要使用的时候再传入到享元对象内部。外蕴状态不可以影响享元对象的内蕴状态，他们是相互独立的。
 *  享元模式可以分成单纯享元模式和复合享元模式。
 */
public class FlyweightTest {

    public static void main(String[] args) {
        FlyWeightFactory factory = new FlyWeightFactory();
        Flyweight f1 = factory.factory("a");
        Flyweight f2 = factory.factory("b");
        Flyweight f3 = factory.factory("a");

        f1.operation("a fly weight");
        f2.operation("b fly weight");
        f3.operation("c fly weight");

        System.out.println(f1 == f3);
        System.out.println(factory.getFlyWeightSize());

    }
}
interface Flyweight
{
    public void operation(String state);
}
class ConcreteFlyweight implements Flyweight
{
    private String str;

    public ConcreteFlyweight(String str)
    {
        this.str = str;
    }

    @Override
    public void operation(String state)
    {
        System.out.println("内蕴状态："+str);
        System.out.println("外蕴状态："+state);
    }
}
class FlyWeightFactory
{
    private Map<String,ConcreteFlyweight> flyWeights = new HashMap<String, ConcreteFlyweight>();

    public ConcreteFlyweight factory(String str)
    {
        ConcreteFlyweight flyweight = flyWeights.get(str);
        if(null == flyweight)
        {
            flyweight = new ConcreteFlyweight(str);
            flyWeights.put(str, flyweight);
        }
        return flyweight;
    }

    public int getFlyWeightSize()
    {
        return flyWeights.size();
    }
}
class ConcreteCompositeFlyweight implements Flyweight
{
    private Map<String,Flyweight> flyWeights = new HashMap<String, Flyweight>();

    public void add(String key, Flyweight fly)
    {
        flyWeights.put(key, fly);
    }

    @Override
    public void operation(String state)
    {
        Flyweight fly = null;
        for(String s:flyWeights.keySet())
        {
            fly = flyWeights.get(s);
            fly.operation(state);
        }
    }
}
class FlyweightCompositeFactory
{
    private Map<String,Flyweight> flyWeights = new HashMap<String, Flyweight>();

    public Flyweight factory(List<String> compositeStates)
    {
        ConcreteCompositeFlyweight compositeFly = new ConcreteCompositeFlyweight();
        for(String s: compositeStates)
        {
            compositeFly.add(s, this.factory(s));
        }
        return compositeFly;
    }

    public Flyweight factory(String s)
    {
        Flyweight fly = flyWeights.get(s);
        if(fly == null)
        {
            fly = new ConcreteFlyweight(s);
            flyWeights.put(s, fly);
        }

        return fly;
    }
}


