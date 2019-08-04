package com.ljx.olog

import android.app.AlertDialog
import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import com.ljx.module_a.TestOLogModuleA
import com.ljx.ologlib.api.LogLevel
import com.ljx.ologlib.api.LogPrinter
import com.ljx.ologlib.config.OLogConfig
import com.ljx.ologlib.init.OLog
import com.okay.sampletamplate.ToolBarActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Executors


class TestOLogActivity : ToolBarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        OLog.setLogPrinter(logPrinter)
    }

    var debuglevel = LogLevel.ALL
    var alertDialog:AlertDialog? = null
    val items = arrayOf("VERBOSE", "DEBUG", "INFO", "WARN", "ERROR","ALL","NONE")
    fun debugLevel(v: View) {
        val alertBuilder = AlertDialog.Builder(this)
        alertBuilder.setTitle("选择Debug LogLevel")
        alertDialog = alertBuilder.setSingleChoiceItems(items, 0,
            DialogInterface.OnClickListener { dialogInterface, i ->
                Toast.makeText(
                    this@TestOLogActivity,
                    items[i],
                    Toast.LENGTH_SHORT
                ).show()
                when(items[i]){
                    items[0]->debuglevel= LogLevel.VERBOSE
                    items[1]->debuglevel= LogLevel.DEBUG
                    items[2]->debuglevel= LogLevel.INFO
                    items[3]->debuglevel= LogLevel.WARN
                    items[4]->debuglevel= LogLevel.ERROR
                    items[5]->debuglevel= LogLevel.ALL
                    items[6]->debuglevel= LogLevel.NONE
                }
                btn_debug.setText("DEBUGLEVEL:"+items[i])
                alertDialog?.dismiss()
            }).create()
        alertDialog?.show()
    }

    var alertDialogb:AlertDialog? = null
    var releaselevel = LogLevel.ALL
    fun releaseLevel(v: View) {
        val alertBuilder = AlertDialog.Builder(this)
        alertBuilder.setTitle("选择Release LogLevel")
        alertDialogb = alertBuilder.setSingleChoiceItems(items, 0,
            DialogInterface.OnClickListener { dialogInterface, i ->
                Toast.makeText(
                    this@TestOLogActivity,
                    items[i],
                    Toast.LENGTH_SHORT
                ).show()
                when(items[i]){
                    items[0]->releaselevel= LogLevel.VERBOSE
                    items[1]->releaselevel= LogLevel.DEBUG
                    items[2]->releaselevel= LogLevel.INFO
                    items[3]->releaselevel= LogLevel.WARN
                    items[4]->releaselevel= LogLevel.ERROR
                    items[5]->releaselevel= LogLevel.ALL
                    items[6]->releaselevel= LogLevel.NONE
                }
                btn_release.setText("RELEASELEVEL:"+items[i])
                alertDialogb?.dismiss()
            }).create()
        alertDialogb?.show()
    }

    var switch:Boolean = true

    fun start(v: View) {
        var threadnum = findViewById<EditText>(R.id.threadnum).text.toString().toInt()
        var ed_cacheAcoount = findViewById<EditText>(R.id.ed_cacheAcoount).text.toString().toInt()
        var ed_divideTime = findViewById<EditText>(R.id.ed_divideTime).text.toString().toInt()
        var ed_path = findViewById<EditText>(R.id.ed_path).text.toString()

        var debug  = true
        radioGroup.setOnCheckedChangeListener { buttonView, isChecked ->
            debug = buttonView.id== R.id.rb_debug
        }

        OLog.init(
            this,
            OLogConfig.Builder()
                .debug(debug)
                .debugGrade(debuglevel)
                .releaseGrade(releaselevel)
                .consoleLogSwitch(true)
                .cacheFile(true)
                .filePath(ed_path)
                .cacheAccount(ed_cacheAcoount)
                .fileSplitTime(ed_divideTime)
                .build()
        )


        if (adapter == null){
            adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,item)
            listview.adapter = adapter
        }else{
            item.clear()
        }
        refreshlist()

        while (threadnum-->0){
            val num = threadnum
            Executors.newCachedThreadPool()
            Executors.newSingleThreadExecutor().submit {
                while (switch) {
                    Thread.sleep(500)
                    OLog.v(TestOLogActivity::class.java.simpleName, "线程${num}测试log:ThreadID${Thread.currentThread().id}")
                    Thread.sleep(500)
                    OLog.d(TestOLogActivity::class.java.simpleName, "线程${num}测试log:ThreadID${Thread.currentThread().id}")
                    Thread.sleep(500)
                    OLog.i(TestOLogActivity::class.java.simpleName, "线程${num}测试log:ThreadID${Thread.currentThread().id}")
                    Thread.sleep(500)
                    OLog.w(TestOLogActivity::class.java.simpleName, "线程${num}测试log:ThreadID${Thread.currentThread().id}")
                    Thread.sleep(500)
                    OLog.e(TestOLogActivity::class.java.simpleName, "线程${num}测试log:ThreadID${Thread.currentThread().id}")
                    Thread.sleep(500)
                    OLog.t(TestOLogActivity::class.java.simpleName, "线程${num}测试log:ThreadID${Thread.currentThread().id}",Throwable())
                }
            }
        }
    }

    var item = arrayListOf<String>()
    val logPrinter = object : LogPrinter {
        override fun print(level: Int, tag: String, value: String) {
            runOnUiThread {
                if(switch){
                    item.add(value)
                    refreshlist()
                }
            }

        }
    }

    var adapter:ArrayAdapter<String>? = null
    fun refreshlist(){
        adapter!!.notifyDataSetChanged()
        if (listview.lastVisiblePosition >= item.size-3){
            listview.smoothScrollToPosition(item.size)
        }
    }


    private val mConnection = object : ServiceConnection {

        override fun onServiceConnected(
            className: ComponentName,
            service: IBinder
        ) {

//            val binder = service as LocalBinder
//            val service = binder.service
//            service.startLog()
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
        }
    }

    fun startS(v: View) {
        bindService(Intent(this, ProcessService::class.java), mConnection, Context.BIND_AUTO_CREATE)
    }

    fun stop(v: View) {
        switch = false
        OLog.setLogEnable(false, 3)
    }

    fun startM(v: View) {
        startActivity(Intent(this, TestOLogModuleA::class.java))
    }

    fun flush(v:View){
        OLog.flush()
    }
}
