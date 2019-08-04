package com.ljx.ologlib.api

import com.okay.exlog.formatter.FormatterType
import com.ljx.ologlib.formatter.FormatterConfig
import com.ljx.ologlib.formatter.JsonPrinter
import com.ljx.ologlib.formatter.XmlPrinter


/**
 * oklog扩展功能接口
 */
abstract class ILog : LogRequire {

    protected var formatterType: FormatterType = FormatterType.None
    protected var formatterConfig: FormatterConfig? = null

    /**
     * 设置格式化配置属性
     * 非必须调用
     */
    fun withFormatConfig(config: FormatterConfig?): ILog {
        formatterConfig = config
        return this@ILog
    }

    /**
     * format msg
     */
    fun format(f: FormatterType,msg: String): String {
        formatterType = f
        var sb = StringBuffer()
        when (formatterType) {
            FormatterType.Json -> {
                var jp = JsonPrinter()
                formatterConfig?.let {
                    jp.setConfig(formatterConfig)
                }
                val items = jp.format(msg)
                for (i in items.indices) {
                    sb.append(items.get(i))
                }
                return sb.toString()
            }
            FormatterType.Xml -> {
                var xp = XmlPrinter()
                formatterConfig?.let {
                    xp.setConfig(formatterConfig)
                }
                val items = xp.format(msg)
                for (i in items.indices) {
                    sb.append(items.get(i) + "\n")
                }
                return sb.toString()
            }
            FormatterType.None -> {
                return msg
            }
        }
    }
}

/**
 * log 基础功能接口
 */
interface LogRequire {

    fun v(tag: String, value: String)
    fun d(tag: String, value: String)
    fun i(tag: String, value: String)
    fun w(tag: String, value: String)
    fun e(tag: String, value: String)

    /**
     * 打印异常堆栈信息
     * 级别默认
     */
    fun t(tag: String, value: String,tr: Throwable)

}

