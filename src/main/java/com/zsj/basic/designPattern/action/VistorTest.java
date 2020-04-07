package com.zsj.basic.designPattern.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 访问者模式的角色：
 *
 * 访问者角色（Visitor）：抽象类或者接口，声明访问者可以访问那些元素，具体到程序中就是visit方法中的参数定义哪些对象是可以被访问的。
 * 具体访问者角色（Concrete Visitor）：实现每个访问者角色（Visitor）声明的操作。
 * 元素角色（Element）：抽象类或者接口，定义一个Accept操作，声明接收哪一类访问者访问。抽象元素角色一般有两类方法，一部分是本身的业务逻辑，另外就是允许接收哪类访问者来访问。
 * 具体元素角色（Concrete Element）：实现由元素角色提供的Accept操作。
 * 对象结构角色（Object Structure）：这是使用访问者模式必备的角色。它要具备一下特征：能枚举它的元素；可以提供一个高层的接口以允许该访问者访问它的元素；可以是一个复合（组合）或是一个集合，如一个列表或一个无序集合。
 * ————————————————
 * 版权声明：本文为CSDN博主「朱小厮」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/u013256816/article/details/51245073
 */
public class VistorTest {
    public static void main(String[] args) {
        List<Element> list = ObjectStructure.getList();
        for(Element e:list){
            e.accept(new Visitor());
        }

    }
}

 interface IVistor
{
    public void visit(ConcreteElement1 e1);
    public void visit(ConcreteElement2 e2);
}
 abstract class Element
{
    public abstract void accept(IVistor visitor);
    public abstract void doSomething();
}
 class Visitor implements IVistor
{
    @Override
    public void visit(ConcreteElement1 e1)
    {
        e1.doSomething();
    }

    @Override
    public void visit(ConcreteElement2 e2)
    {
        e2.doSomething();
    }
}
 class ConcreteElement1 extends Element
{
    @Override
    public void accept(IVistor visitor)
    {
        visitor.visit(this);
    }

    @Override
    public void doSomething()
    {
        System.out.println("Element1");
    }
}
 class ConcreteElement2 extends Element
{
    @Override
    public void accept(IVistor visitor)
    {
        visitor.visit(this);
    }

    @Override
    public void doSomething()
    {
        System.out.println("Element2");
    }
}
 class ObjectStructure
{
    public static List<Element> getList()
    {
        List<Element> list = new ArrayList<Element>();
        Random ran = new Random();
        for(int i=0;i<10;i++)
        {
            int a = ran.nextInt(100);
            if(a>50)
                list.add(new ConcreteElement1());
            else
                list.add(new ConcreteElement2());
        }
        return list;
    }
}
