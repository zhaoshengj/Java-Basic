package com.zsj.basic.designPattern.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 迭代器模式角色组成：
 *
 * 抽象容器角色（Aggregate）：负责提供创建具体迭代器角色的接口，一般是一个接口，提供一个iterator()方法，例如java中的Collection接口，List接口，Set接口等。
 * 具体容器角色（ConcreteAggregate）：就是实现抽象容器的具体实现类，比如List接口的有序列表实现ArrayList，List接口的链表实现LinkedList,Set接口的哈希列表的实现HashSet等。
 * 抽象迭代器角色（Iterator）：负责定义访问和遍历元素的接口。
 * 具体迭代器角色（ConcreteIterator）：实现迭代器接口，并要记录遍历中的当前位置。
 * ————————————————
 * 版权声明：本文为CSDN博主「朱小厮」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/u013256816/article/details/51058234
 */
public class IteratorTest {
    public static void main(String[] args) {
        Aggregate ag = new ConcreteAggregate();
        ag.add("zzh");
        ag.add("jj");
        ag.add("qq");
        Iterator it = ag.iterator();
        while(it.hasNext())
        {
            System.out.println(it.next());
        }

    }
}
interface Iterator
{
    public Object next();
    public boolean hasNext();
}
class ConcreteIterator implements Iterator
{
    private List<Object> list = Collections.emptyList();
    private int current = 0;

    public ConcreteIterator(List<Object> list)
    {
        this.list = list;
    }

    @Override
    public Object next()
    {
        Object obj = null;
        if(this.hasNext())
        {
            obj = this.list.get(current++);
        }
        return obj;
    }

    @Override
    public boolean hasNext()
    {
        if(current == list.size())
        {
            return false;
        }
        return true;
    }
}
interface Aggregate
{
    public void add(Object obj);
    public void remove(Object obj);
    public Iterator iterator();
}
class ConcreteAggregate implements Aggregate
{
    private List<Object> list = new ArrayList<>();
    @Override
    public void add(Object obj)
    {
        list.add(obj);
    }

    @Override
    public void remove(Object obj)
    {
        list.remove(obj);
    }

    @Override
    public Iterator iterator()
    {
        return new ConcreteIterator(list);
    }
}
