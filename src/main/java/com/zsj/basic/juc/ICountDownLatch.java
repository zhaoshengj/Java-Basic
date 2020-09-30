package com.zsj.basic.juc;

import com.sun.corba.se.impl.orbutil.concurrent.Sync;
import com.zsj.basic.juc.locks.IAbstractQueuedSynchronizer;

import javax.annotation.security.RunAs;
import java.util.concurrent.*;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author ZSJ
 * @date 2020-09-28 16:43
 * @description 信号量学习
 * <p>
 * <p>
 * 允许一个或多个线程等待直到在其他线程中执行的一组操作完成的同步辅助程序。
 * <p>
 * 1、强调的是 并行、而不是并发。一次性操作。如果想使用多次应该使用 {@link CyclicBarrier}
 * 2、如果 count 初始为1 的时候，可以用作锁、或者阻塞线程。
 * 3、实现借助于  AQS
 * <p>
 * 使用场景：
 * 1、实现最大并行性。
 * 2、并行多线程执行任务。
 * 3、死锁检查：一个非常方便的使用场景是你用N个线程去访问共享资源，在每个测试阶段线程数量不同，并尝试产生死锁。
 */
public class ICountDownLatch {

    /**
     * 使用AQS状态表示计数
     */
    private static final class Sync extends IAbstractQueuedSynchronizer {
        private static final long serialVersionUID = 4982264981922014374L;

        // AQS 中同步状态 state 为 volatile 类型 保证数据一致性
        Sync(int count) {
            setState(count);
        }

        int getCount() {
            return getState();
        }

        // 尝试获取 同步状态state  如果为0 的时候则返回1  其他为 -1
        @Override
        protected int tryAcquireShared(int acquires) {
            return (getState() == 0) ? 1 : -1;
        }

        // 释放资源，CAS更新  同步状态state  为true 则表示 state为1   false 表示state !=1
        @Override
        protected boolean tryReleaseShared(int releases) {
            // 递减计数；过渡到零时发出信号
            for (; ; ) {
                int c = getState();
                if (c == 0) {
                    return false;
                }
                int nextc = c - 1;
                // CAS 比较并交换 保证内存数据一致 防止并发
                if (compareAndSetState(c, nextc)) {
                    return nextc == 0;
                }
            }
        }
    }

    private final Sync sync;

    // 初始并行容器 大小 不能小于0  为0 的时候没有什么意义
    public ICountDownLatch(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("count < 0");
        }
        this.sync = new Sync(count);
    }

    /**
     * 当前线程等待 ，直到 锁存器计数为0  除非线程 {@linkplain Thread#interrupt 中断}.
     */
    public void await() throws InterruptedException {
        /**
         * 1、先检查中断状态，如果中断 抛异常 InterruptedException
         * 2、 尝试获取  {@linkplain Sync#tryAcquireShared(int)}
         *     2.1、调用 {@linkplain IAbstractQueuedSynchronizer#doAcquireSharedInterruptibly}
         *          2.1.1、添加共享节点，并且入队，替换尾结点
         *          2.1.2、for死循环，判断首节点的 state 当为0的时候 退出循环。不为0 则阻塞线程
         *          2.1.3、取消正在进行的获取尝试。 {@linkplain IAbstractQueuedSynchronizer#cancelAcquire};
         */
        sync.acquireSharedInterruptibly(1);
    }


    public boolean await(long timeout, TimeUnit unit)
            throws InterruptedException {
        /**
         * 1、先检查中断状态，如果中断 抛异常 InterruptedException
         * 2、 尝试获取  {@linkplain Sync#tryAcquireShared(int)}
         *     2.1、设置最大阻塞时间  {@linkplain IAbstractQueuedSynchronizer#doAcquireSharedNanos}
         *          2.1.1、获取死亡时间、添加共享节点，并且入队，替换尾结点
         *          2.1.2、for死循环，判断首节点的 state 当为0的时候 退出循环。不为0 则阻塞线程
         *          2.1.3、如果超过死亡时间 ，则中断线程。{@linkplain LockSupport#parkNanos};
         *          2.1.4、取消正在进行的获取尝试。 {@linkplain IAbstractQueuedSynchronizer#cancelAcquire};
         */
        return sync.tryAcquireSharedNanos(1, unit.toNanos(timeout));
    }

    public void countDown() {
        /**
         *   1、判断，state为1 时为true  {@linkplain Sync#tryReleaseShared(int)}
         *   2、释放信号  {@linkplain IAbstractQueuedSynchronizer#doReleaseShared}
         */
         boolean b = sync.releaseShared(1);
         System.out.println(" ************* :"+b);
    }

    // 返回 state 值 volatile 修饰
    public long getCount() {
        return sync.getCount();
    }

}
/**
 * 例子 1  乘客上车、司机开车
 */
class Demo1 {

    static ICountDownLatch startSignal = new ICountDownLatch(1);
    static ICountDownLatch doneSignal = new ICountDownLatch(5);

    public static void main(String[] args) throws InterruptedException {

        for (int i = 1; i <= 5; i++) {
            new Thread(new User(startSignal, doneSignal, i)).start();
        }
        System.out.println(" 乘客准备上车啦！！！");
        startSignal.countDown();
        doneSignal.await();
        System.out.println("开车走吧！");
    }

    static class User implements Runnable {
        private final ICountDownLatch start;
        private final ICountDownLatch done;
        private int num;

        public User(ICountDownLatch startSignal, ICountDownLatch doneSignal, int num) {
            this.start = startSignal;
            this.done = doneSignal;
            this.num = num;
        }

        @Override
        public void run() {
            try {
                start.await();
                System.out.println("----- " + num + "号乘客上车 ---- ");
                Thread.currentThread().setName("线程"+num);
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                done.countDown();
            }

        }
    }
}


/**
 * 例子二  拆分任务
 */
class Demo2 {
    static ICountDownLatch doneSignal = new ICountDownLatch(5);
    static Executor executor = Executors.newSingleThreadExecutor();

    public static void main(String[] args) throws InterruptedException {

        System.out.println("等待同时开始工作");
        for (int i = 1; i <= 5; i++) {
            executor.execute(new Work(doneSignal, "工作" + i));
        }
        doneSignal.await();
        System.out.println("工作结束");

    }

    static class Work extends Thread {
        private final ICountDownLatch done;
        private final String workName;

        public Work(ICountDownLatch done, String workName) {
            this.done = done;
            this.workName = workName;
        }

        @Override
        public void run() {
            done.countDown();
            System.out.println(" 执行：" + workName);
        }
    }


}
