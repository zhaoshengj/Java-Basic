package com.zsj.basic.designPattern.structure;

/**
 * 外观 门面
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
