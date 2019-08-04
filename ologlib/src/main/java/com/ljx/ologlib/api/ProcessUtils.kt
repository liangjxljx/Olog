package com.ljx.ologlib.api

import android.app.ActivityManager
import android.content.Context

/**
 * 获取当前进程包名
 */
class ProcessUtils {

    companion object {
        /**
         * 获取当前进程的名字，一般就是当前app的包名
         *
         * @param context 当前上下文
         * @return 返回进程的名字
         */
        fun getAppName(context: Context): String? {
            val pid = android.os.Process.myPid() // Returns the identifier of this process
            val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val list = activityManager.runningAppProcesses
            val i = list.iterator()
            while (i.hasNext()) {
                val info = i.next() as ActivityManager.RunningAppProcessInfo
                try {
                    if (info.pid == pid) {
                        // 根据进程的信息获取当前进程的名字
                        return info.processName
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    return null
                }

            }
            // 没有匹配的项，返回为null
            return null
        }
    }

}