package com.example.spring.jedis.test.dynamicThreadCorePool;


import java.util.concurrent.*;

/**
 * 动态线程池核心参数设计
 * 1、必须同时设置corePoolSize
 * 2、必须同时设置maximumPoolSize
 * 3、自定义动态的队列
 *
 * 常见问题：
 * 1、线程预热
 *      executor.prestartCoreThread();          // start a core thread
 *      executor.prestartAllCoreThreads();      // start all core thread
 *
 * 2、核心线程如何进行回收
 *  executor.allowCoreThreadTimeOut(true); // 允许核心线程数超时
 *
 *   创建新的工作线程 worker，然后工作线程数进行加一操作。 运行创建的工作线程 worker，开始获取任务 task。 工作线程数量大于最大线程数，
 *   对工作线程数进行减一操作。 返回 null，即没有获取到 task。 清理该任务，流程结束。
 *
 * 1、工作线程
 * 2、核心线程
 * 3、最大线程
 *
 */
public class DynamicThreadCorePool {

    public static void main(String[] args) throws InterruptedException {
        dynamicModifyExecutor();
    }

    private static ThreadPoolExecutor buildThreadPoolExecutor() {
        return new ThreadPoolExecutor(2,
                5,
                60,
                TimeUnit.SECONDS,
                new ResizableCapacityLinkedBlockIngQueue<>(10),
                Executors.defaultThreadFactory());
    }

    private static void dynamicModifyExecutor() throws InterruptedException {
        ThreadPoolExecutor executor = buildThreadPoolExecutor();

        for (int i = 0; i < 15; i++) {
            executor.submit(() -> {
                threadPoolStatus(executor, "创建任务");
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        threadPoolStatus(executor, "改变之前");
        executor.setCorePoolSize(10);
        executor.setMaximumPoolSize(10);
        executor.allowCoreThreadTimeOut(true); // 允许核心线程数超时

        ResizableCapacityLinkedBlockIngQueue queue = (ResizableCapacityLinkedBlockIngQueue)executor.getQueue();
        queue.setCapacity(100);

        executor.prestartCoreThread();
        threadPoolStatus(executor, "改变之后");
        Thread.currentThread().join();


    }


    /**
     * setCorePoolSize方法：允许运行时进行修改
     * 工作线程
     * 核心线程
     * 最大线程
     * <p>
     * 在运行期线程池使用方调用此方法设置corePoolSize之后，线程池会直接覆盖原来的corePoolSize值，并且基于当前值和原始值的比较结果采取不同的处理策略。
     * <p>
     * 对于当前值小于当前工作线程数的情况，说明有多余的worker线程，此时会向当前idle的worker线程发起中断请求以实现回收，多余的worker在下次idel的时候也会被回收；
     * <p>
     * 对于当前值大于原始值且当前队列中有待执行任务，则线程池会创建新的worker线程来执行队列任务，setCorePoolSize具体流程如下：
     */
    private static void threadPoolStatus(ThreadPoolExecutor executor, String name) {
        BlockingQueue<Runnable> queue = executor.getQueue();
        System.out.println(Thread.currentThread().getName() + "-" + name + "-:" +
                "核心线程数：" + executor.getCorePoolSize() +
                " 活动线程数：" + executor.getActiveCount() +
                " 最大线程数：" + executor.getMaximumPoolSize() +
                " 线程池活跃度:" + divide(executor.getActiveCount(), executor.getMaximumPoolSize()) +
                " 任务完成数：" + executor.getCompletedTaskCount() +
                " 队列大小：" + (queue.size() + queue.remainingCapacity() +
                " 当前排队线程数：" + queue.size() +
                " 队列剩余大小" + queue.remainingCapacity() +
                " 队列使用度：" + divide(queue.size(), queue.size() + queue.remainingCapacity())));
    }

    private static String divide(int num1, int num2) {
        return String.format("%1.2f%%", Double.parseDouble(num1 + "") / Double.parseDouble(num2 + "") * 100);
    }

}
