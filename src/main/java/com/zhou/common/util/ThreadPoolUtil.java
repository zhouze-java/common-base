package com.zhou.common.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author 周泽
 * @date Create in 9:51 2021/1/5
 * @Description
 */
public enum ThreadPoolUtil implements ThreadPoolCommon {

    /**
     * 枚举天生的单例
     */
    INSTANCE {
        private final ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("thread-pool-thread-%d").build();

        private final transient ExecutorService threadPoolExecutor = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(5), namedThreadFactory, new ThreadPoolExecutor.CallerRunsPolicy());

        @Override
        public void addExecuteTask(Runnable task) {
            if (task != null) {
                threadPoolExecutor.execute(task);
            }
        }

        @Override
        public <T> Future<T> submitTask(Callable<T> task) {
            if (task != null) {
                return threadPoolExecutor.submit(task);
            }
            return null;
        }

        /**
         * 判断是否全部完成
         *
         * @return boolean
         */
        protected boolean isTerminated() {
            return threadPoolExecutor.isTerminated();
        }

        @Override
        public void shutdown() {
            threadPoolExecutor.shutdown();
        }
    },
}
