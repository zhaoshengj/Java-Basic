package com.zsj.basic.designPattern.create;

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

