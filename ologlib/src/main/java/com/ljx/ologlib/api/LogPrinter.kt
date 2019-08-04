package com.ljx.ologlib.api

/**
 * this is a log printer lister
 * for user to print cuntom log
 */
interface LogPrinter {
    fun print(level:Int,tag: String, value: String)
}
