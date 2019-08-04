package com.ljx.ologlib.config

import com.ljx.ologlib.api.LogLevel

/**
 *
 * OLog Confing class
 * use for init oklog
 */
class OLogConfig private constructor() {

    /**
     * debug mode筛选日志级别 gradle
     * 打印>=gradle 级别的日志到控制台
     * @param grade
     * defdault >= LogLevel.ALL
     */
    var debugGrade: Int = LogLevel.ALL

    /**
     * release mode筛选日志级别 gradle
     * 打印>=gradle 级别的日志到控制台
     * @param grade
     * defdault >= LogLevel.ALL
     */
    var releaseGrade: Int = LogLevel.ALL

    /**
     * debug mode or release mode
     * @param debug {ture is debug false id release}
     */
    var debug:Boolean = false

    /**
     * 是否缓存文件
     * @param cacheFile
     * * default = true
     */
    var cacheFile: Boolean = true

    /**
     * log文件缓存地址
     * @param filePath
     * default = null
     * 不设置则
     * @see cacheFile = false
     */
    var filePath: String? = null

    /**
     * cache log number
     *
     * @param cacheAccount
     *{@link in OLoggerHandler.CACHEACCOUNT_MIN..OLoggerHandler.CACHEACCOUNT_MAX }
     *
     * if(list<log>.size>cacheAccount)
     *     flushToFile()
     * else
     *     addlist(list<log>)
     *
     * if(cacheAccount=0|1)
     *   flushToFileImmediately()
     *
     * default = 10
     */
    var cacheAccount: Int = 10

    /**
     * console log switch
     * @param consoleLogSwitch
     * default = true
     */
    var consoleLogSwitch = true

    /**
     * log file devide time
     * @param fileSplitTime
     * {defalut = 2 hours  24 is not devide must in 0 until 24}
     */
    var fileSplitTime = 24

    class Builder() {
        private var debug:Boolean = false
        private var debugGrade: Int = LogLevel.ALL
        private var releaseGrade: Int = LogLevel.ALL
        private var cacheFile: Boolean = true
        private var filePath: String? = null
        private var cacheAccount: Int = 10
        private var consoleLogSwitch: Boolean = true
        private var fileSplitTime: Int = 24

        fun debug(debug: Boolean): Builder {
            this.debug = debug
            return this
        }

        fun debugGrade(debugGrade: Int): Builder {
            this.debugGrade = debugGrade
            return this
        }

        fun releaseGrade(releaseGrade: Int): Builder {
            this.releaseGrade = releaseGrade
            return this
        }

        fun cacheFile(cacheFile: Boolean): Builder {
            this.cacheFile = cacheFile
            return this
        }

        fun filePath(filePath: String): Builder {
            this.filePath = filePath
            return this
        }

        fun cacheAccount(cacheAccount: Int): Builder {
            this.cacheAccount = cacheAccount
            return this
        }

        fun consoleLogSwitch(consoleLogSwitch: Boolean): Builder {
            this.consoleLogSwitch = consoleLogSwitch
            return this
        }

        fun fileSplitTime(fileSplitTime: Int): Builder {
            this.fileSplitTime = fileSplitTime
            return this
        }

        fun build(): OLogConfig {
            var okLogConfig = OLogConfig()
            okLogConfig.debug = this.debug
            okLogConfig.debugGrade = this.debugGrade
            okLogConfig.releaseGrade = this.releaseGrade
            okLogConfig.cacheFile = this.cacheFile
            okLogConfig.filePath = this.filePath
            okLogConfig.cacheAccount = this.cacheAccount
            okLogConfig.consoleLogSwitch = this.consoleLogSwitch
            okLogConfig.fileSplitTime = this.fileSplitTime
            return okLogConfig
        }
    }

}