## design

1. 进程同步用contentprovider 
	数据阈值由用户控制 
	提供立即刷新文件接口

2. 数据结构
	list<Pair<String进程名,String日志>>
	进程名决定文件夹
	日志格式 时间yyyyMMDDhhmmss_模块名_tag_内容

	满足到多少条自动刷新文件并清除缓存
	单线程阻塞存储文件

3. 控制台输出是立即输出
	模块独立
	Map<包名，boolean> 模块开关 独立库开关
	开关决定日志是否写入文件
	目前提供API设置  
	后续会有UI功能

4. 提供日志打印接口回调
	提供格式化输出

5. 日志文件名	
	日期 - 1-24
	memory 1-100M
	提供开放设置接口
