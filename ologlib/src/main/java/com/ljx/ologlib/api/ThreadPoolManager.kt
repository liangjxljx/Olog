package com.ljx.ologlib.api

import java.util.concurrent.Executors
import java.util.concurrent.Future

/**
 * 线程池管理类：用于单线程调度
 *
 * @Description: 线程池管理类
 * @author: liangjianxiong
 * @date: 2018/4/15
 */
object ThreadPoolManager {


    private val TAG = ThreadPoolManager::class.java.simpleName

    private val CORE_POOL_SIZE = 5

    /**
     * 对外提供 ThreadPoolExecutor
     * @return executor
     */
    var threadPool = Executors.newSingleThreadExecutor()
        private set

    /**
     * 执行 任务
     *
     * @param r 执行对象
     */
    fun execute(r: Runnable) {
        //不为空，执行
        if(threadPool == null){
            threadPool = Executors.newSingleThreadExecutor()
        }
        threadPool!!.execute(r)
    }

    fun submit(r: Runnable): Future<*> {
        //不为空，执行
        if(threadPool == null){
            threadPool = Executors.newSingleThreadExecutor()
        }
       return threadPool!!.submit(r)
    }

    /**
     * 把线程从线程池中移除
     *
     * @param r 移除的对象
     */
    fun shutdownNow() {
        if (threadPool != null) {
            threadPool!!.shutdownNow()
            threadPool = null
        }
    }

}

