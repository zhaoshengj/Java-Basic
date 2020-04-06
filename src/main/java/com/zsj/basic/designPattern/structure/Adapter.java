package com.zsj.basic.designPattern.structure;

/**
 * 适配器模式有
 *   类适配器模式和
 *   对象适配器模式两种不同的形式。  建议尽量使用对象适配器的实现方式，符合CARP原则。
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




