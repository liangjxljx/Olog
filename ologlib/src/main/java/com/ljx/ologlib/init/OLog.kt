package com.ljx.ologlib.init

import android.content.Context
import com.ljx.ologlib.api.ILog
import com.ljx.ologlib.api.LogLevel
import com.ljx.ologlib.api.LogPrinter
import com.ljx.ologlib.config.OLogConfig
import com.ljx.ologlib.logger.Logger

/**
 *  OLog <p>
 *  @author liangjianxiong
 *  @since 2019/07/15
 */
object OLog: ILog(){

    private var logger: Logger? = null

    /**
     * init OLog
     * @param applicationCtx context for get appName
     * @param oLogConfig oklog config model
     */
    @JvmStatic
    fun init(applicationCtx: Context, oLogConfig: OLogConfig){
        if (logger == null){
            logger = Logger()
        }
        logger?.init(applicationCtx,oLogConfig)
    }

    /**
     * set this module log enable write
     * to local logfile
     */
    @JvmStatic
    fun setLogEnable(enable:Boolean,pkgDepth:Int){
        logger?.setLogEnable(enable,pkgDepth)
    }

    /**
     * set log listner
     * use for custom print log
     */
    @JvmStatic
    fun setLogPrinter(logPrinter: LogPrinter){
        logger?.setLogPrinter(logPrinter)
    }

    /**
     * flush memory cache log
     * to file immediately
     */
    fun flush(){
        logger?.flush()
    }

    override fun v(tag: String, value: String) {
        logger?.log(LogLevel.VERBOSE,tag,value)
    }

    override fun d(tag: String, value: String) {
        logger?.log(LogLevel.DEBUG,tag,value)
    }

    override fun i(tag: String, value: String) {
        logger?.log(LogLevel.INFO,tag,value)
    }

    override fun w(tag: String, value: String) {
        logger?.log(LogLevel.WARN,tag,value)
    }

    override fun e(tag: String, value: String) {
        logger?.log(LogLevel.ERROR,tag,value)
    }

    override fun t(tag: String,value: String, tr: Throwable) {
        logger?.log(LogLevel.ERROR,tag,value,tr)
    }
}