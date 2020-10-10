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

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * {@link IThreadPoolExecutor}无法执行的任务的处理程序。
 *
 * @since 1.5
 * @author Doug Lea
 */
public interface IRejectedExecutionHandler {

    /**
     * 当{@link IThreadPoolExecutor＃execute execute}无法接受任务时，
     * 可由{@link IThreadPoolExecutor}调用的方法。
     * 当没有更多的线程或队列插槽因为超出了它们的界限而可用时，或者在执行器关闭时，可能会发生这种情况。
     *
     * <p>在没有其他选择的情况下，该方法可能会抛出未经检查的{@link RejectedExecutionException}，并将其传播给{@code execute}的调用者。
     *
     * @param r 请求执行的可运行任务
     * @param executor 试图执行此任务的执行者
     * @throws RejectedExecutionException 如果没有补救措施
     */
    void rejectedExecution(Runnable r, IThreadPoolExecutor executor);
}
