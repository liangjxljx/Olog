# Information
日志库：<p>
支持不同进程日志输出<br>
支持不同线程日志输出<br>
支持module/独立模块日志打印开关<br>
支持日志级别筛选<br>
支持日志缓存数目设置<br>
支持日志文件按小时分割<br>
支持日志文件路径设置<br>
支持日志json格式化<br>
支持日志xml格式化<br>
支持日志格式化样式自定义<br>
  
  
# use

```
implementation 'com.okay.client_app:s_okloglib:0.0.1-SNAPSHOT'
```
<p>

| 接口 | 参数 | 备注 |
| --- | --- | --- |
| init(Context,OLogConfig) | context,okLogConfig | 初始化OLog |
| setLogEnable(Boolean,Int) | boolean,int | 日志module开关 int为包层级深度(由用户自定义控制该层级下的日志是否输出文件) |
| setLogPrinter(LogPrinter) | LogPrinter | 自定义日志接口 |
| withFormatConfig(formatterConfig: FormatterConfig?):ILog | formatterConfig | 自定义格式化样式 |
| format(f: formatterType,msg: String): String | formatterType,msg | 格式化json or xml数据 |
| setLogPrinter(LogPrinter) | LogPrinter | 自定义日志接口 |
| v(String,String) | tag,value | 打印 VERBOSE 级别日志 |
| d(String,String) | tag,value | 打印 DEBUG 级别日志 |
| i(String,String) | tag,value | 打印 INFO 级别日志 |
| w(String,String) | tag,value | 打印 WARN 级别日志 |
| e(String,String) | tag,value | 打印 ERROR 级别日志 |
| t(String,String,Throwable) | tag,value | 打印 thowable 日志 |

# simple
```kotlin
//初始化
OLog.init(
            this,
            OLogConfig.Builder()
                .debug(BuildConfig.DEBUG)//debug or release
                .debugGrade(LogLevel.ALL)//debug 日志文件输出级别
                .releaseGrade(LogLevel.ALL)//release 日志文件输出级别
                .consoleLogSwitch(true)//console log 开关
                .cacheFile(true)//是否输出日志文件
                .filePath(LogFilePath())//日志文件存放路径
                .cacheAccount(10)//日志内存缓存条数
                .fileSplitTime(2)//日志文件分割时间 0-24小时 按照小时分割文件
                .build()
        )
        
//在任意包下调用 parm2是包层级深度 例如在com.okay.studentapp包下调用下句 则studentapp文件下的所有类日志不生效
OLog.setLogEnable(false,3)

//日志打印回调接口 用于开发者自定义日志输出(此方法不影响OKLog日志打印)
OLog.setLogPrinter(object : LogPrinter{
    // implement method        
})        
   
//会立即刷新内存缓存日志到文件    
OLog.flush()        

//格式化数据
OLog.format(FormatterType.Json,dataJson)//默认格式 格式化json
OLog.format(FormatterType.Xml,dataXml)//默认格式 格式化xml
OLog.withFormatConfig(FormatterConfig()).format(FormatterType.Json,dataJson)//自定义格式 格式化json
OLog.withFormatConfig(FormatterConfig()).format(FormatterType.Xml,dataXml)//自定义格式 格式化xml
```
# show
![Demo演示](https://github.com/liangjxljx/Olog/raw/master/app/src/main/assets/image/device-2019-07-23-112941.png)<br>
![Demo演示](https://github.com/liangjxljx/Olog/raw/master/app/src/main/assets/image/device-2019-07-23-113943.png)<br>

# About
[前期设计](../Summary.md)<br>
[更新记录](../Update.md)<br>

