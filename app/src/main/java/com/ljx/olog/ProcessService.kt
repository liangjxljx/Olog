package com.ljx.olog

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.ljx.ologlib.init.OLog
import java.util.concurrent.Executors

class ProcessService : Service() {

    private val mBinder = LocalBinder()

    inner class LocalBinder : Binder() {
        internal// Return this instance of LocalService so clients can call public methods
        val service: ProcessService
            get() = this@ProcessService
    }


    override fun onBind(intent: Intent?): IBinder? {
        startLog()
        return mBinder
    }

    fun startLog(){
        Executors.newSingleThreadExecutor().submit {
            while (true) {
                Thread.sleep(500)
                OLog.v(TestOLogActivity::class.java.simpleName, "Service测试log")
                Thread.sleep(500)
                OLog.d(TestOLogActivity::class.java.simpleName, "Service测试log")
                Thread.sleep(500)
                OLog.i(TestOLogActivity::class.java.simpleName, "Service测试log")
                Thread.sleep(500)
                OLog.w(TestOLogActivity::class.java.simpleName, "Service测试log")
                Thread.sleep(500)
                OLog.e(TestOLogActivity::class.java.simpleName, "Service测试log")
                Thread.sleep(500)
                OLog.t(TestOLogActivity::class.java.simpleName, "Service测试异常", Throwable())
            }
        }
    }

}