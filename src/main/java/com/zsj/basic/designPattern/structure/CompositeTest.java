package com.zsj.basic.designPattern.structure;

import java.util.LinkedList;
import java.util.List;

/**
 * 组合模式设计的角色：
 *
 * Component：是组合中的对象声明接口，在适当的情况下，实现所有类共有接口的默认行为。声明一个接口用于访问和管理Component.
 * Leaf：在组合中表示叶子节点对象，叶子节点没有子节点。
 * Composite：定义树枝节点行为，用来存储子部件，在Component接口中实现与子部件有关操作，如增加和删除等。
 * ————————————————
 * 版权声明：本文为CSDN博主「朱小厮」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/u013256816/article/details/51009417
 */
public class CompositeTest {

    public static void main(String[] args) {

        Composite root = new Composite("树根");
        Composite branch01 = new Composite("树枝01");
        Composite branch02 = new Composite("树枝02");
        Composite branch03 = new Composite("树枝03");
        Composite branch04 = new Composite("树枝04");

        branch01.add(new Leaf("树叶01"));
        branch01.add(new Leaf("树叶02"));
        branch03.add(new Leaf("树叶03"));
        branch03.add(new Leaf("树叶04"));
        branch03.add(new Leaf("树叶05"));
        branch01.add(branch03);

        branch02.add(new Leaf("树叶06"));
        branch02.add(new Leaf("树叶07"));
        branch02.add(new Leaf("树叶08"));
        branch04.add(new Leaf("树叶09"));
        branch04.add(new Leaf("树叶10"));
        branch02.add(branch04);

        root.add(branch01);
        root.add(branch02);

        root.operation(0);
    }

}
abstract class Component
{
    protected String name;

    public Component(String name)
    {
        this.name = name;
    }
    protected abstract void operation(int depth);
}
//树叶
class Leaf extends Component
{
    public Leaf(String name)
    {
        super(name);
    }
    @Override
    protected void operation(int depth){
        String temp = "";
        for(int i=0;i<depth;i++)
        {
            temp += "    ";
        }
        System.out.println(temp+this.name);
    }
}
class Composite extends Component{
    private LinkedList<Component> children;

    public Composite(String name)
    {
        super(name);
        this.children = new LinkedList<>();
    }

    public void add(Component component)
    {
        this.children.add(component);
    }

    public void remove(Component component)
    {
        this.children.remove(component);
    }

    public LinkedList<Component> getChildren()
    {
        return children;
    }

    @Override
    protected void operation(int depth)
    {
        String temp = "";
        for(int i=0;i<depth;i++)
        {
            temp += "    ";
        }

        LinkedList<Component> children = this.getChildren();
        System.out.println(temp+this.name);
        for (Component c : children) {
            c.operation(depth+1);
        }
    }
}

