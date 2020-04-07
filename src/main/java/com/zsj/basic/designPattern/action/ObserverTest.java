package com.zsj.basic.designPattern.action;

import java.util.ArrayList;
import java.util.List;

/**
 * 观察者模式的角色
 *
 * 抽象主题角色(Subject)：把所有对观察者对象的引用保存在一个集合中，每个抽象主题角色都可以有任意数量的观察者。抽象主题提供一个接口，可以增加和删除观察者角色。一般用一个抽象类和接口来实现。
 * 具体主题角色(ConcreteSubject)：在具体主题内部状态改变时，给所有登记过的观察者发出通知。具体主题角色通常用一个子类实现。
 * 抽象观察者角色（Observer）：为所有的管擦着定义一个接口，在得到主题的通知更新自己。
 * 具体观察者角色(ConcreteObserver)：该角色实现抽象观察者角色所要求的更新接口，以便使本身的状态和主题的状态相协调。通常用一个子类实现。如果需要，具体观察者角色可以保持一个指向具体主题角色的引用。
 * ————————————————
 * 版权声明：本文为CSDN博主「朱小厮」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/u013256816/article/details/51245002
 */
public class ObserverTest {
    public static void main(String[] args) {
        //推
        ConcreteSubject subject = new ConcreteSubject();
        Observer observer = new ConcreteObserver();
        subject.attach(observer);
        subject.setState("new State");

        //拉
        ConcreteSubject2 subject2 = new ConcreteSubject2();
        Observer2 observer2 = new ConcreteObserver2();
        subject2.attach(observer2);
        subject2.setState("new State");

    }
}
//推模式
interface Observer
{
    public void update(String str);
}
class ConcreteObserver implements Observer
{
    private String observerState;
    @Override
    public void update(String state)
    {
        observerState = state;
        System.out.println("状态为: "+observerState);
    }
}

abstract class Subject
{
    private List<Observer> list = new ArrayList<>();

    public void attach(Observer observer)
    {
        list.add(observer);
    }

    public void detach(Observer observer)
    {
        list.remove(observer);
    }

    public void notifyObservers(String str)
    {
        int len = list.size();
        for(int i=0;i<len;i++)
        {
            list.get(i).update(str);
        }
    }
}
class ConcreteSubject extends Subject
{
    private String subjectState;

    public String getState(){
        return subjectState;
    }

    public void setState(String newState)
    {
        this.subjectState = newState;
        System.out.println("主题状态为： "+subjectState);
        this.notifyObservers(subjectState);
    }
}
//拉模式
 interface Observer2
{
    public void update(Subject2 subject);
}
 class ConcreteObserver2 implements Observer2
{
    private String observerState;
    @Override
    public void update(Subject2 subject)
    {
        observerState = ((ConcreteSubject2)subject).getState();
        System.out.println("状态为: "+observerState);
    }
}
abstract class Subject2
{
    private List<Observer2> list = new ArrayList<>();

    public void attach(Observer2 observer)
    {
        list.add(observer);
    }

    public void detach(Observer2 observer)
    {
        list.remove(observer);
    }

    public void notifyObservers()
    {
        int len = list.size();
        for(int i=0;i<len;i++)
        {
            list.get(i).update(this);
        }
    }
}
class ConcreteSubject2 extends Subject2
{
    private String subjectState;

    public String getState(){
        return subjectState;
    }

    public void setState(String newState)
    {
        this.subjectState = newState;
        System.out.println("主题状态为： "+subjectState);
        this.notifyObservers();
    }
}
