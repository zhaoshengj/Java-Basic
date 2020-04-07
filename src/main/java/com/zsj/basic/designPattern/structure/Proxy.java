package com.zsj.basic.designPattern.structure;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 静态代理是由程序猿创建或特定工具自动生成源代码，再对其编译。
 *   在程序运行前，代理类的.class文件就已经存在了。
 *   动态代理是在程序运行时，通过运用反射机制动态的创建而成。
 *
 *   代理模式提供以下3个角色。
 *   ■ 抽象主题（Subject）角色：该角色是真实主题和代理主题的共同接口，以便在任何可以使用真实主题的地方都可以使用代理主题。
 *   ■ 代理主题（Proxy Subject）角色：也叫做委托类、代理类，该角色负责控制对真实主题的引用，负责在需要的时候创建或删除真实主题对象，并且在真实主题角色处理完毕前后做预处理和善后处理工作。
 *   ■ 真实主题（Real Subject）角色：该角色也叫做被委托角色、被代理角色，是业务逻辑的具体执行者。
 */
public class Proxy {
    public static void main(String[] args) {
        Subject subject = new StaticProxy();
        subject.operate();

        Subject o = (Subject) new JDKProxy().newProxyInstance(new RealSubject());
        o.operate();

        Subject instance = (Subject) new CGLIBProxy().getInstance(new RealSubject());
        instance.operate();
    }
}
interface Subject
{
    void operate();
}
class RealSubject implements Subject
{
    @Override
    public void operate()
    {
        System.out.println("RealSubject");
    }
}
class RealSubjectCglib
{
    public String operate(){
        return "RealSubjectCglib";
    }
}

class StaticProxy implements Subject
{
    private Subject subject = null;

    @Override
    public void operate()
    {
        if(subject == null)
            subject = new RealSubject();
        System.out.print("I'm Proxy, I'm invoking...");
        this.subject.operate();
    }
}
class JDKProxy implements InvocationHandler{

    Object object = null;

    public Object newProxyInstance(Object proxyObj){
        this.object = proxyObj;
        Class<?> aClass = object.getClass();
        return java.lang.reflect.Proxy.newProxyInstance(aClass.getClassLoader(),
                aClass.getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("JDK Proxy Start");
        method.invoke(object,args);
        System.out.println("JDK Proxy End ");
        return null;
    }
}
class CGLIBProxy implements MethodInterceptor {

    private Object target;

    public Object getInstance(Object proxyTarget){
        this.target = proxyTarget;
        //Cglib中的加强器，用来创建动态代理
        Enhancer enhancer = new Enhancer();
        //设置要创建动态代理的类
        enhancer.setSuperclass(this.target.getClass());
        //设置回调，这里相当于是对于代理类上所有方法的调用，都会调用Callback，而Callback则需要实现intercept()方法进行拦截
        enhancer.setCallback(this);

        Object obj = enhancer.create();
        return obj;

    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.print("I'm Proxy, I'm invoking...");
        Object object = methodProxy.invokeSuper(o, objects);
        System.out.println(object);
        return object;
    }
}