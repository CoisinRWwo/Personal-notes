在Hadoop上查询数据统计行数是需带上字段

```shell
[root@master ~]# echo -n "文件数据记录数为：" &&  hadoop fs -cat /platfrom_data/w* |wc -l 
文件数据记录数为：5902
[root@master ~]# 
```

