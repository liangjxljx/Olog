package com.ljx.ologlib.provider

import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

object OLoggerHandler {

    internal data class LogItem(
        val value: String? = null,
        val date: Long = 0
    )

    /**
     * log cache
     */
    private val logs = HashMap<String, MutableList<LogItem>>()
    /**
     * file rootPath
     */
    private lateinit var fileRootPath: String
    /**
     * log file split templete string
     */
    private val OKLOG_FILE_NAME_TEMPLATE = "%sokLog_%s_%s~%s.log"
    /**
     * log file path templete string
     */
    private val OKLOG_FILE_PATH__NAME_TEMPLATE = "%s%s${File.separator}full_log${File.separator}"
    /**
     * crash log file path templete string
     */
    private val OKLOG_CRASH_FILE_PATH__NAME_TEMPLATE = "%s%s${File.separator}crash_log${File.separator}"
    /**
     * log file date simpleDataFormat
     */
    private val simpleDataFormat = SimpleDateFormat("yyyyMMdd")
    /**
     * log file divide by time hours
     * default = 2 hours
     */
    private var logFileDivideTime = 24
    /**
     * log memory cache number
     * @param cacheAccount {deflaut = 10 in 1-9999}
     *
     */
    private var cacheAccount = 10
    /**
     * log cache max account
     */
    private val CACHEACCOUNT_MAX = 1000
    /**
     * log cache min account
     */
    private val CACHEACCOUNT_MIN = 0

    private val NEW_LINE = System.getProperty("line.separator")
    /**
     * processName
     */
    private lateinit var processPkgName:String

    private lateinit var processLogFilePath:String

    private lateinit var processCrashFilePath:String
    /**
     * init OLoggerHandler
     */
    fun init(processPkgName: String,filePath: String, divideTime: Int, logCacheAccount: Int) {
        OLoggerHandler.processPkgName = processPkgName
        if (!filePath.endsWith(File.separator)) {
            fileRootPath = filePath + File.separator
        } else {
            fileRootPath = filePath
        }
        processLogFilePath = String.format(
            OKLOG_FILE_PATH__NAME_TEMPLATE,
            fileRootPath, processPkgName)
        processCrashFilePath = String.format(
            OKLOG_CRASH_FILE_PATH__NAME_TEMPLATE,
            fileRootPath, processPkgName)
        Int
        val logfile = File(processLogFilePath)
        if (!logfile.exists()) {
            try {
                logfile.mkdirs()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        val crashLogfile = File(processCrashFilePath)
        if (!crashLogfile.exists()) {
            try {
                crashLogfile.mkdirs()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if (0 < divideTime && divideTime <= 24) {
            logFileDivideTime = divideTime
        }

        if (logCacheAccount > CACHEACCOUNT_MIN && logCacheAccount < CACHEACCOUNT_MAX) {
            cacheAccount = logCacheAccount
        }
    }

    /**
     * put one log into memory
     */
    fun put(value: String, date: Long) {
        val logItem = LogItem(value, date)
        var log_p = logs.get(processPkgName)
        if (log_p == null) {
            var log_new = mutableListOf(logItem)
            log_p = log_new
            logs.put(processPkgName, log_new)
        } else {
            log_p.add(logItem)
        }
        if (log_p.size >= cacheAccount) {
            flush()
        }
    }

    /**
     * flush memory log to file and delete memory
     */
    fun flush() {
        //核心代码 输入log到文件 清除内存缓存
        val log_p = logs.get(processPkgName)
        if (log_p != null && log_p.size > 0) {
            //如果按小时分割log文件
            val date = log_p.last().date
            val curDate = Date(date)
            var startHour = 0
            var endHour = 23
            if (logFileDivideTime != 24) {
                startHour = (curDate.hours / logFileDivideTime) * logFileDivideTime
                endHour = startHour + logFileDivideTime - 1
            }
            val logFileName = String.format(
                OKLOG_FILE_NAME_TEMPLATE,
                processLogFilePath,
                simpleDataFormat.format(curDate), startHour, endHour)
            val file = File(logFileName)
            if (!file.exists()) {
                try {
                    file.createNewFile()
                    file.setWritable(true)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            try {
                if (file.exists()) {
                    val fw = FileWriter(file,true)
                    for (log_p_item in log_p) {
                        fw.write(log_p_item.value)
                        fw.write(NEW_LINE)
                    }
                    log_p.clear()
                    fw.flush()
                    fw.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }


}