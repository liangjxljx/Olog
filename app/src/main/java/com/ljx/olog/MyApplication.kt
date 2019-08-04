package com.ljx.olog

import android.app.Application
import android.os.Environment
import com.ljx.ologlib.api.LogLevel
import com.ljx.ologlib.config.OLogConfig
import com.ljx.ologlib.init.OLog
import com.okay.sampletamplate.configurtion.Document
import com.okay.sampletamplate.configurtion.TemplateConfiguration
import java.io.File

@Document("README.md")
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        TemplateConfiguration.init(this) {
            item {
                id = 1
                title = "OLog Demo"
                desc = "OLog Demo"
                clazz = TestOLogActivity::class.java

            }
        }

        OLog.init(
            this,
            OLogConfig.Builder()
                .debug(BuildConfig.DEBUG)
                .debugGrade(LogLevel.ALL)
                .releaseGrade(LogLevel.ALL)
                .consoleLogSwitch(true)
                .cacheFile(true)
                .filePath(LogFilePath())
                .cacheAccount(10)
                .fileSplitTime(2)
                .build()
        )
    }

    fun LogFilePath(): String {
        return Environment.getExternalStorageDirectory().absolutePath + File.separator + "ROOT_PATH" + File.separator + "oklog" + File.separator
    }
}