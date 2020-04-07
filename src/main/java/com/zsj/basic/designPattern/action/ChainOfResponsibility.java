package com.zsj.basic.designPattern.action;

import java.math.BigDecimal;

/**
 * 责任链模式的角色：
 *
 * 抽象处理者角色（Handler）：定义出一个处理请求的接口。如果需要，接口可以定义出一个方法以设定和返回对下家的引用。
 *     这个角色通常由一个Java抽象类或者Java接口实现。上图中Handler类的聚合关系给出了具体子类对下家的引用，抽象方法handlerRequest()规范了子类处理请求的操作。
 * 具体处理者角色（ConcreteHandler）：具体处理者接到请求后，可以选择将请求处理掉，或者将请求传给下家。
 *     举个简单例子：报销流程，项目经理<部门经理<总经理
 *  其中项目经理报销额度不能超过1000，部门经理报销额度不能超过5000，超过5000的则需要总经理审核。
 * ————————————————
 * 版权声明：本文为CSDN博主「朱小厮」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/u013256816/article/details/51058075
 */
public class ChainOfResponsibility {
    public static void main(String[] args) {
        ConsumeHandler project = new ProjectHandler();
        ConsumeHandler dept = new DeptHandler();
        ConsumeHandler general = new GeneralHandler();
        project.setNextHandler(dept);
        dept.setNextHandler(general);
        project.doHandler("jj", new BigDecimal(2000));
        project.doHandler("jj", new BigDecimal(300));
        project.doHandler("qq", new BigDecimal(2000));
        project.doHandler("zzh", new BigDecimal(20000));
        project.doHandler("qq", new BigDecimal(20000));

    }
}
abstract class ConsumeHandler
{
    private ConsumeHandler nextHandler;

    public ConsumeHandler getNextHandler()
    {
        return nextHandler;
    }

    public void setNextHandler(ConsumeHandler nextHandler)
    {
        this.nextHandler = nextHandler;
    }

    public abstract void doHandler(String user, BigDecimal free);
}
class ProjectHandler extends ConsumeHandler
{
    @Override
    public void doHandler(String user, BigDecimal free)
    {
        if(free.doubleValue() < 1000)
        {
            if(user.equals("jj"))
                System.out.println(user+"报销不通过");
            else
                System.out.println(user+"给予报销："+free);
        }
        else
        {
            if(getNextHandler() != null)
            {
                getNextHandler().doHandler(user, free);
            }
        }
    }
}
class DeptHandler extends ConsumeHandler
{
    @Override
    public void doHandler(String user, BigDecimal free)
    {
        if(free.doubleValue() < 5000)
        {
            if(user.equals("qq"))
                System.out.println(user+"报销不通过");
            else
                System.out.println(user+"给予报销："+free);
        }
        else
        {
            if(getNextHandler() != null)
            {
                getNextHandler().doHandler(user, free);
            }
        }
    }
}

class GeneralHandler extends ConsumeHandler
{
    @Override
    public void doHandler(String user, BigDecimal free)
    {
        if(free.doubleValue() >= 5000)
        {
            if(user.equals("zzh"))
                System.out.println(user+"报销不通过");
            else
                System.out.println(user+"给予报销："+free);
        }
        else
        {
            if(getNextHandler() != null)
            {
                getNextHandler().doHandler(user, free);
            }
        }
    }
}
