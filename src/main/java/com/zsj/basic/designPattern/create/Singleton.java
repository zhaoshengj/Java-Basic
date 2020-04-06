package com.zsj.basic.designPattern.create;

/**
 * 单例模式有5中写法（线程安全）：
 *
    * 饿汉式
    * 懒汉式
    * 双检索（DCL）
    * 占位符式
    * 枚举式
 */
public class Singleton {
}
//懒汉
class LazySingleton{
    //静态变量启动的时候，会初始化（连接阶段的--准备）
    private static LazySingleton demo = null;
    private LazySingleton(){}
    public static synchronized LazySingleton getInstance(){
        if(demo == null){
            return new LazySingleton();
        }
        return demo;
    }
}
//饿汉
class EagerSingleton{
    private static EagerSingleton demo = new EagerSingleton();
    private EagerSingleton(){}
    public static EagerSingleton getInstance(){
        return demo;
    }
}
//DCL
class DoubleCheckedLockingSingleton{
    //volatile 保证指令不会重排
    private volatile static  DoubleCheckedLockingSingleton  dclSingleton = null;
    private DoubleCheckedLockingSingleton(){}
    public static DoubleCheckedLockingSingleton getInstance(){
        DoubleCheckedLockingSingleton localRef = dclSingleton;
        if(dclSingleton == null){
            synchronized(DoubleCheckedLockingSingleton.class){
                localRef = dclSingleton;
                if(localRef == null){
                    dclSingleton = localRef = new DoubleCheckedLockingSingleton();
                }
            }

        }
        return localRef;
    }

    /**
     *  注意上面的变量localRef，“似乎”看上去显得有点多余。但是实际上绝大多数时候uniqueInstance已经被初始化，
     *  引入localRef可以使得volatile的只被访问一次（利用return localRef代替return helper）,
     *  这样可以使得这个单例的整体性能提升25%
     */

}
//占位符式
class LazyInitHolderSingleton {
    private static class SingletonHolder {
        private static final LazyInitHolderSingleton INSTANCE = new LazyInitHolderSingleton();
    }
    private LazyInitHolderSingleton() {
    }
    public static LazyInitHolderSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
//枚举式
/*enum SingletonClass
{
    INSTANCE;
}*/


