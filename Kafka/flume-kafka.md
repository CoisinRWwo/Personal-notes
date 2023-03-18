## 1. 编辑Kafka文件



在文件中直接添加以下配置

```properties
listeners=PLAINTEXT://0.0.0.0:9092
advertised.host.name=master
advertised.listeners=PLAINTEXT://192.168.23.69:9092
```



启动kafka

```bash
/opt/kafka/kafka_2.11-2.0.0/bin/kafka-server-start.sh -daemon /opt/kafka/kafka_2.11-2.0.0/config/server.properties
```



创建topic

```properties
bin/kafka-topics.sh --zookeeper hadoop102:2181 --create --replication-factor 1 --partitions 4 --topic first
```



启动kafka消费者

```bash
bin/kafka-console-consumer.sh  --bootstrap-server hadoop102:9092 --from-beginning --topic first
```



## 2. 配置Flume文件



在Flume下创建job文件夹，并创建允许文件

flume-netcat-logger.conf

在文件中编辑一下配置文件

```properties
a1.sources = r1
a1.sinks = k1 k2
a1.channels = c1
 
a1.sources.r1.type = netcat
a1.sources.r1.bind = localhost
a1.sources.r1.port = 26001

a1.channels.c1.type = memory

a1.sinks.k1.type = org.apache.flume.sink.kafka.KafkaSink
a1.sinks.k1.topic = order
a1.sinks.k1.brokerList = master:9092

a1.sinks.k2.type = hdfs
a1.sinks.k2.hdfs.path = hdfs://master:9000/user/test/flumebackup

a1.sources.r1.channels = c1
a1.sinks.k1.channel = c1
a1.sinks.k2.channel = c1
```

 启动flume

```bash
./bin/flume-ng agent --conf conf/ --name a1 --conf-file job/flume-netcat-logger.conf -Dflume.root.logger=INFO,console
```

