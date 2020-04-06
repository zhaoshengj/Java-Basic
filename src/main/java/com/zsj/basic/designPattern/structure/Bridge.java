package com.zsj.basic.designPattern.structure;

/**
 * 桥接模式所涉及的角色
 *
 * Abstraction：定义抽象接口，拥有一个Implementor类型的对象引用
 * RefinedAbstraction：扩展Abstraction中的接口定义
 * Implementor：是具体实现的接口，Implementor和RefinedAbstraction接口并不一定完全一致，实际上这两个接口可以完全不一样Implementor提供具体操作方法，而Abstraction提供更高层次的调用
 * ConcreteImplementor：实现Implementor接口，给出具体实现
 * ————————————————
 * 版权声明：本文为CSDN博主「朱小厮」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/u013256816/article/details/51000327
 */
public class Bridge {
    public static void main(String[] args) {
        Restaurant rest = new XiaoNanGuo();
        AbstractCityArea sr = new ShanghaiRestaurant(rest);
        sr.commentTaste();

        Restaurant rest1 = new WaiPojia();
        AbstractCityArea sr1 = new NanjingRestaurant(rest1);
        sr1.commentTaste();

    }
}
interface Restaurant
{
    public String taste();
}
 class XiaoNanGuo implements Restaurant
{
    @Override
    public String taste()
    {
        return "红烧肉比较好吃";
    }
}
 class WaiPojia implements Restaurant
{
    @Override
    public String taste()
    {
        return "红烧肉比较一般";
    }
}
abstract class AbstractCityArea
{
    protected Restaurant restaurant;

    public AbstractCityArea(Restaurant restaurant)
    {
        this.restaurant = restaurant;
    }

    public abstract void commentTaste();
}
class NanjingRestaurant extends AbstractCityArea
{
    public NanjingRestaurant(Restaurant restaurant)
    {
        super(restaurant);
    }

    @Override
    public void commentTaste()
    {
        System.out.println("南京的"+super.restaurant.taste());
    }
}
class ShanghaiRestaurant extends AbstractCityArea
{
    public ShanghaiRestaurant(Restaurant restaurant)
    {
        super(restaurant);
    }

    @Override
    public void commentTaste()
    {
        System.out.println("上海的"+super.restaurant.taste());
    }
}
