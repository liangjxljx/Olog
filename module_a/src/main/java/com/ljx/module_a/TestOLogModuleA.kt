package com.ljx.module_a

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import com.okay.module_a.R
import com.ljx.ologlib.init.OLog

class TestOLogModuleA : AppCompatActivity() {

    var b = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_module_a)
        btnOpen(View(this))
    }

    var i = 1
    fun btnStart(v: View) {
        OLog.v("TestOLogModuleA", "This is ${i++} modue_a log test${Thread.currentThread()}")
        OLog.d("TestOLogModuleA", "This is ${i++} modue_a log test${Thread.currentThread()}")
        OLog.i("TestOLogModuleA", "This is ${i++} modue_a log test${Thread.currentThread()}")
        OLog.w("TestOLogModuleA", "This is ${i++} modue_a log test${Thread.currentThread()}")
        OLog.e("TestOLogModuleA", "This is ${i++} modue_a log test${Thread.currentThread()}")
    }


    fun btnOpen(v: View) {
        b = !b
        OLog.setLogEnable(b, 3)
        if (b) {
            findViewById<Button>(R.id.btnOpen).setText("日志开关：开")
        } else {
            findViewById<Button>(R.id.btnOpen).setText("日志开关：关")
        }
    }


}