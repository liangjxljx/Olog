package com.ljx.ologlib.api

import android.text.TextUtils
import com.ljx.ologlib.init.OLog
import java.io.PrintWriter
import java.io.StringWriter
import java.lang.StringBuilder

object StackTraceUtils {

    val linkSpan = ":"

    /**
     * 获取OkLog 调用栈包名
     * 默认 OLog
     */
    @JvmStatic
    fun getPackName(): String? {
        getUpperStack(OLog::class.java.simpleName)?.let {
            if (!TextUtils.isEmpty(it.className)) {
                val split = it.className.split(".")
                if (split.size <= 3) {
                    return it.className
                } else {
                    return it.className.substring(0,
                        StringUtils.getStringNum(it.className, ".", 3)
                    )
                }
            }
        }
        return null
    }

    @JvmStatic
    fun getPackName(depth: Int): String? {
        getUpperStack(OLog::class.java.simpleName)?.let {
            if (!TextUtils.isEmpty(it.className)) {
                val split = it.className.split(".")
                if (split.size <= depth) {
                    return it.className
                } else {
                    return it.className.substring(0,
                        StringUtils.getStringNum(it.className, ".", depth)
                    )
                }
            }
        }
        return null
    }

    /**
     * 获取任意类调用栈包名
     */
    @JvmStatic
    fun getPackName(simpleName: String): String? {

        getUpperStack(simpleName)?.let {
            if (!TextUtils.isEmpty(it.className)) {
                val split = it.className.split(".")
                if (split.size <= 3) {
                    return it.className
                } else {
                    return it.className.substring(0, it.className.indexOf(".", 3))
                }
            }
        }
        return null
    }

    /**
     *  上一级调用栈信息与vaule 绑定
     */
    @JvmStatic
    fun getStrWithStack(element: StackTraceElement?): String {
        return if (element == null) {
            ""
        } else {
            StringBuilder().append(element.className)
                .append(linkSpan)
                .append(element.fileName)
                .append(linkSpan)
                .append(element.methodName)
                .append(linkSpan)
                .append(element.lineNumber).toString()
        }
    }

    /**
     * 获取我们log的上一级调用栈信息
     */
    @JvmStatic
    fun getUpperStack(simpleName: String): StackTraceElement? {

        val stackTraceElements = Thread.currentThread().stackTrace
        for (i in 0 until stackTraceElements.size - 1) {
            if (stackTraceElements[i].fileName.startsWith(simpleName)
                && stackTraceElements.size > i + 1
                && !stackTraceElements[i + 1].fileName.startsWith(simpleName)
            ) {
                return stackTraceElements[i + 1]
            }
        }
        return null
    }

    /**
     * 获得Throwable异常堆栈信息
     *
     * @param t
     * @return
     */
    @JvmStatic
    fun getStackTraceString(t: Throwable?): String {
        val value: String
        if (t == null) {
            value = ""
        } else {
            val stackTrace = StringWriter()
            t.printStackTrace(PrintWriter(stackTrace))
            value = stackTrace.toString()
        }
        return value
    }


}