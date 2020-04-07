package com.zsj.basic.designPattern.action;

/**
 * 中介者模式中的角色：
 *
 * 抽象中介者（Mediator)：定义了同事对象到中介者对象之间的接口。
 * 具体中介者（ConcreteMediator)：定义抽象中介者的方法，它需要知道所有的具体同事类，同时需要从具体的同事类那里接收消息，并且向具体的同事类发送信息。
 * 抽象同事类（Colleague）
 * 具体同事类（ConcreteColleague）：每个具体同事类都只需要知道自己的行为即可，但是它们都需要认识中介者。
 * 案例1：
 * 房屋租赁中介就是一个很好的中介模式，租客只知道自己和中介，房东也只知道自己和中介，但是中介必须知道租客和房东。（在我们的生活中处处充斥着“中介者”，比如租房、买房、出过留学、找工作、旅游等可能都需要哪些中介者的帮助。）
 * ————————————————
 * 版权声明：本文为CSDN博主「朱小厮」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/u013256816/article/details/51058254
 */
public class MediatorTest {
    public static void main(String[] args) {
        MediatorStructure mediator = new MediatorStructure();

        HouseOwner houseOwner = new HouseOwner("qq",mediator);
        Tenant tenant = new Tenant("jj",mediator);

        mediator.setHouseOwner(houseOwner);
        mediator.setTenant(tenant);

        tenant.contact("I wanna a house");
        houseOwner.contact("I have~");

    }

}
 interface Mediator
{
    void contact(String message, Person person);
}
abstract class Person
{
    protected String name;
    protected Mediator mediator;

    Person(String name, Mediator mediator)
    {
        this.name = name;
        this.mediator = mediator;
    }
}
class HouseOwner extends Person
{
    HouseOwner(String name, Mediator mediator)
    {
        super(name, mediator);
    }

    public void contact(String message)
    {
        mediator.contact(message, this);
    }

    public void getMessage(String message)
    {
        System.out.println("HouseOwner : "+name+", Get Message: "+message);
    }
}
class Tenant extends Person
{
    Tenant(String name, Mediator mediator)
    {
        super(name, mediator);
    }

    public void contact(String message)
    {
        mediator.contact(message, this);
    }

    public void getMessage(String message)
    {
        System.out.println("Tenant : "+name+", Get Message: "+message);
    }
}
class MediatorStructure implements Mediator
{
    private HouseOwner houseOwner;
    private Tenant tenant;

    public HouseOwner getHouseOwner()
    {
        return houseOwner;
    }
    public void setHouseOwner(HouseOwner houseOwner)
    {
        this.houseOwner = houseOwner;
    }
    public Tenant getTenant()
    {
        return tenant;
    }
    public void setTenant(Tenant tenant)
    {
        this.tenant = tenant;
    }

    @Override
    public void contact(String message, Person person)
    {
        if(person == houseOwner)
        {
            tenant.getMessage(message);
        }
        else
        {
            houseOwner.getMessage(message);
        }
    }
}
