package com.zsj.basic.designPattern.action;

/**
 * 备忘录模式的角色：
 *
 * 原发器（Originator）：负责创建一个备忘录，用以记录当前对象的内部状态，通过也可以使用它来利用备忘录回复内部状态。同时原发器还可以根据需要决定Memento存储Originator的那些内部状态。
 * 备忘录（Memento）：用于存储Originator的内部状态，并且可以防止Originator以外的对象访问Memento。在备忘录Memento中有两个接口，其中Caretaker只能看到备忘录中的窄接口，它只能将备忘录传递给其他对象。Originator可以看到宽接口，允许它访问返回到先前状态的所有数据。
 * 负责人（Caretaker)：负责保存好备忘录，不能对备忘录的内容进行操作和访问，只能够将备忘录传递给其他对象。
 * ————————————————
 * 版权声明：本文为CSDN博主「朱小厮」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/u013256816/article/details/51244961
 */
public class MementoTest {
    public static void main(String[] args) {
        Originator originator = new Originator(100,100);
        System.out.println("Before fighting BOSS...");
        originator.display();

        //存档
        Caretaker caretaker = new Caretaker();
        caretaker.setMemento(originator.saveMemento());

        //Fighting
        System.out.println("Fighting...");
        originator.setBloodValue(20);
        originator.setMagicValue(20);
        originator.display();

        //回复存档
        System.out.println("Restore...");
        originator.restoreMemento(caretaker.getMemento());
        originator.display();

    }
}
class Originator
{
    private int bloodValue;
    private int magicValue;

    public Originator(int bloodValue, int magicValue){
        this.bloodValue = bloodValue;
        this.magicValue = magicValue;
    }

    public int getBloodValue()
    {
        return bloodValue;
    }

    public void setBloodValue(int bloodValue)
    {
        this.bloodValue = bloodValue;
    }

    public int getMagicValue()
    {
        return magicValue;
    }

    public void setMagicValue(int magicValue)
    {
        this.magicValue = magicValue;
    }

    public void display()
    {
        System.out.println("用户当前状态：");
        System.out.println("血量："+getBloodValue()+";蓝量："+getMagicValue());
    }

    public Memento saveMemento()
    {
        return new Memento(getBloodValue(),getMagicValue());
    }

    public void restoreMemento(Memento memento){
        this.bloodValue = memento.getBloodValue();
        this.magicValue = memento.getMagicValue();
    }
}
class Memento
{
    private int bloodValue;
    private int magicValue;

    public int getBloodValue()
    {
        return bloodValue;
    }

    public void setBloodValue(int bloodValue)
    {
        this.bloodValue = bloodValue;
    }

    public int getMagicValue()
    {
        return magicValue;
    }

    public void setMagicValue(int magicValue)
    {
        this.magicValue = magicValue;
    }

    public Memento(int bloodValue, int magicValue)
    {
        this.bloodValue = bloodValue;
        this.magicValue = magicValue;
    }
}
class Caretaker
{
    private Memento memento;

    public Memento getMemento()
    {
        return memento;
    }

    public void setMemento(Memento memento)
    {
        this.memento = memento;
    }
}
