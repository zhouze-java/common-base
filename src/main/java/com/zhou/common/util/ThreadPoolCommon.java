package com.zhou.common.util;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @author 周泽
 * @date Create in 9:49 2021/1/5
 * @Description 线程池规范
 */
public interface ThreadPoolCommon {

    /**
     * 添加任务
     *
     * @param task
     */
    void addExecuteTask(Runnable task);

    /**
     * 提交任务并获取返回值
     *
     * @param task
     * @param <T>
     * @return
     */
    <T> Future<T> submitTask(Callable<T> task);

    /**
     * 关闭线程池
     */
    void shutdown();
}
