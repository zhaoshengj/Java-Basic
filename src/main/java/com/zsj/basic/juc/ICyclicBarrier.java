/*
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

/*
 *
 *
 *
 *
 *
 * Written by Doug Lea with assistance from members of JCP JSR-166
 * Expert Group and released to the public domain, as explained at
 * http://creativecommons.org/publicdomain/zero/1.0/
 */

package com.zsj.basic.juc;

import javafx.concurrent.Worker;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 同步帮助，允许一组线程互相等待，以达到共同的障碍点。 CyclicBarriers在涉及固定大小的线程方的程序中很有用，
 * 这些线程有时必须互相等待。该屏障称为* <em>cyclic</em>，因为它可以在释放等待线程后重新使用。
 *
 * <p> {@code CyclicBarrier}支持可选的{@link Runnable}命令，在聚会中的最后一个线程到达之后，
 * 但在释放任何线程之前，每个障碍点运行一次。 此<em>屏障操作</em>很有用对于在任何一方继续之前更新共享状态。
 *
 * <p><b>示例用法：</b>以下是在并行分解设计中使用障碍的示例：
 *
 * <pre> {@code
 * class Solver {
 *   final int N;
 *   final float[][] data;
 *   final CyclicBarrier barrier;
 *
 *   class Worker implements Runnable {
 *     int myRow;
 *     Worker(int row) { myRow = row; }
 *     public void run() {
 *       while (!done()) {
 *         processRow(myRow);
 *
 *         try {
 *           barrier.await();
 *         } catch (InterruptedException ex) {
 *           return;
 *         } catch (BrokenBarrierException ex) {
 *           return;
 *         }
 *       }
 *     }
 *   }
 *
 *   public Solver(float[][] matrix) {
 *     data = matrix;
 *     N = matrix.length;
 *     Runnable barrierAction =
 *       new Runnable() { public void run() { mergeRows(...); }};
 *     barrier = new CyclicBarrier(N, barrierAction);
 *
 *     List<Thread> threads = new ArrayList<Thread>(N);
 *     for (int i = 0; i < N; i++) {
 *       Thread thread = new Thread(new Worker(i));
 *       threads.add(thread);
 *       thread.start();
 *     }
 *
 *     // wait until done
 *     for (Thread thread : threads)
 *       thread.join();
 *   }
 * }}</pre>
 * <p>
 * 在这里，每个工作线程处理矩阵的一行，然后在屏障处等待，直到处理完所有行。
 * 处理完所有行后，将执行提供的{@link Runnable}屏障操作并合并行。
 * 如果合并确定已找到解决方案，则{@code done()}将返回 {@code true}，每个工作人员将终止。
 *
 * <p>如果屏障操作不依赖于被执行时被挂起的当事方，那么当该操作被释放时，该方中的任何线程都可以执行该操作.
 * 为了简化此操作，每次调用{@link #await()}}都会返回该线程在屏障处的到达索引。 然后，您可以选择哪个线程应执行屏障操作，例如
 * <pre> {@code
 * if (barrier.await() == 0) {
 *   // log the completion of this iteration
 * }}</pre>
 *
 * <p> {@code CyclicBarrier}对失败的同步尝试使用全或无中断模型：如果线程由于中断，失败或超时而过早离开屏障，
 * 则所有其他线程在该屏障上等待该点还会通过{@link BrokenBarrierException}或{@link InterruptedException}，如果它们也在大约同一时间被中断）异常离开。
 *
 * <p>内存一致性影响：调用前线程中的操作
 * {@code await()}
 * <a href="package-summary.html#MemoryVisibility"><i>happen-before</i></a>
 * 屏障行动的一部分
 * <i>happen-before</i> 从其他线程中的相应{@code await（）}成功返回之后的操作。
 *
 * @author Doug Lea
 * @see CountDownLatch
 * @since 1.5
 */
public class ICyclicBarrier {
    /**
     * 屏障的每次使用都表示为一个生成实例。 障碍物触发或复位时，发电量会改变。
     * 可能有许多与使用线程屏障的线程相关联的世代-由于非确定性的方式，锁可以分配给等待的线程-但一次只能激活其中一个*{@code count}），
     * 其余所有都损坏或跳闸。 如果有休息，则无需活跃的发电，但无需后续重置。
     */
    private static class Generation {
        boolean broken = false;
    }

    /**
     * 防护栅栏入口的锁
     */
    private final ReentrantLock lock = new ReentrantLock();
    /**
     * 等待直到跳闸的条件
     */
    private final Condition trip = lock.newCondition();
    /**
     * 拦截数
     */
    private final int parties;
    /* 跳闸时运行的命令 */
    private final Runnable barrierCommand;
    /**
     * 当前的一代
     */
    private Generation generation = new Generation();

    /**
     * 仍在等待的聚会数量。从聚会倒数到每一代的0 。在每个新世代或出现故障时，它将重置为各方。
     */
    private int count;

    /**
     * 更新障碍旅行的状态并唤醒所有人。 仅在锁定时调用。
     */
    private void nextGeneration() {
        // 上一代信号完成
        trip.signalAll();
        // 建立下一代
        count = parties;
        generation = new Generation();
    }

    /**
     * 将当前的障碍生成设置为已破坏并唤醒所有人。 仅在锁定时调用。
     */
    private void breakBarrier() {
        generation.broken = true;
        count = parties;
        trip.signalAll();
    }

    /**
     * 主要障碍代码，涵盖了各种政策。
     */
    private int dowait(boolean timed, long nanos)
            throws InterruptedException, BrokenBarrierException,
            TimeoutException {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            final Generation g = generation;

            if (g.broken)
                throw new BrokenBarrierException();

            if (Thread.interrupted()) {
                breakBarrier();
                throw new InterruptedException();
            }

            int index = --count;
            if (index == 0) {  // tripped
                boolean ranAction = false;
                try {
                    final Runnable command = barrierCommand;
                    if (command != null)
                        command.run();
                    ranAction = true;
                    nextGeneration();
                    return 0;
                } finally {
                    if (!ranAction)
                        breakBarrier();
                }
            }

            // 循环直到跳闸，断开，中断或超时
            for (; ; ) {
                try {
                    if (!timed)
                        trip.await();
                    else if (nanos > 0L)
                        nanos = trip.awaitNanos(nanos);
                } catch (InterruptedException ie) {
                    if (g == generation && !g.broken) {
                        breakBarrier();
                        throw ie;
                    } else {
                        // 即使我们没有等待，我们也将完成等待
                        // 已被中断，因此此中断被视为
                        // "belong" 到后续执行。
                        Thread.currentThread().interrupt();
                    }
                }

                if (g.broken)
                    throw new BrokenBarrierException();

                if (g != generation)
                    return index;

                if (timed && nanos <= 0L) {
                    breakBarrier();
                    throw new TimeoutException();
                }
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * 创建一个新的{@code CyclicBarrier}，它将在给定数目的参与者（线程）正在等待时跳闸，
     * 并且在屏障被跳开时将执行给定的屏障动作，由进入屏障的最后一个线程执行。
     *
     * @param parties       障碍解除之前必须调用{@link #await} 的线程数
     * @param barrierAction 当障碍物被绊倒时执行的命令；如果没有动作，则执行{@code null}
     * @throws IllegalArgumentException 如果{@code parties}小于1
     */
    public ICyclicBarrier(int parties, Runnable barrierAction) {
        if (parties <= 0) throw new IllegalArgumentException();
        this.parties = parties;
        this.count = parties;
        this.barrierCommand = barrierAction;
    }

    /**
     * 创建一个新的{@code CyclicBarrier}，当*给定数量的参与者（线程）正在等待它时，它将触发，并且当障碍被触发时，不执行预定义的操作。
     *
     * @param parties 障碍解除之前必须调用{@link #await} 的线程数
     * @throws IllegalArgumentException if {@code parties} is less than 1
     */
    public ICyclicBarrier(int parties) {
        this(parties, null);
    }

    /**
     * 返回克服此障碍所需的各方数目。
     *
     * @return 克服这一障碍所需的各方数量
     */
    public int getParties() {
        return parties;
    }

    /**
     * 等到所有{@linkplain #getParties}都在此屏障上调用{@code await}。
     *
     * <p>如果当前线程不是最后到达的线程，则出于线程调度目的将其禁用，并使其处于休眠状态，直到发生以下情况之一：
     * <ul>
     * <li>最后一个线程到达；
     * <li>其他一些线程{@linkplain Thread#interrupt interrupts} 当前线程；
     * <li>其他一些线程{@linkplain Thread#interrupt interrupts} 其他等待线程之一；
     * <li>其他一些线程在等待屏障时超时；
     * <li>其他一些线程在此屏障上调用{@link #reset}
     * </ul>
     *
     * <p>If the current thread:
     * <ul>
     * <li>has its interrupted status set on entry to this method; or
     * <li>is {@linkplain Thread#interrupt interrupted} while waiting
     * </ul>
     * then {@link InterruptedException} is thrown and the current thread's
     * interrupted status is cleared.
     *
     * <p>If the barrier is {@link #reset} while any thread is waiting,
     * or if the barrier {@linkplain #isBroken is broken} when
     * {@code await} is invoked, or while any thread is waiting, then
     * {@link BrokenBarrierException} is thrown.
     *
     * <p>If any thread is {@linkplain Thread#interrupt interrupted} while waiting,
     * then all other waiting threads will throw
     * {@link BrokenBarrierException} and the barrier is placed in the broken
     * state.
     *
     * <p>如果当前线程是最后到达的线程，并且在构造函数中提供了非null屏障操作，
     * 则当前线程将在允许其他线程继续之前运行该操作。
     * 如果在屏障操作期间发生异常，则该异常将在当前线程中传播，并且屏障处于断开状态。
     *
     * @return the arrival index of the current thread, where index
     * {@code getParties() - 1} indicates the first
     * to arrive and zero indicates the last to arrive
     * @throws InterruptedException   if the current thread was interrupted
     *                                while waiting
     * @throws BrokenBarrierException if <em>another</em> thread was
     *                                interrupted or timed out while the current thread was
     *                                waiting, or the barrier was reset, or the barrier was
     *                                broken when {@code await} was called, or the barrier
     *                                action (if present) failed due to an exception
     */
    public int await() throws InterruptedException, BrokenBarrierException {
        try {
            return dowait(false, 0L);
        } catch (TimeoutException toe) {
            throw new Error(toe); // cannot happen
        }
    }

    /**
     * 等待直到所有{@linkplain #getParties parties}在此屏障上已调用 {@code await}，或者经过了指定的等待时间。
     *
     * <p>如果当前线程不是最后到达的线程，则出于线程调度目的将其禁用，并使其处于休眠状态，直到发生以下情况之一：
     * <ul>
     * <li>The last thread arrives; or
     * <li>The specified timeout elapses; or
     * <li>Some other thread {@linkplain Thread#interrupt interrupts}
     * the current thread; or
     * <li>Some other thread {@linkplain Thread#interrupt interrupts}
     * one of the other waiting threads; or
     * <li>Some other thread times out while waiting for barrier; or
     * <li>Some other thread invokes {@link #reset} on this barrier.
     * </ul>
     *
     * <p>If the current thread:
     * <ul>
     * <li>has its interrupted status set on entry to this method; or
     * <li>is {@linkplain Thread#interrupt interrupted} while waiting
     * </ul>
     * then {@link InterruptedException} is thrown and the current thread's
     * interrupted status is cleared.
     *
     * <p>If the specified waiting time elapses then {@link TimeoutException}
     * is thrown. If the time is less than or equal to zero, the
     * method will not wait at all.
     *
     * <p>If the barrier is {@link #reset} while any thread is waiting,
     * or if the barrier {@linkplain #isBroken is broken} when
     * {@code await} is invoked, or while any thread is waiting, then
     * {@link BrokenBarrierException} is thrown.
     *
     * <p>If any thread is {@linkplain Thread#interrupt interrupted} while
     * waiting, then all other waiting threads will throw {@link
     * BrokenBarrierException} and the barrier is placed in the broken
     * state.
     *
     * <p>If the current thread is the last thread to arrive, and a
     * non-null barrier action was supplied in the constructor, then the
     * current thread runs the action before allowing the other threads to
     * continue.
     * If an exception occurs during the barrier action then that exception
     * will be propagated in the current thread and the barrier is placed in
     * the broken state.
     *
     * @param timeout the time to wait for the barrier
     * @param unit    the time unit of the timeout parameter
     * @return the arrival index of the current thread, where index
     * {@code getParties() - 1} indicates the first
     * to arrive and zero indicates the last to arrive
     * @throws InterruptedException   if the current thread was interrupted
     *                                while waiting
     * @throws TimeoutException       if the specified timeout elapses.
     *                                In this case the barrier will be broken.
     * @throws BrokenBarrierException if <em>another</em> thread was
     *                                interrupted or timed out while the current thread was
     *                                waiting, or the barrier was reset, or the barrier was broken
     *                                when {@code await} was called, or the barrier action (if
     *                                present) failed due to an exception
     */
    public int await(long timeout, TimeUnit unit)
            throws InterruptedException,
            BrokenBarrierException,
            TimeoutException {
        return dowait(true, unit.toNanos(timeout));
    }

    /**
     * 查询此屏障是否处于断开状态。
     *
     * @return {@code true}如果一个或多个参与方由于自构建或最后一次重置以来的中断或超时而突破了此屏障，则屏障操作由于异常而失败；否则{@code false}。
     */
    public boolean isBroken() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return generation.broken;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 将屏障重置为其初始状态。如果任何一方当前正在等待障碍，他们将返回 {@link BrokenBarrierException}。
     * 请注意，在<em> </em>之后重新设置由于其他原因导致破损执行起来很复杂；线程需要以其他某种方式重新同步，并选择一个执行重置。 最好为以后的使用创建一个新的屏障。
     */
    public void reset() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            breakBarrier();   // 打破当前的一代
            nextGeneration(); // 开始新一代
        } finally {
            lock.unlock();
        }
    }

    /**
     * 返回当前在障碍处等待的参与者数量。 此方法主要用于调试和断言。
     *
     * @return {@link #await}中当前被阻止的参与者数量
     */
    public int getNumberWaiting() {
        //通过加锁的形式来处理
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return parties - count;
        } finally {
            lock.unlock();
        }
    }
}

class ICyclicBarrierDemo {

    public static void main(String[] args) throws InterruptedException {
        ICyclicBarrier barrier = new ICyclicBarrier(2, new Runnable() {
            @Override
            public void run() {
                System.out.println("~~~屏障执行结束~~~~");
            }
        });
        for (int i = 0; i < 2; i++) {
            new Thread(new Worker(barrier)).start();

        }
        Thread.sleep(10000);

    }

    static class Worker implements Runnable {

        private final ICyclicBarrier barrier;

        Worker(ICyclicBarrier barrier) {
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + "到达栅栏 A");
                boolean broken = barrier.isBroken();
                System.out.println(broken);
                barrier.await();
                System.out.println(Thread.currentThread().getName() + "---------------冲破栅栏 A");
                barrier.await();
                System.out.println(Thread.currentThread().getName() + "到达栅栏 B");
                barrier.await();
                System.out.println(Thread.currentThread().getName() + "---------------冲破栅栏 B");

            } catch (InterruptedException ex) {
                System.out.println("InterruptedException");
            } catch (BrokenBarrierException ex) {
                System.out.println("BrokenBarrierException");
            }
        }
    }
}
