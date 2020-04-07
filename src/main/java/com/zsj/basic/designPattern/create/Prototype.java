package com.zsj.basic.designPattern.create;

/**
 * 原型模式涉及3个角色。
 *  ■ 客户（Client）角色：该角色提出创建对象的请求。
 *  ■ 抽象原型（Prototype）角色：该角色是一个抽象角色，通常由一个Java接口或抽象类实现，给出所有的具体原型类所需的接口。
 *  ■ 具体原型（Concrete Prototype）角色：该角色是被复制的对象，必须实现抽象原型接口。
 */
public class Prototype {
    public static void main(String[] args)
    {
        ProtoType pt1 = new ProtoType();
        pt1.setName("protoType_1");
        ProtoType pt2 = pt1.clone();
        System.out.println(pt1 == pt2);
        System.out.println(pt1.getClass() == pt2.getClass());
        pt2.setName("protoType_2");
        System.out.println(pt1.getName()+" "+pt2.getName());
    }

}
class ProtoType implements Cloneable
{
    private String name;

    @Override
    public ProtoType clone()
    {
        try
        {
            return (ProtoType)super.clone();
        }
        catch (CloneNotSupportedException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}

