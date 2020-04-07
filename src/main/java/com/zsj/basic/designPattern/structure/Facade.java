package com.zsj.basic.designPattern.structure;

/**
 * 外观 门面
 *
 * 外观模式具有以下两个角色。
 * ■ 外观（Facade）角色：客户端可以调用该角色的方法，该角色知晓相关子系统的功能和责任。正常情况下，本角色会将所有从客户端发来的请求委派到相应的子系统，即该角色没有实际的业务逻辑，只是一个委托类。
 * ■ 子系统（Subsystem）角色：可以同时有一个或多个子系统，每一个子系统都不是一个单独的类，而是一个类的集合。子系统不知道外观角色的存在，对于子系统而言，外观角色仅仅是另外一个客户端而已。
 *
 * 优点：
 *
 * 松散耦合：门面模式松散了客户端与子系统的耦合关系，让子系统内部的模块能更容易扩展和维护
 * 简单易用：门面模式让子系统更加易用，客户端不再需要了解子系统内部的实现，也不需要跟踪多个子系统内部的模块进行交互，只需要跟门面类交互就可以了
 * 更好的划分访问层次：通过合理的使用Facade，可以帮助我们更好的划分访问的层次。有些方法是对系统外的，有些方法是系统内部使用的。把需要暴露给外部的功能集中到门面中，这样既方便客户端使用，也很好地掩藏了内部的细节。
 */
public class Facade {
    public static void main(String[] args) {
        ClassFacade facade = new ClassFacade();
        facade.Method();
    }
}
class Class1 {
    public void op1() {
        System.out.println("方法1");
    }
}
class Class2 {
    public void op2() {
        System.out.println("方法2");
    }
}
class Class3 {
    public void op3() {
        System.out.println("方法3");
    }
}
class ClassFacade {
    private Class1 one = new Class1();
    private Class2 two = new Class2();
    private Class3 three = new Class3();
    public void op1() {
        System.out.println("Facade op1()");
        one.op1();
    }
    public void op2() {
        System.out.println("Facade op2()");
        two.op2();
    }
    public void op3() {
        System.out.println("Facade op3()");
        three.op3();
    }

    public void Method() {
        System.out.println("Facade Method()");
        three.op3();
        two.op2();
        one.op1();
    }
}
