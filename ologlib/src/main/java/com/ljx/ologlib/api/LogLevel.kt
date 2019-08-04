/*
 * Copyright 2015 Elvis Hew
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ljx.ologlib.api

/**
 * Log level indicate how important the log is.
 *
 *
 * Usually when we log a message, we also specify the log level explicitly or implicitly,
 * so if we setup a log level using `XLog.init(...)`, all the logs which is with
 * a log level smaller than the setup one would not be printed.
 *
 *
 * The priority of log levels is: [.VERBOSE] &lt; [.DEBUG] &lt; [.INFO] &lt;
 * [.WARN] &lt; [.ERROR].
 * <br></br>And there are two special log levels which are usually used for Log#init:
 * [.NONE] and [.ALL], [.NONE] for not printing any log and [.ALL] for
 * printing all logs.
 *
 * @see .VERBOSE
 *
 * @see .DEBUG
 *
 * @see .INFO
 *
 * @see .WARN
 *
 * @see .ERROR
 *
 * @see .NONE
 *
 * @see .ALL
 */
object LogLevel {


    /**
     * Priority constant for the println method; use Log.v.
     */
    @JvmStatic
    val VERBOSE = 2

    /**
     * Priority constant for the println method; use Log.d.
     */
    @JvmStatic
    val DEBUG = 3

    /**
     * Priority constant for the println method; use Log.i.
     */
    @JvmStatic
    val INFO = 4

    /**
     * Priority constant for the println method; use Log.w.
     */
    @JvmStatic
    val WARN = 5

    /**
     * Priority constant for the println method; use Log.e.
     */
    @JvmStatic
    val ERROR = 6

    /**
     * Priority constant for the println method.
     */
    @JvmStatic
    val ASSERT = 7


    /**
     * Log level for XLog#init, printing all logs.
     */
    @JvmStatic
    val ALL = Integer.MIN_VALUE

    /**
     * Log level for XLog#init, printing no log.
     */
    @JvmStatic
    val NONE = Integer.MAX_VALUE

    /**
     * Get a name representing the specified log level.
     *
     *
     * The returned name may be<br></br>
     * Level less than [LogLevel.VERBOSE]: "VERBOSE-N", N means levels below
     * [LogLevel.VERBOSE]<br></br>
     * [LogLevel.VERBOSE]: "VERBOSE"<br></br>
     * [LogLevel.DEBUG]: "DEBUG"<br></br>
     * [LogLevel.INFO]: "INFO"<br></br>
     * [LogLevel.WARN]: "WARN"<br></br>
     * [LogLevel.ERROR]: "ERROR"<br></br>
     * Level greater than [LogLevel.ERROR]: "ERROR+N", N means levels above
     * [LogLevel.ERROR]
     *
     * @param logLevel the log level to get name for
     * @return the name
     */
    @JvmStatic
    fun getLevelName(logLevel: Int): String {
        val levelName: String
        when (logLevel) {
            VERBOSE -> levelName = "V"
            DEBUG -> levelName = "D"
            INFO -> levelName = "I"
            WARN -> levelName = "W"
            ERROR -> levelName = "E"
            ASSERT -> levelName = "A"
            else -> if (logLevel < VERBOSE) {
                levelName = "V-" + (VERBOSE - logLevel)
            } else {
                levelName = "E+" + (logLevel - ERROR)
            }
        }
        return levelName
    }

    /**
     * Get a short name representing the specified log level.
     *
     *
     * The returned name may be<br></br>
     * Level less than [LogLevel.VERBOSE]: "V-N", N means levels below
     * [LogLevel.VERBOSE]<br></br>
     * [LogLevel.VERBOSE]: "V"<br></br>
     * [LogLevel.DEBUG]: "D"<br></br>
     * [LogLevel.INFO]: "I"<br></br>
     * [LogLevel.WARN]: "W"<br></br>
     * [LogLevel.ERROR]: "E"<br></br>
     * Level greater than [LogLevel.ERROR]: "E+N", N means levels above
     * [LogLevel.ERROR]
     *
     * @param logLevel the log level to get short name for
     * @return the short name
     */
    @JvmStatic
    fun getShortLevelName(logLevel: Int): String {
        val levelName: String
        when (logLevel) {
            VERBOSE -> levelName = "V"
            DEBUG -> levelName = "D"
            INFO -> levelName = "I"
            WARN -> levelName = "W"
            ERROR -> levelName = "E"
            ASSERT -> levelName = "A"
            else -> if (logLevel < VERBOSE) {
                levelName = "V-" + (VERBOSE - logLevel)
            } else {
                levelName = "E+" + (logLevel - ERROR)
            }
        }
        return levelName
    }

}
