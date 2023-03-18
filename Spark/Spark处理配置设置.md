## spark配置参数设置



```properties
driver.memory ：driver运行内存，默认值512m，一般2-6G
num-executors ：集群中启动的executor总数
executor.memory ：每个executor分配的内存数，默认值512m，一般4-8G
executor.cores ：每个executor分配的核心数目
 
yarn.am.memory ：AppMaster内存，默认值512m
yarn.am.memoryOverhead ：am堆外内存，值为 AM memory * 0.07, 最小384
yarn.driver.memoryOverhead ：driver堆外内存，Max(384MB, 7% of spark.executor.memory)
yarn.executor.memoryOverhead ：executor堆外内存，值为 executorMemory * 0.07, 最小384
```

**每个executor从Yarn请求的内存 =**  **executor.memory + yarn.executor.memoryOverhead**



## spark on yarn模式，yarn资源管理设置



```properties
yarn.app.mapreduce.am.resource.mb ：AM能够申请的最大内存，默认值为1G，一般1-2G
yarn.nodemanager.resource.memory-mb ：nodemanager能够申请的最大内存，默认值为8G
yarn.scheduler.maximum-allocation-mb ：调度时一个container能够申请的最大资源，默认值为8G
```



## **不同配置的优劣分析**

```properties
配置：
3台     48核    64GB
```

### **1. 使用小的executors**

```properties
--num-executors = 每个核心分配一个executor
                = 集群的总核心数
                = 48 x 3 = 144
                  
--executor-cores  = 1 
 
--executor-memory = 每个节点内存总数数/每个节点上分配的executor数
                  = 64GB/48 = 1GB
```

分析：

由于每个executor只分配了一个核，将无法利用在同一个JVM中运行多个任务的优点。

此外，共享/缓存变量（如广播变量和累加器）将在节点的每个核心中复制16次。

最严重的就是，没有为Hadoop / Yarn守护程序进程留下足够的内存开销，还忘记了将ApplicationManagers运行所需要的开销加入计算。




### **2：使用较大的executors**

```properties
-num-executors = 每个节点分配一个executor
                = 集群的总节点数
                = 3
                    
--executor-cores = 每个节点所有核心都分配给一个执executor
                 = 每个节点的总核心数
                 = 48
                     
--executor-memory = 每个节点内存总数数/每个节点上分配的executor数
                  = 64GB/1 = 64GB
```

**分析：**

每个executor都有16个核心，由于HDFS客户端遇到大量并发线程会出现一些bug，即HDFS吞吐量会受到影响。

同时过大的内存分配也会导致过多的GC延迟。



### **3：使用优化的executors（推荐）**

```properties

考虑Linux运行及程序、Hadoop、Yarn等守护进程等，约占5%-10%，每台预留3核心以及4G内存。
 
 
为每个执行器分配3个核心
--executor-cores = 3
 
每个节点除去预留核心，剩下： 48-3 = 45
群集中核心的可用总数： 45 x 3 = 135
 
–-num-executors = 群集中核心的可用总数/每个executors分配3核心数
                = 135/3 
                = 45
 
每个节点的executors数目： 45/3 = 15
 
群集中每个节点的可使用的总内存数： 64GB - 4GB = 60GB
 
--executor-memory = 每个executor的内存= 60GB / 15 
                  = 4GB
 
预留的 off heap overhead = 4GB * Max(384MB, 7% of 4GB)
--executor-memory = 4 - 384M 
                  = 约4GB
```

