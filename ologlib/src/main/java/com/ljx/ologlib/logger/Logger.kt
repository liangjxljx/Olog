package com.ljx.ologlib.logger

import android.content.Context
import android.util.Log
import com.ljx.ologlib.api.*
import com.ljx.ologlib.config.OLogConfig
import com.ljx.ologlib.init.OLog
import com.ljx.ologlib.provider.OLoggerHandler
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.util.*

/**
 * oklog 核心类
 */
class Logger {

    private var okLogConfig = OLogConfig.Builder()
        .debug(true)
        .debugGrade(LogLevel.ALL)
        .releaseGrade(LogLevel.WARN)
        .consoleLogSwitch(true)
        .cacheFile(false)
        .build()

    /**
     * process pkagename
     */
    private var processPkgName: String? = null

    /**
     * module log switchs
     */
    private var moduleMap = mutableMapOf<String, Boolean>()

    private var logPrinterRef:WeakReference<LogPrinter>? = null

    private val simpleDataFormat = SimpleDateFormat("yyyy-MM-dd E HH:mm:ss.SSS")

    private fun getSimpleTimeStr(time: Long): String {
        return simpleDataFormat.format(Date(time))
    }

    fun init(applicationCtx: Context, oLogConfig: OLogConfig) {
        this.okLogConfig = oLogConfig
        processPkgName = ProcessUtils.getAppName(applicationCtx)
        if (this.okLogConfig.filePath != null && processPkgName != null) {
            OLoggerHandler.init(
                processPkgName!!,
                oLogConfig.filePath!!,
                oLogConfig.fileSplitTime,
                oLogConfig.cacheAccount
            )
        }
    }

    /**
     * set module log switch
     */
    fun setLogEnable(enable: Boolean, pkgDepth: Int) {
        val modulePkg = StackTraceUtils.getPackName(pkgDepth)
        if (modulePkg != null) {
            moduleMap!![modulePkg] = enable
        }
    }

    fun setLogPrinter(logPrinter: LogPrinter) {
        logPrinterRef = WeakReference(logPrinter)
    }

    /**
     * log to provider
     */
    fun log(loglevel: Int, tag: String, value: String, tr: Throwable? = null) {
        var valueMsg = value
        if (okLogConfig.consoleLogSwitch) {
            if (tr != null) {
                valueMsg = value + "\n" + Log.getStackTraceString(tr)
            }
            when (loglevel) {
                LogLevel.VERBOSE -> android.util.Log.v(tag, valueMsg)
                LogLevel.ASSERT -> android.util.Log.v(tag, valueMsg)
                LogLevel.DEBUG -> android.util.Log.d(tag, valueMsg)
                LogLevel.INFO -> android.util.Log.i(tag, valueMsg)
                LogLevel.WARN -> android.util.Log.w(tag, valueMsg)
                LogLevel.ERROR -> android.util.Log.e(tag, valueMsg)
                else -> android.util.Log.i(tag, value)
            }
        }

        //call back
        logPrinterRef?.get()?.print(loglevel,tag,value)

        if (okLogConfig.cacheFile) {
            //filter filePath
            if (okLogConfig.filePath == null) {
                return
            }
            //filter debug or release grade
            if (okLogConfig.debug) {
                if (loglevel < okLogConfig.debugGrade) {
                    return
                }
            } else {
                if (loglevel < okLogConfig.releaseGrade) {
                    return
                }
            }
            //filter module switch
            val stackTraceElement = StackTraceUtils.getUpperStack(OLog::class.java.simpleName)
            if (stackTraceElement != null) {
                moduleMap.forEach {
                    if (stackTraceElement.className.startsWith(it.key)) {
                        if (!it.value) {
                            return
                        }
                    }
                }
            }
            //save log to OLoggerHandler
            if (processPkgName != null) {
                val time = System.currentTimeMillis()
                val tid = Thread.currentThread().id
                ThreadPoolManager.submit(Runnable {
                    val log = jointMessage(loglevel, tag, valueMsg, time,tid,stackTraceElement)
                    OLoggerHandler.put(log, time)

                })
            }
        }
    }

    /**
     * jonit log value message
     * with seven parts
     * incloud {loglevel、timestamp、pid、thread-id、pkg、tag、value}
     */
    private fun jointMessage(loglevel: Int, tag: String, value: String, time: Long, tid: Long,element: StackTraceElement?): String {
        var sb = StringBuffer()
        //first part loglevel
        sb.append("[").append(LogLevel.getLevelName(loglevel)).append("]")
        //second part timestamp
        sb.append("[").append(getSimpleTimeStr(time)).append("]")
        //third part pid
        sb.append("[").append("pid:").append(android.os.Process.myPid()).append("]")
        //fourth part thread-id
        sb.append("[").append("tid:").append(tid).append("]")
        //fifth part pkg
        sb.append("[").append(StackTraceUtils.getStrWithStack(element)).append("]")
        //sixth part tag
        sb.append("[").append(tag).append("]")
        //seventh part value
        sb.append("[").append(value).append("]")
        return sb.toString()
    }

    /**
     * flush all cache log to file immediately
     */
    fun flush() {
        ThreadPoolManager.submit(Runnable {
          OLoggerHandler.flush()
        })
    }

}