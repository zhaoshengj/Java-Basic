package com.zsj.basic.designPattern.structure;

/**
 * 适配器模式有
 *   类适配器模式和
 *   对象适配器模式两种不同的形式。  建议尽量使用对象适配器的实现方式，符合CARP原则。
 *
 *   适配器模式涉及以下3个角色。
 *   ■ 目标（Target）角色：该角色定义要转换成的目标接口。
 *   ■ 源（Adaptee）角色：需要被转换成目标角色的源角色。
 *   ■ 适配器（Adapter）角色：该角色是适配器模式的核心，其职责是通过继承或是类关联的方式，将源角色转换为目标角色。
 */
public class Adapter {

    public static void main(String[] args) {
        Target adapter = new ClassAdapter();
        adapter.request();

        ObjectAdapter objectAdapter = new ObjectAdapter(new Adaptee());
        objectAdapter.request();
    }

}
interface Target
{
    public void request();
}
class Adaptee
{
    public void specificRequest()
    {
        System.out.println("被适配的类Adaptee");
    }
}
class ClassAdapter extends Adaptee implements Target
{
    @Override
    public void request()
    {
        super.specificRequest();
    }
}
class ObjectAdapter implements Target
{
    private Adaptee adaptee;

    public ObjectAdapter(Adaptee adaptee)
    {
        this.adaptee = adaptee;
    }

    @Override
    public void request()
    {
        this.adaptee.specificRequest();
    }
}




