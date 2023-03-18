## 

1.0HDFS 概述

## 1.1 HDFS 产出背景及定义

![image-20210912111340239](C:\Users\Admin\OneDrive\MarkDown\Hadoop2.X\图片\image-20210912111340239.png)

---

## 1.2 HDFS 优缺点

### 1.2.1优点

![image-20210912111702475](C:\Users\Admin\OneDrive\MarkDown\Hadoop2.X\图片\image-20210912111702475.png)

---

### 1.2.2缺点

![image-20210912112020384](C:\Users\Admin\OneDrive\MarkDown\Hadoop2.X\图片\image-20210912112020384.png)

---

## 1.3 HDFS 组成架构

![image-20210912113110694](C:\Users\Admin\OneDrive\MarkDown\Hadoop2.X\图片\image-20210912113110694.png)

![image-20210912113417128](C:\Users\Admin\OneDrive\MarkDown\Hadoop2.X\图片\image-20210912113417128.png)

---

## 1.4 HDFS 文件块大小

![image-20210912113647317](C:\Users\Admin\OneDrive\MarkDown\Hadoop2.X\图片\image-20210912113647317.png)

![image-20210912114101180](C:\Users\Admin\OneDrive\MarkDown\Hadoop2.X\图片\image-20210912114101180.png)

---

## 2.1HDFS 的 Shell 操作

**1．基本语法**

bin/hadoop fs 具体命令 OR bin/hdfs dfs 具体命令 

dfs 是 fs 的实现类。

**2．命令大全**

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ bin/hadoop fs
[-appendToFile <localsrc> ... <dst>]
 [-cat [-ignoreCrc] <src> ...]
 [-checksum <src> ...]
 [-chgrp [-R] GROUP PATH...]
 [-chmod [-R] <MODE[,MODE]... | OCTALMODE> PATH...]
 [-chown [-R] [OWNER][:[GROUP]] PATH...]
 [-copyFromLocal [-f] [-p] <localsrc> ... <dst>]
 [-copyToLocal [-p] [-ignoreCrc] [-crc] <src> ... <localdst>]
 [-count [-q] <path> ...]
 [-cp [-f] [-p] <src> ... <dst>]
 [-createSnapshot <snapshotDir> [<snapshotName>]]
 [-deleteSnapshot <snapshotDir> <snapshotName>]
 [-df [-h] [<path> ...]]
 [-du [-s] [-h] <path> ...]
 [-expunge]
 [-get [-p] [-ignoreCrc] [-crc] <src> ... <localdst>]
 [-getfacl [-R] <path>]
 [-getmerge [-nl] <src> <localdst>]
 [-help [cmd ...]]
 [-ls [-d] [-h] [-R] [<path> ...]]
 [-mkdir [-p] <path> ...]
 [-moveFromLocal <localsrc> ... <dst>]
 [-moveToLocal <src> <localdst>]
  [-mv <src> ... <dst>]
 [-put [-f] [-p] <localsrc> ... <dst>]
 [-renameSnapshot <snapshotDir> <oldName> <newName>]
 [-rm [-f] [-r|-R] [-skipTrash] <src> ...]
 [-rmdir [--ignore-fail-on-non-empty] <dir> ...]
 [-setfacl [-R] [{-b|-k} {-m|-x <acl_spec>} <path>]|[--
set <acl_spec> <path>]]
 [-setrep [-R] [-w] <rep> <path> ...]
 [-stat [format] <path> ...]
 [-tail [-f] <file>]
 [-test -[defsz] <path>]
 [-text [-ignoreCrc] <src> ...]
 [-touchz <path> ...]
 [-usage [cmd ...]]
```

3．常用命令实操

（0）启动 Hadoop 集群（方便后续的测试）

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ sbin/start-dfs.sh
[atguigu@hadoop103 hadoop-2.7.2]$ sbin/start-yarn.sh
```

（1）-help：输出这个命令参数

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ hadoop fs -help rm
```

（2）-ls: 显示目录信息

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ hadoop fs -ls /
```

（3）-mkdir：在 HDFS 上创建目录

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ hadoop fs -mkdir -p /sanguo/shuguo
```

（4）-moveFromLocal：从本地剪切粘贴到 HDFS

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ touch kongming.txt
[atguigu@hadoop102 hadoop-2.7.2]$ hadoop fs -moveFromLocal ./kongming.txt /sanguo/shuguo
```

（5）-appendToFile：追加一个文件到已经存在的文件末尾

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ touch liubei.txt
[atguigu@hadoop102 hadoop-2.7.2]$ vi liubei.txt
输入
san gu mao lu
[atguigu@hadoop102 hadoop-2.7.2]$ hadoop fs -appendToFile liubei.txt /sanguo/shuguo/kongming.txt
```

（6）-cat：显示文件内容

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ hadoop fs -cat /sanguo/shuguo/kongming.txt
```

（7）-chgrp 、-chmod、-chown：Linux 文件系统中的用法一样，修改文件所属权限

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ hadoop fs -chmod 666 /sanguo/shuguo/kongming.txt
[atguigu@hadoop102 hadoop-2.7.2]$ hadoop fs -chown atguigu:atguigu /sanguo/shuguo/kongming.txt
```

（8）-copyFromLocal：从本地文件系统中拷贝文件到 HDFS 路径去

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ hadoop fs -copyFromLocal README.txt /
```

（9）-copyToLocal：从 HDFS 拷贝到本地

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ hadoop fs -copyToLocal /sanguo/shuguo/kongming.txt ./
```

（10）-cp ：从 HDFS 的一个路径拷贝到 HDFS 的另一个路径

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ hadoop fs -cp /sanguo/shuguo/kongming.txt /zhuge.txt
```

（11）-mv：在 HDFS 目录中移动文件

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ hadoop fs -mv /zhuge.txt /sanguo/shuguo/
```

（12）-get：等同于 copyToLocal，就是从 HDFS 下载文件到本地

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ hadoop fs -get /sanguo/shuguo/kongming.txt ./
```

（13）-getmerge：合并下载多个文件，比如 HDFS 的目录 /user/atguigu/test 下有多个文 件:log.1, log.2,log.3,...

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ hadoop fs -getmerge /user/atguigu/test/* ./zaiyiqi.txt
```

（14）-put：等同于 copyFromLocal

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ hadoop fs -put ./zaiyiqi.txt /user/atguigu/test/
```

（15）-tail：显示一个文件的末尾

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ hadoop fs -tail /sanguo/shuguo/kongming.txt
```

（16）-rm：删除文件或文件夹

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ hadoop fs -rm /user/atguigu/test/jinlian2.txt
```

（17）-rmdir：删除空目录

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ hadoop fs -mkdir /test
[atguigu@hadoop102 hadoop-2.7.2]$ hadoop fs -rmdir /test
```

（18）-du 统计文件夹的大小信息

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ hadoop fs -du -s -h
/user/atguigu/test
2.7 K /user/atguigu/test
[atguigu@hadoop102 hadoop-2.7.2]$ hadoop fs -du -h
/user/atguigu/test
1.3 K /user/atguigu/test/README.txt
15 /user/atguigu/test/jinlian.txt
1.4 K /user/atguigu/test/zaiyiqi.txt
```

（19）-setrep：设置 HDFS 中文件的副本数量

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ hadoop fs -setrep 10 /sanguo/shuguo/kongming.txt
```

![image-20210912134124181](C:\Users\Admin\OneDrive\MarkDown\Hadoop2.X\图片\image-20210912134124181.png)

  这里设置的副本数只是记录在 NameNode 的元数据中，是否真的会有这么多副本，还得看 DataNode 的数量。因为目前只有 3 台设备，最多也就 3 个副本，只有节点数的增加到 10 台时，副本数才能达到 10。

---

## 3.0HDFS 客户端操作

## 3.1 HDFS 客户端环境准备

1．根据自己电脑的操作系统拷贝对应的编译后的 hadoop jar 包到非中文路径（例 如：D:\Develop\hadoop-2.7.2），如图

![image-20210912140702746](C:\Users\Admin\OneDrive\MarkDown\Hadoop2.X\图片\image-20210912140702746.png)

2．配置 HADOOP_HOME 环境变量，如图

![image-20210912140730989](C:\Users\Admin\OneDrive\MarkDown\Hadoop2.X\图片\image-20210912140730989.png)

3. 配置 Path 环境变量，如图

![image-20210912140759518](C:\Users\Admin\OneDrive\MarkDown\Hadoop2.X\图片\image-20210912140759518.png)

4．创建一个 Maven 工程 HdfsClientDemo

5．导入相应的依赖坐标+日志添加

```properties
<dependencies>
<dependency>
<groupId>junit</groupId>
<artifactId>junit</artifactId>
<version>RELEASE</version>
</dependency>
<dependency>
<groupId>org.apache.logging.log4j</groupId>
<artifactId>log4j-core</artifactId>
<version>2.8.2</version>
</dependency>
<dependency>
<groupId>org.apache.hadoop</groupId>
<artifactId>hadoop-common</artifactId>
<version>2.7.2</version>
</dependency>
<dependency>
<groupId>org.apache.hadoop</groupId>
<artifactId>hadoop-client</artifactId>
<version>2.7.2</version>
</dependency>
<dependency>
<groupId>org.apache.hadoop</groupId>
<artifactId>hadoop-hdfs</artifactId>
<version>2.7.2</version>
</dependency>
<dependency>
<groupId>jdk.tools</groupId>
<artifactId>jdk.tools</artifactId>
<version>1.8</version>
<scope>system</scope>
<systemPath>${JAVA_HOME}/lib/tools.jar</systemPath>
</dependency>
</dependencies>
```

**如果Java路径报错则改为使用绝对路劲**

**注意：如果 Eclipse/Idea 打印不出日志，在控制台上只显示**

```powershell
1.log4j:WARN No appenders could be found for logger (org.apach
e.hadoop.util.Shell).
2.log4j:WARN Please initialize the log4j system properly.
3.log4j:WARN See http://logging.apache.org/log4j/1.2/faq.html#
noconfig for more info.
```

需要在项目的 src/main/resources 目录下，新建一个文件，命名为“log4j.properties”，在 文件中填入

```properties
log4j.rootLogger=INFO, stdout
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%d %p [%c] - %m%n
log4j.appender.logfile=org.apache.log4j.FileAppender
log4j.appender.logfile.File=target/spring.log
log4j.appender.logfile.layout=org.apache.log4j.PatternLayout
log4j.appender.logfile.layout.ConversionPattern=%d %p [%c] - %m%n
```

6．创建包名：com.atguigu.hdfs

7．创建 HdfsClient 类

```java
public class HDFSClient {
    public static void main(String[] args) throws IOException, URISyntaxException, Exception {

        Configuration entries = new Configuration();
        //entries.set("fs.default","hdfs://hadoop102:9000");

        //获取hdfs客户端对象
        //FileSystem fileSystem = FileSystem.get(entries);
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop102:9000"), entries, "shf");


        //在hdfs上创建路劲
        fs.mkdirs(new Path("/test/dashen/aaaaa"));

        //关闭资源
        fs.close();

        System.out.println("over");
    }
}
```

8．执行程序

运行时需要配置用户名称，如图

![image-20210912150133512](C:\Users\Admin\OneDrive\MarkDown\Hadoop2.X\图片\image-20210912150133512.png)

客户端去操作 HDFS 时，是有一个用户身份的。默认情况下，HDFS 客户端 API 会从 JVM 中获取一个参数来作为自己的用户身份：-DHADOOP_USER_NAME=atguigu，atguigu 为用户名称。

---



## 3.2 HDFS 的 API 操作

### 3.2.1 HDFS 文件上传（测试参数优先级）

```java
//文件上传
    @Test
    public void testCopyFromLocalFile() throws URISyntaxException, IOException, InterruptedException {

        Configuration entries = new Configuration();

        //获取fs对象
        FileSystem shf = FileSystem.get(new URI("hdfs://192.168.0.205:9000"), entries, "root ");

        //执行上传API
        shf.copyFromLocalFile(new Path("Data/HelloWord"),new Path("/Date/HelloWord1"));
        //关闭资源
        shf.close();
    }
}
```

2．将 hdfs-site.xml 拷贝到项目的根目录下

```properties
<?xml version="1.0" encoding="UTF-8"?>
<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
<!--
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License. See accompanying LICENSE file.
-->

<!-- Put site-specific property overrides in this file. -->

<configuration>
    <property>
        <name>dfs.namenode.name.dir</name>
        <value>file:/usr/local/src/hadoop/dfs/name</value>
    </property>
    <property>
        <name>dfs.datanode.data.dir</name>
        <value>file:/usr/local/usr/hadoop/dfs/data</value>
    </property>
    <property>
        <name>dfs.replication</name>
        <value>3</value>
    </property>
</configuration>
```

3．参数优先级 参数优先级排序：（1）客户端代码中设置的值 >（2）ClassPath 下的用户自定义配置文 件 >（3）然后是服务器的默认配置

---

### 3.2.2 HDFS 文件下载

```java
//文件下载
    @Test
    public void teetCopyToLocalFile() throws URISyntaxException, IOException, InterruptedException {
        Configuration entries = new Configuration();

        //获取对象
        FileSystem root = FileSystem.get(new URI("hdfs://192.168.0.205:9000"), entries, "root");

        //执行下载操作
        root.copyToLocalFile(new Path("/Date/HelloWord1"),new Path("F:\\SHFWorkSpace\\cn.shf.HadoopProject\\Data"));

        //boolean delSrc 指是否将原文件删除
        // Path src 指要下载的文件路径
        // Path dst 指将文件下载到的路径
        // boolean useRawLocalFileSystem 是否开启文件校验
        root.copyToLocalFile(false,new Path("/Date/HelloWord1"),new Path("F:\\SHFWorkSpace\\cn.shf.HadoopProject\\Data"),true);
        
        //关闭资源
        root.close();
    }
```

---

### 3.2.3 HDFS 文件夹删除

```java
//文件删除
    @Test
    public void testDelect() throws URISyntaxException, IOException, InterruptedException {
        Configuration entries = new Configuration();

        //获取对象
        FileSystem root = FileSystem.get(new URI("hdfs://192.168.0.205:9000"), entries, "root");

        //文件的删除
        //后者参数如果为文件目录的话，则写true，使其递归删除
        root.delete(new Path("/Date/HelloWord1"),true);

        //关闭资源
        root.close();

    }
```

---



### 3.2.4 HDFS 文件名更改

```java
public void testRename() throws IOException,
InterruptedException, URISyntaxException{
// 1 获取文件系统
Configuration configuration = new Configuration();
FileSystem fs = FileSystem.get(new URI("hdfs://hadoop102:9000"), configuration, "atguigu");
// 2 修改文件名称
fs.rename(new Path("/banzhang.txt"), new Path("/banhua.txt"));
// 3 关闭资源
fs.close();
}
```

---



### 3.2.5 HDFS 文件详情查看

```java
@Test
public void testListFiles() throws IOException,
InterruptedException, URISyntaxException{
// 1 获取文件系统
Configuration configuration = new Configuration();
FileSystem fs = FileSystem.get(new
URI("hdfs://hadoop102:9000"), configuration, "atguigu");
// 2 获取文件详情
RemoteIterator<LocatedFileStatus> listFiles =
fs.listFiles(new Path("/"), true);
while(listFiles.hasNext()){
LocatedFileStatus status = listFiles.next();
// 输出详情
// 文件名称
System.out.println(status.getPath().getName());
// 长度
System.out.println(status.getLen());
// 权限
System.out.println(status.getPermission());
// 分组
System.out.println(status.getGroup());
// 获取存储的块信息
BlockLocation[] blockLocations =
status.getBlockLocations();
for (BlockLocation blockLocation : blockLocations) {
// 获取块存储的主机节点
String[] hosts = blockLocation.getHosts();
for (String host : hosts) {
System.out.println(host);
}
}
System.out.println("-----------班长的分割线----------");
}
// 3 关闭资源
fs.close();
}
```

---

### 3.2.6 HDFS 文件和文件夹判断

```java
@Test
public void testListStatus() throws IOException,
InterruptedException, URISyntaxException{
// 1 获取文件配置信息
Configuration configuration = new Configuration();
FileSystem fs = FileSystem.get(new
URI("hdfs://hadoop102:9000"), configuration, "atguigu");
// 2 判断是文件还是文件夹
FileStatus[] listStatus = fs.listStatus(new Path("/"));
for (FileStatus fileStatus : listStatus) {
// 如果是文件
if (fileStatus.isFile()) {
System.out.println("f:"+fileStatus.getPath().getName());
}else {
System.out.println("d:"+fileStatus.getPath().getName());
}
}
// 3 关闭资源
fs.close();
}
```

---

## 3.3 HDFS 的 I/O 流操作

上面我们学的 API 操作 HDFS 系统都是框架封装好的。那么如果我们想自己实现上述 API 的操作该怎么实现呢？ 

我们可以采用 IO 流的方式实现数据的上传和下载。

### 3.3.1 HDFS 文件上传

1．需求：把本地 e 盘上的 banhua.txt 文件上传到 HDFS 根目录

2．编写代码

```java
@Test
public void putFileToHDFS() throws IOException, InterruptedException, URISyntaxException {
// 1 获取文件系统
Configuration configuration = new Configuration();
FileSystem fs = FileSystem.get(new URI("hdfs://hadoop102:9000"), configuration, "atguigu");
// 2 创建输入流
FileInputStream fis = new FileInputStream(new File("e:/banhua.txt"));
// 3 获取输出流
FSDataOutputStream fos = fs.create(new Path("/banhua.txt"));
// 4 流对拷
IOUtils.copyBytes(fis, fos, configuration);
// 5 关闭资源
IOUtils.closeStream(fos);
IOUtils.closeStream(fis);
 fs.close();
}
```

---

### 3.3.2 HDFS 文件下载

1．需求：从 HDFS 上下载 banhua.txt 文件到本地 e 盘上

2．编写代码

```java
// 文件下载
@Test
public void getFileFromHDFS() throws IOException,
InterruptedException, URISyntaxException{
// 1 获取文件系统
Configuration configuration = new Configuration();
FileSystem fs = FileSystem.get(new URI("hdfs://hadoop102:9000"), configuration, "atguigu");
// 2 获取输入流
FSDataInputStream fis = fs.open(new Path("/banhua.txt"));
// 3 获取输出流
FileOutputStream fos = new FileOutputStream(new File("e:/banhua.txt"));
// 4 流的对拷
IOUtils.copyBytes(fis, fos, configuration);
// 5 关闭资源
IOUtils.closeStream(fos);
IOUtils.closeStream(fis);
fs.close();
}
```

---

### 3.3.3 定位文件读取

1．需求：分块读取 HDFS 上的大文件，比如根目录下的/hadoop-2.7.2.tar.gz

2．编写代码

（1）下载第一块

```java
@Test
public void readFileSeek1() throws IOException,
InterruptedException, URISyntaxException{
// 1 获取文件系统
Configuration configuration = new Configuration();
FileSystem fs = FileSystem.get(new
URI("hdfs://hadoop102:9000"), configuration, "atguigu");
// 2 获取输入流
FSDataInputStream fis = fs.open(new Path("/hadoop2.7.2.tar.gz"));
// 3 创建输出流
FileOutputStream fos = new FileOutputStream(new
File("e:/hadoop-2.7.2.tar.gz.part1"));
// 4 流的拷贝
byte[] buf = new byte[1024];
for(int i =0 ; i < 1024 * 128; i++){
fis.read(buf);
fos.write(buf);
}
// 5 关闭资源
IOUtils.closeStream(fis);
IOUtils.closeStream(fos);
fs.close();
}
```

（2）下载第二块

```java
@Test
public void readFileSeek2() throws IOException,
InterruptedException, URISyntaxException{
// 1 获取文件系统
Configuration configuration = new Configuration();
FileSystem fs = FileSystem.get(new
URI("hdfs://hadoop102:9000"), configuration, "atguigu");
// 2 打开输入流
FSDataInputStream fis = fs.open(new Path("/hadoop2.7.2.tar.gz"));
// 3 定位输入数据位置
fis.seek(1024*1024*128);
// 4 创建输出流
FileOutputStream fos = new FileOutputStream(new
File("e:/hadoop-2.7.2.tar.gz.part2"));
// 5 流的对拷
IOUtils.copyBytes(fis, fos, configuration);
// 6 关闭资源
IOUtils.closeStream(fis);
IOUtils.closeStream(fos);
}
```

（3）合并文件 

在 Window 命令窗口中进入到目录 E:\，然后执行如下命令，对数据进行合并 type hadoop-2.7.2.tar.gz.part2 >> hadoop-2.7.2.tar.gz.part1 合并完成后，将 hadoop-2.7.2.tar.gz.part1 重新命名为 hadoop-2.7.2.tar.gz。解压发现该 tar 包非常完整。

---

## 4.1 HDFS 写数据流程

### 4.1.1 剖析文件写入

![image-20210912165349307](C:\Users\Admin\OneDrive\MarkDown\Hadoop2.X\图片\image-20210912165349307.png)

1）客户端通过 Distributed FileSystem 模块向 NameNode 请求上传文件，NameNode 检查目 标文件是否已存在，父目录是否存在。

2）NameNode 返回是否可以上传

3）客户端请求第一个 Block 上传到哪几个 DataNode 服务器上。 

4）NameNode 返回 3 个 DataNode 节点，分别为 dn1、dn2、dn3。 

5）客户端通过 FSDataOutputStream 模块请求 dn1 上传数据，dn1 收到请求会继续调用 dn2， 然后 dn2 调用 dn3，将这个通信管道建立完成。 

6）dn1、dn2、dn3 逐级应答客户端。 

7）客户端开始往 dn1 上传第一个 Block（先从磁盘读取数据放到一个本地内存缓存），以 Packet 为单位，dn1 收到一个 Packet 就会传给 dn2，dn2 传给 dn3；dn1 每传一个 packet 会放 入一个应答队列等待应答。 

8）当一个 Block 传输完成之后，客户端再次请求 NameNode 上传第二个 Block 的服务器。 （重复执行 3-7 步）。

---

### 4.1.2 网络拓扑-节点距离计算

在 HDFS 写数据的过程中，NameNode 会选择距离待上传数据最近距离的 DataNode 接 收数据。那么这个最近距离怎么计算呢？ 

**节点距离：两个节点到达最近的共同祖先的距离总和。**

![image-20210912170232362](C:\Users\Admin\OneDrive\MarkDown\Hadoop2.X\图片\image-20210912170232362.png)

例如，假设有数据中心 d1 机架 r1 中的节点 n1。该节点可以表示为/d1/r1/n1。利用这种 标记，这里给出四种距离描述，如图

![image-20210912183828821](C:\Users\Admin\OneDrive\MarkDown\Hadoop2.X\图片\image-20210912183828821.png)

---

### 4.1.3 机架感知（副本存储节点选择）

1. 官方 ip 地址

机架感知说明 

http://hadoop.apache.org/docs/r2.7.2/hadoop-project-dist/hadoophdfs/HdfsDesign.html#Data_Replication

```shell
For the common case, when the replication factor is three, HDFS’s
placement policy is to put one replica on one node in the local
rack, another on a different node in the local rack, and the
last on a different node in a different rack.
```

2. Hadoop2.7.2 副本节点选择

![image-20210912184510737](C:\Users\Admin\OneDrive\MarkDown\Hadoop2.X\图片\image-20210912184510737.png)

---

## 4.2 HDFS 读数据流程

HDFS 的读数据流程，如图

![image-20210912184616008](C:\Users\Admin\OneDrive\MarkDown\Hadoop2.X\图片\image-20210912184616008.png)

1）客户端通过 Distributed FileSystem 向 NameNode 请求下载文件，NameNode 通过查询元 数据，找到文件块所在的 DataNode 地址。 

2）挑选一台 DataNode（就近原则，然后随机）服务器，请求读取数据。 

3）DataNode 开始传输数据给客户端（从磁盘里面读取数据输入流，以 Packet 为单位来做校 验）。 

4）客户端以 Packet 为单位接收，先在本地缓存，然后写入目标文件。

---

## 5.1 NN 和 2NN 工作机制

思考：NameNode 中的元数据是存储在哪里的？ 

首先，我们做个假设，如果存储在 NameNode 节点的磁盘中，因为经常需要进行随机访 问，还有响应客户请求，必然是效率过低。因此，元数据需要存放在内存中。但如果只存在 内存中，一旦断电，元数据丢失，整个集群就无法工作了。**因此产生在磁盘中备份元数据的 FsImage。**

这样又会带来新的问题，当在内存中的元数据更新时，如果同时更新 FsImage，就会导 致效率过低，但如果不更新，就会发生一致性问题，一旦 NameNode 节点断电，就会产生数据丢失。**因此，引入 Edits 文件(只进行追加操作，效率很高)。每当元数据有更新或者添加元 数据时，修改内存中的元数据并追加到 Edits 中。**这样，一旦 NameNode 节点断电，可以通 过 FsImage 和 Edits 的合并，合成元数据。

但是，如果长时间添加数据到 Edits 中，会导致该文件数据过大，效率降低，而且一旦 断电，恢复元数据需要的时间过长。因此，需要定期进行 FsImage 和 Edits 的合并，如果这 个操作由NameNode节点完成，又会效率过低。**因此，引入一个新的节点SecondaryNamenode， 专门用于 FsImage 和 Edits 的合并。**

NN 和 2NN 工作机制

![image-20210912185921252](C:\Users\Admin\OneDrive\MarkDown\Hadoop2.X\图片\image-20210912185921252.png)

1. 第一阶段：NameNode 启动

（1）第一次启动 NameNode 格式化后，创建 Fsimage 和 Edits 文件。如果不是第一次启 动，直接加载编辑日志和镜像文件到内存。 

（2）客户端对元数据进行增删改的请求。 

（3）NameNode 记录操作日志，更新滚动日志。

（4）NameNode 在内存中对数据进行增删改。

2. 第二阶段：Secondary NameNode 工作

（1）Secondary NameNode 询问 NameNode 是否需要 CheckPoint。直接带回 NameNode 是否检查结果。 

（2）Secondary NameNode 请求执行 CheckPoint。

（3）NameNode 滚动正在写的 Edits 日志。 

（4）将滚动前的编辑日志和镜像文件拷贝到 Secondary NameNode。 

（5）Secondary NameNode 加载编辑日志和镜像文件到内存，并合并。 

（6）生成新的镜像文件 fsimage.chkpoint。 

（7）拷贝 fsimage.chkpoint 到 NameNode。 

（8）NameNode 将 fsimage.chkpoint 重新命名成 fsimage。

**NN 和 2NN 工作机制详解：**

```
Fsimage：NameNode 内存中元数据序列化后形成的文件。
Edits：记录客户端更新元数据信息的每一步操作（可通过 Edits 运算出元数据）。
NameNode启动时，先滚动Edits并生成一个空的edits.inprogress，然后加载Edits和Fsimage
到内存中，此时 NameNode 内存就持有最新的元数据信息。Client 开始对 NameNode 发送
元数据的增删改的请求，这些请求的操作首先会被记录到 edits.inprogress 中（查询元数据
的操作不会被记录在 Edits 中，因为查询操作不会更改元数据信息），如果此时 NameNode
挂掉，重启后会从 Edits 中读取元数据的信息。然后，NameNode 会在内存中执行元数据
的增删改的操作。
由于 Edits 中记录的操作会越来越多，Edits 文件会越来越大，导致 NameNode 在启动加载
Edits 时会很慢，所以需要对 Edits 和 Fsimage 进行合并（所谓合并，就是将 Edits 和 Fsimage
加载到内存中，照着 Edits 中的操作一步步执行，最终形成新的 Fsimage）。
SecondaryNameNode 的作用就是帮助 NameNode 进行 Edits 和 Fsimage 的合并工作。
SecondaryNameNode 首先会询问 NameNode 是否需要 CheckPoint（触发 CheckPoint 需要
满足两个条件中的任意一个，定时时间到和 Edits 中数据写满了）。直接带回 NameNode
是否检查结果。SecondaryNameNode 执行 CheckPoint 操作，首先会让 NameNode 滚动 Edits
并生成一个空的 edits.inprogress，滚动 Edits 的目的是给 Edits 打个标记，以后所有新的操
作都写入 edits.inprogress，其他未合并的 Edits 和 Fsimage 会拷贝到 SecondaryNameNode
的本地，然后将拷贝的 Edits 和 Fsimage 加载到内存中进行合并，生成 fsimage.chkpoint，
然后将 fsimage.chkpoint 拷贝给 NameNode，重命名为 Fsimage 后替换掉原来的 Fsimage。
NameNode 在启动时就只需要加载之前未合并的 Edits 和 Fsimage 即可，因为合并过的
Edits 中的元数据信息已经被记录在 Fsimage 中。
```

---



## 5.2 Fsimage 和 Edits 解析

1. 概念

![image-20210912191448265](C:\Users\Admin\OneDrive\MarkDown\Hadoop2.X\图片\image-20210912191448265.png)

2. oiv 查看 Fsimage 文件

（1）查看 oiv 和 oev 命令

```bash
[atguigu@hadoop102 current]$ hdfs
oiv apply the offline fsimage viewer to an fsimage
oev apply the offline edits viewer to an edits file
```

（2）基本语法

hdfs oiv -p 文件类型 -i 镜像文件 -o 转换后文件输出路径

（3）案例实操

```bash
[atguigu@hadoop102 current]$ pwd
/opt/module/hadoop-2.7.2/data/tmp/dfs/name/current
[atguigu@hadoop102 current]$ hdfs oiv -p XML -i
fsimage_0000000000000000025 -o /opt/module/hadoop2.7.2/fsimage.xml
[atguigu@hadoop102 current]$ cat /opt/module/hadoop2.7.2/fsimage.xml
```

将显示的 xml 文件内容拷贝到 Eclipse 中创建的 xml 文件中，并格式化。部分显示 结果如下。

```properties
<inode>
<id>16386</id>
<type>DIRECTORY</type>
<name>user</name>
<mtime>1512722284477</mtime>
<permission>atguigu:supergroup:rwxr-xr-x</permission>
<nsquota>-1</nsquota>
<dsquota>-1</dsquota>
</inode>
<inode>
<id>16387</id>
<type>DIRECTORY</type>
<name>atguigu</name>
<mtime>1512790549080</mtime>
<permission>atguigu:supergroup:rwxr-xr-x</permission>
<nsquota>-1</nsquota>
<dsquota>-1</dsquota>
</inode>
<inode>
<id>16389</id>
<type>FILE</type>
<name>wc.input</name>
<replication>3</replication>
<mtime>1512722322219</mtime>
<atime>1512722321610</atime>
<perferredBlockSize>134217728</perferredBlockSize>
<permission>atguigu:supergroup:rw-r--r--</permission>
<blocks>
<block>
<id>1073741825</id>
<genstamp>1001</genstamp>
<numBytes>59</numBytes>
</block>
</blocks>
</inode >
```

思考：可以看出，Fsimage 中没有记录块所对应 DataNode，为什么？

在集群启动后，要求 DataNode 上报数据块信息，并间隔一段时间后再次上报。

3. oev 查看 Edits 文件

   （1）基本语法

hdfs oev -p 文件类型 -i 编辑日志 -o 转换后文件输出路径

（2）案例实操

```bash
[atguigu@hadoop102 current]$ hdfs oev -p XML -i
edits_0000000000000000012-0000000000000000013 -o
/opt/module/hadoop-2.7.2/edits.xml
[atguigu@hadoop102 current]$ cat /opt/module/hadoop2.7.2/edits.xml
```

将显示的 xml 文件内容拷贝到 Eclipse 中创建的 xml 文件中，并格式化。显示结果 如下。

```properties
<?xml version="1.0" encoding="UTF-8"?>
<EDITS>
<EDITS_VERSION>-63</EDITS_VERSION>
<RECORD>
<OPCODE>OP_START_LOG_SEGMENT</OPCODE>
<DATA>
<TXID>129</TXID>
</DATA>
</RECORD>
<RECORD>
<OPCODE>OP_ADD</OPCODE>
<DATA>
<TXID>130</TXID>
<LENGTH>0</LENGTH>
<INODEID>16407</INODEID>
<PATH>/hello7.txt</PATH>
<REPLICATION>2</REPLICATION>
<MTIME>1512943607866</MTIME>
<ATIME>1512943607866</ATIME>
<BLOCKSIZE>134217728</BLOCKSIZE>
<CLIENT_NAME>DFSClient_NONMAPREDUCE_-
1544295051_1</CLIENT_NAME>
<CLIENT_MACHINE>192.168.1.5</CLIENT_MACHINE>
<OVERWRITE>true</OVERWRITE>
<PERMISSION_STATUS>
<USERNAME>atguigu</USERNAME>
<GROUPNAME>supergroup</GROUPNAME>
<MODE>420</MODE>
</PERMISSION_STATUS>
<RPC_CLIENTID>908eafd4-9aec-4288-96f1-
e8011d181561</RPC_CLIENTID>
<RPC_CALLID>0</RPC_CALLID>
</DATA>
</RECORD>
<RECORD>
<OPCODE>OP_ALLOCATE_BLOCK_ID</OPCODE>
<DATA>
<TXID>131</TXID>
<BLOCK_ID>1073741839</BLOCK_ID>
</DATA>
</RECORD>
<RECORD>
<OPCODE>OP_SET_GENSTAMP_V2</OPCODE>
<DATA>
<TXID>132</TXID>
<GENSTAMPV2>1016</GENSTAMPV2>
</DATA>
</RECORD>
<RECORD>
<OPCODE>OP_ADD_BLOCK</OPCODE>
<DATA>
<TXID>133</TXID>
<PATH>/hello7.txt</PATH>
<BLOCK>
<BLOCK_ID>1073741839</BLOCK_ID>
<NUM_BYTES>0</NUM_BYTES>
<GENSTAMP>1016</GENSTAMP>
</BLOCK>
<RPC_CLIENTID></RPC_CLIENTID>
<RPC_CALLID>-2</RPC_CALLID>
</DATA>
</RECORD>
<RECORD>
<OPCODE>OP_CLOSE</OPCODE>
<DATA>
<TXID>134</TXID>
<LENGTH>0</LENGTH>
<INODEID>0</INODEID>
<PATH>/hello7.txt</PATH>
<REPLICATION>2</REPLICATION>
<MTIME>1512943608761</MTIME>
<ATIME>1512943607866</ATIME>
<BLOCKSIZE>134217728</BLOCKSIZE>
<CLIENT_NAME></CLIENT_NAME>
<CLIENT_MACHINE></CLIENT_MACHINE>
<OVERWRITE>false</OVERWRITE>
<BLOCK>
<BLOCK_ID>1073741839</BLOCK_ID>
<NUM_BYTES>25</NUM_BYTES>
<GENSTAMP>1016</GENSTAMP>
</BLOCK>
<PERMISSION_STATUS>
<USERNAME>atguigu</USERNAME>
<GROUPNAME>supergroup</GROUPNAME>
<MODE>420</MODE>
</PERMISSION_STATUS>
</DATA>
</RECORD>
</EDITS >
```

---



## 5.3 CheckPoint 时间设置

（1）通常情况下，SecondaryNameNode 每隔一小时执行一次。

[hdfs-default.xml]

```properties
<property>
 <name>dfs.namenode.checkpoint.period</name>
 <value>3600</value>
</property>
```

（2）一分钟检查一次操作次数，3 当操作次数达到 1 百万时，SecondaryNameNode 执 行一次。

```properties
<property>
 <name>dfs.namenode.checkpoint.txns</name>
 <value>1000000</value>
<description>操作动作次数</description>
</property>
<property>
 <name>dfs.namenode.checkpoint.check.period</name>
 <value>60</value>
<description> 1 分钟检查一次操作次数</description>
</property >
```

---

## 5.4 NameNode 故障处理

NameNode 故障后，可以采用如下两种方法恢复数据

方法一：将 SecondaryNameNode 中数据拷贝到 NameNode 存储数据的目录；

1. kill -9 NameNode 进程

2. 删除 NameNode 存储的数据（/opt/module/hadoop-2.7.2/data/tmp/dfs/name）

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ rm -rf /opt/module/hadoop2.7.2/data/tmp/dfs/name/*
```

3. 拷贝 SecondaryNameNode 中数据到原 NameNode 存储数据目录

```bash
[atguigu@hadoop102 dfs]$ scp -r atguigu@hadoop104:/opt/module/hadoop2.7.2/data/tmp/dfs/namesecondary/* ./name/
```

4. 重新启动 NameNode

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ sbin/hadoop-daemon.sh start namenode
```

方 法 二 ： 使 用 -importCheckpoint 选 项 启 动 NameNode 守 护 进 程 ， 从 而 将 SecondaryNameNode 中数据拷贝到 NameNode 目录中。

1. 修改 hdfs-site.xml 中的

```properties
<property>
 <name>dfs.namenode.checkpoint.period</name>
 <value>120</value>
</property>
<property>
 <name>dfs.namenode.name.dir</name>
 <value>/opt/module/hadoop-2.7.2/data/tmp/dfs/name</value>
</property>
```

2. kill -9 NameNode 进程

3. 删除 NameNode 存储的数据（/opt/module/hadoop-2.7.2/data/tmp/dfs/name）

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ rm -rf /opt/module/hadoop2.7.2/data/tmp/dfs/name/*
```

4. 如果 SecondaryNameNode 不和 NameNode 在一个主机节点上，需要将 SecondaryNameNode 存储数据的目录拷贝到 NameNode 存储数据的平级目录，并删 除 in_use.lock 文件 [atguigu@had

```bash
[atguigu@hadoop102 dfs]$ scp -r
atguigu@hadoop104:/opt/module/hadoop2.7.2/data/tmp/dfs/namesecondary ./
[atguigu@hadoop102 namesecondary]$ rm -rf in_use.lock
[atguigu@hadoop102 dfs]$ pwd
/opt/module/hadoop-2.7.2/data/tmp/dfs
[atguigu@hadoop102 dfs]$ ls
data name namesecondary
```

5. 导入检查点数据（等待一会 ctrl+c 结束掉）

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ bin/hdfs namenode -importCheckpoint
```

6. 启动 NameNode

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ sbin/hadoop-daemon.sh start namenode
```

---

## 5.5 集群安全模式

1. 概述

   ![image-20210912194945493](C:\Users\Admin\OneDrive\MarkDown\Hadoop2.X\图片\image-20210912194945493.png)

2. 基本语法

集群处于安全模式，不能执行重要操作（写操作）。集群启动完成后，自动退出安全模式。

（1）bin/hdfs dfsadmin -safemode get （功能描述：查看安全模式状态） 

（2）bin/hdfs dfsadmin -safemode enter （功能描述：进入安全模式状态） 

（3）bin/hdfs dfsadmin -safemode leave （功能描述：离开安全模式状态） 

（4）bin/hdfs dfsadmin -safemode wait （功能描述：等待安全模式状态）

3. 案例

模拟等待安全模式

（1）查看当前模式

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ hdfs dfsadmin -safemode get Safe mode is OFF
```

（2）先进入安全模式

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ bin/hdfs dfsadmin -safemode enter
```

（3）创建并执行下面的脚本

```shell
[atguigu@hadoop102 hadoop-2.7.2]$ touch safemode.sh
[atguigu@hadoop102 hadoop-2.7.2]$ vim safemode.sh
#!/bin/bash
hdfs dfsadmin -safemode wait
hdfs dfs -put /opt/module/hadoop-2.7.2/README.txt /
[atguigu@hadoop102 hadoop-2.7.2]$ chmod 777 safemode.sh
[atguigu@hadoop102 hadoop-2.7.2]$ ./safemode.sh 
```

（4）再打开一个窗口，执行

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ bin/hdfs dfsadmin -safemode leave
```

（5）观察 

（a）再观察上一个窗口 Safe mode is OFF 

（b）HDFS 集群上已经有上传的数据了。

## 5.6 NameNode 多目录配置

1. NameNode 的本地目录可以配置成多个，且每个目录存放内容相同，增加了可 靠性

2. 具体配置如下

（1）在 hdfs-site.xml 文件中增加如下内容

```properties
<property>
 <name>dfs.namenode.name.dir</name>
<value>file:///${hadoop.tmp.dir}/dfs/name1,file:///${hadoop.t
mp.dir}/dfs/name2</value>
</property>
```

（2）停止集群，删除 data 和 logs 中所有数据

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ rm -rf data/ logs/
[atguigu@hadoop103 hadoop-2.7.2]$ rm -rf data/ logs/
[atguigu@hadoop104 hadoop-2.7.2]$ rm -rf data/ logs/
```

（3）格式化集群并启动。

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ bin/hdfs namenode –format
[atguigu@hadoop102 hadoop-2.7.2]$ sbin/start-dfs.sh
```

（4）查看结果

```bash
[atguigu@hadoop102 dfs]$ ll
总用量 12
drwx------. 3 atguigu atguigu 4096 12 月 11 08:03 data
drwxrwxr-x. 3 atguigu atguigu 4096 12 月 11 08:03 name1
drwxrwxr-x. 3 atguigu atguigu 4096 12 月 11 08:03 name2
```

---

## 6.1 DataNode 工作机制

![image-20210913101550314](C:\Users\Admin\OneDrive\MarkDown\Hadoop2.X\图片\image-20210913101550314.png)

1）一个数据块在 DataNode 上以文件形式存储在磁盘上，包括两个文件，一个是数据本 身，一个是元数据包括数据块的长度，块数据的校验和，以及时间戳。 

2）DataNode 启动后向 NameNode 注册，通过后，周期性（1 小时）的向 NameNode 上 报所有的块信息。 

3）心跳是每 3 秒一次，心跳返回结果带有 NameNode 给该 DataNode 的命令如复制块数 据到另一台机器，或删除某个数据块。如果超过 10 分钟没有收到某个 DataNode 的心跳，则 认为该节点不可用。 

4）集群运行中可以安全加入和退出一些机器。

---

## 6.2 数据完整性

思考：如果电脑磁盘里面存储的数据是控制高铁信号灯的红灯信号（1）和绿灯信号（0）， 但是存储该数据的磁盘坏了，一直显示是绿灯，是否很危险？同理 DataNode 节点上的数据 损坏了，却没有发现，是否也很危险，那么如何解决呢？

如下是 DataNode 节点保证数据完整性的方法。

1）当 DataNode 读取 Block 的时候，它会计算 CheckSum。 

2）如果计算后的 CheckSum，与 Block 创建时值不一样，说明 Block 已经损坏。 

3）Client 读取其他 DataNode 上的 Block。 

4）DataNode 在其文件创建后周期验证 CheckSum，如图 

![image-20210913102225774](C:\Users\Admin\OneDrive\MarkDown\Hadoop2.X\图片\image-20210913102225774.png)

---

## 6.3 掉线时限参数设置

![image-20210913102010902](C:\Users\Admin\OneDrive\MarkDown\Hadoop2.X\图片\image-20210913102010902.png)

需要注意的是 hdfs-site.xml 配置文件中的 heartbeat.recheck.interval 的单位为毫秒， dfs.heartbeat.interval 的单位为秒。

```properties
<property>
 <name>dfs.namenode.heartbeat.recheck-interval</name>
 <value>300000</value>
</property>
<property>
 <name>dfs.heartbeat.interval</name>
 <value>3</value>
</property>
```

---

## 6.4 服役新数据节点

0. 需求 随着公司业务的增长，数据量越来越大，原有的数据节点的容量已经不能满足存储数据 的需求，需要在原有集群基础上动态添加新的数据节点。

1. 环境准备

（1）在 hadoop104 主机上再克隆一台 hadoop105 主机 

（2）修改 IP 地址和主机名称 

（3）删除原来 HDFS 文件系统留存的文件（/opt/module/hadoop-2.7.2/data 和 log） 

（4）source 一下配置文件

```bash
[atguigu@hadoop105 hadoop-2.7.2]$ source /etc/profile
```

2. 服役新节点具体步骤

（1）直接启动 DataNode，即可关联到集群

```bash
[atguigu@hadoop105 hadoop-2.7.2]$ sbin/hadoop-daemon.sh start
datanode
[atguigu@hadoop105 hadoop-2.7.2]$ sbin/yarn-daemon.sh start
nodemanager
```

![image-20210913102716949](C:\Users\Admin\OneDrive\MarkDown\Hadoop2.X\图片\image-20210913102716949.png)

（2）在 hadoop105 上上传文件

```bash
[atguigu@hadoop105 hadoop-2.7.2]$ hadoop fs -put /opt/module/hadoop-2.7.2/LICENSE.txt /
```

（3）如果数据不均衡，可以用命令实现集群的再平衡

```bash
[atguigu@hadoop102 sbin]$ ./start-balancer.sh
starting balancer, logging to /opt/module/hadoop2.7.2/logs/hadoop-atguigu-balancer-hadoop102.out
Time Stamp Iteration# Bytes Already Moved Bytes
Left To Move Bytes Being Moved
```

---

## 6.5 退役旧数据节点

### 6.5.1 添加白名单

添加到白名单的主机节点，都允许访问 NameNode，不在白名单的主机节点，都会被退 出。

配置白名单的具体步骤如下：

（1）在 NameNode 的/opt/module/hadoop-2.7.2/etc/hadoop 目录下创建 dfs.hosts 文件

```bash
[atguigu@hadoop102 hadoop]$ pwd
/opt/module/hadoop-2.7.2/etc/hadoop
[atguigu@hadoop102 hadoop]$ touch dfs.hosts
[atguigu@hadoop102 hadoop]$ vi dfs.hosts
```

添加如下主机名称**（不添加 hadoop105）**

```properties
hadoop102
hadoop103
hadoop104
```

（2）在 NameNode 的 hdfs-site.xml 配置文件中增加 dfs.hosts 属性

```properties
<property>
<name>dfs.hosts</name>
<value>/opt/module/hadoop-2.7.2/etc/hadoop/dfs.hosts</value>
</property>
```

（3）配置文件分发

```bash
[atguigu@hadoop102 hadoop]$ xsync hdfs-site.xml
```

（4）刷新 NameNode

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ hdfs dfsadmin -refreshNodes
```

（5）更新 ResourceManager 节点

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ yarn rmadmin -refreshNodes
17/06/24 14:17:11 INFO client.RMProxy: Connecting to
ResourceManager at hadoop103/192.168.1.103:8033
```

（6）在 web 浏览器上查看

![image-20210913103714443](C:\Users\Admin\OneDrive\MarkDown\Hadoop2.X\图片\image-20210913103714443.png)

4. 如果数据不均衡，可以用命令实现集群的再平衡

```bash
[atguigu@hadoop102 sbin]$ ./start-balancer.sh
starting balancer, logging to /opt/module/hadoop2.7.2/logs/hadoop-atguigu-balancer-hadoop102.out
Time Stamp Iteration# Bytes Already Moved Bytes
Left To Move Bytes Being Moved
```

---

### 6.5.2 黑名单退役

在黑名单上面的主机都会被强制退出。

1. 在 NameNode 的 /opt/module/hadoop-2.7.2/etc/hadoop 目 录 下 创 建 dfs.hosts.exclude 文件

```bash
[atguigu@hadoop102 hadoop]$ pwd
/opt/module/hadoop-2.7.2/etc/hadoop
[atguigu@hadoop102 hadoop]$ touch dfs.hosts.exclude
[atguigu@hadoop102 hadoop]$ vi dfs.hosts.exclude
```

添加如下主机名称（要退役的节点）

```properties
hadoop105
```

2．在 NameNode 的 hdfs-site.xml 配置文件中增加 dfs.hosts.exclude 属性

```properties
<property>
<name>dfs.hosts.exclude</name>
 <value>/opt/module/hadoop2.7.2/etc/hadoop/dfs.hosts.exclude</value>
</property>
```

3．刷新 NameNode、刷新 ResourceManager

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ hdfs dfsadmin -refreshNodes
Refresh nodes successful
[atguigu@hadoop102 hadoop-2.7.2]$ yarn rmadmin -refreshNodes
17/06/24 14:55:56 INFO client.RMProxy: Connecting to
ResourceManager at hadoop103/192.168.1.103:8033
```

4. 检查 Web 浏览器，退役节点的状态为 decommission in progress（退役中）， 说明数据节点正在复制块到其他节点，如图

![image-20210913104541827](C:\Users\Admin\OneDrive\MarkDown\Hadoop2.X\图片\image-20210913104541827.png)

5. 等待退役节点状态为 decommissioned（所有块已经复制完成），停止该节点及 节点资源管理器。注意：如果副本数是 3，服役的节点小于等于 3，是不能退役 成功的，需要修改副本数后才能退役，如图

![image-20210913104616481](C:\Users\Admin\OneDrive\MarkDown\Hadoop2.X\图片\image-20210913104616481.png)

```bash
[atguigu@hadoop105 hadoop-2.7.2]$ sbin/hadoop-daemon.sh stop datanode 
stopping datanode
[atguigu@hadoop105 hadoop-2.7.2]$ sbin/yarn-daemon.sh stop nodemanager
stopping nodemanager
```

6. 如果数据不均衡，可以用命令实现集群的再平衡

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ sbin/start-balancer.sh
starting balancer, logging to /opt/module/hadoop2.7.2/logs/hadoop-atguigu-balancer-hadoop102.out
Time Stamp Iteration# Bytes Already Moved
Bytes Left To Move Bytes Being Moved
```

**注意：不允许白名单和黑名单中同时出现同一个主机名称。**

---

## 6.6 Datanode 多目录配置

1. DataNode 也可以配置成多个目录，每个目录存储的数据不一样。即：数据不 是副本

2．具体配置如下

hdfs-site.xml

```properties
<property>
 <name>dfs.datanode.data.dir</name>
<value>file:///${hadoop.tmp.dir}/dfs/data1,file:///${hadoop.tm
p.dir}/dfs/data2</value>
</property>
```

---

## 7.1 集群间数据拷贝

1．scp 实现两个远程主机之间的文件复制

scp -r hello.txt root@hadoop103:/user/atguigu/hello.txt // 推 push

scp -r root@hadoop103:/user/atguigu/hello.txt hello.txt // 拉 pull

scp -r root@hadoop103:/user/atguigu/hello.txt root@hadoop104:/user/atguigu //是通过本 地主机中转实现两个远程主机的文件复制；如果在两个远程主机之间 ssh 没有配置的情况下 可以使用该方式。

2．采用 distcp 命令实现两个 Hadoop 集群之间的递归数据复制

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ bin/hadoop distcp
hdfs://haoop102:9000/user/atguigu/hello.txt
hdfs://hadoop103:9000/user/atguigu/hello.txt
```

---

## 7.2 小文件存档

![image-20210913110215001](C:\Users\Admin\OneDrive\MarkDown\Hadoop2.X\图片\image-20210913110215001.png)

3．案例实操

（1）需要启动 YARN 进程

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ start-yarn.sh
```

（2）归档文件

把/user/atguigu/input 目录里面的所有文件归档成一个叫 input.har 的归档文件，并把 归档后文件存储到/user/atguigu/output 路径下。

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ bin/hadoop archive -archiveName input.har –p /user/atguigu/input
/user/atguigu/output
```

（3）查看归档

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ hadoop fs -lsr /user/atguigu/output/input.har
[atguigu@hadoop102 hadoop-2.7.2]$ hadoop fs -lsr har:///user/atguigu/output/input.har
```

（4）解归档文件

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ hadoop fs -cp har:///user/atguigu/output/input.har/* /user/atguigu
```

---

## 7.3 回收站

开启回收站功能，可以将删除的文件在不超时的情况下，恢复原数据，起到防止误删除、 备份等作用。

1．回收站参数设置及工作机制

![image-20210913125358001](C:\Users\Admin\OneDrive\MarkDown\Hadoop2.X\图片\image-20210913125358001.png)

2．启用回收站

修改 core-site.xml，配置垃圾回收时间为 1 分钟。

```properties
<property>
 <name>fs.trash.interval</name>
<value>1</value>
</property>
```

3．查看回收站

回收站在集群中的路径：/user/atguigu/.Trash/….

4．修改访问垃圾回收站用户名称

进入垃圾回收站用户名称，默认是 dr.who，修改为 atguigu 用户

[core-site.xml]

```properties
<property>
 <name>hadoop.http.staticuser.user</name>
 <value>atguigu</value>
</property>
```

5. 通过程序删除的文件不会经过回收站，需要调用 moveToTrash()才进入回收站

Trash trash = New Trash(conf); 

trash.moveToTrash(path);

6. 恢复回收站数据

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ hadoop fs -mv /user/atguigu/.Trash/Current/user/atguigu/input
/user/atguigu/input
```

7. 清空回收站 

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ hadoop fs -expunge
```

---

## 7.4 快照管理

快照相当于对目录做一个备份。并不会立即复制所有文件，而是记录文件变化。

（1）hdfs dfsadmin -allowSnapshot 路径 （功能描述：开启指定目录的快照功能） 

（2）hdfs dfsadmin -disallowSnapshot 路径 （功能描述：禁用指定目录的快照功能，默认是禁用） 

（3）hdfs dfs -createSnapshot 路径 （功能描述：对目录创建快照） 

（4）hdfs dfs -createSnapshot 路径 名称 （功能描述：指定名称创建快照） 

（5）hdfs dfs -renameSnapshot 路径 旧名称 新名称 （功能描述：重命名快照） 

（6）hdfs lsSnapshottableDir （功能描述：列出当前用户所有可快照目录） 

（7）hdfs snapshotDiff 路径1 路径2 （功能描述：比较两个快照目录的不同之处） 

（8）hdfs dfs -deleteSnapshot   （功能描述：删除快照）

2．案例实操

（1）开启/禁用指定目录的快照功能

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ hdfs dfsadmin -allowSnapshot /user/atguigu/input
[atguigu@hadoop102 hadoop-2.7.2]$ hdfs dfsadmin -disallowSnapshot /user/atguigu/input
```

（2）对目录创建快照

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ hdfs dfs -createSnapshot /user/atguigu/input
```

通过 web 访问 hdfs://hadoop102:50070/user/atguigu/input/.snapshot/s…..// 快照和源文 件使用相同数据

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ hdfs dfs -lsr /user/atguigu/input/.snapshot/
```

（3）指定名称创建快照

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ hdfs dfs -createSnapshot /user/atguigu/input miao170508
```

（4）重命名快照

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ hdfs dfs -renameSnapshot /user/atguigu/input/ miao170508 atguigu170508
```

（5）列出当前用户所有可快照目录

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ hdfs lsSnapshottableDir
```

（6）比较两个快照目录的不同之处

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ hdfs snapshotDiff /user/atguigu/input/ . .snapshot/atguigu170508
```

（7）恢复快照

```bash
[atguigu@hadoop102 hadoop-2.7.2]$ hdfs dfs -cp /user/atguigu/input/.snapshot/s20170708-134303.027 /user
```

---

