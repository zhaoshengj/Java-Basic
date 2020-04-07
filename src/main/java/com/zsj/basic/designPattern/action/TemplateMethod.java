package com.zsj.basic.designPattern.action;

/**
 * 模板方法模式涉及两个角色。
 * ■ 抽象模板（Abstract Template）角色：该角色定义一个或多个抽象操作，以便让子类实现；
 * 这些抽象操作是基本操作，是一个顶级逻辑的组成步骤。还需要定义并实现一个或几个模板方法，
 * 这些模板方法一般是具体方法，即一个框架，实现对基本方法的调度，完成固定的逻辑。
 * ■ 具体模板（Concrete Template）角色：该角色实现抽象模板中定义的一个或多个抽象方法，
 * 每一个抽象模板角色都可以有任意多个具体模板角色与之对应，而每一个具体模板角色都可以给出这些抽象方法的不同实现，
 * 从而使得顶级逻辑的实现各不相同。
 *
 *
 * 基本方法又可以分为三种：抽象方法（Abstract Method）、具体方法(Concrete Method)和钩子方法(Hook Method)。
 *  抽象方法：一个抽象方法由抽象类声明，由具体子类实现。
 *  具体方法：一个具体方法由抽象类声明并实现，而子类并不实现或置换。
 *  钩子方法：一个钩子方法由抽象类声明并实现，而子类会加以扩展。通常抽象类给出的实现是一个空实现，作为方法的默认实现。这种空的钩子方法叫做“Do Nothing Hook"。钩子方法的名字应当以do开始，这是熟悉设计模式的Java开发人员的标准做法。譬如HttpServlet类中，也遵从这一命名规则：doGet, doPost等。 在HttpServlet中模板方法由service()方法担任，基本方法由doPost(),doGet()等方法担任。
 * ————————————————
 * 版权声明：本文为CSDN博主「朱小厮」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/u013256816/article/details/51018676
 */
public class TemplateMethod {
    public static void main(String[] args) {
        ConcreteTemplate concreteTemplate = new ConcreteTemplate();
        concreteTemplate.templateMethod();
    }

}
abstract class AbstractTemplate
{
    public void templateMethod(){
        abstractMethod();
        doHookMethod();
        concreteMethod();
    }

    protected abstract void abstractMethod();
    protected void doHookMethod(){}
    protected final void concreteMethod(){
        System.out.println("invoke concreteMethod");
    };
}
class ConcreteTemplate extends AbstractTemplate
{
    @Override
    protected void abstractMethod()
    {
        System.out.println("ConcreteTemplate-abstractMethod");
    }

    @Override
    public void doHookMethod()
    {
        System.out.println("ConcreteTemplate-doHookMethod");
    }
}
