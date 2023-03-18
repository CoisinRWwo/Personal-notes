## 从hdfs上将数据到导出到mysql中

```bash
[root@master ~]# sqoop export --connect jdbc:mysql://192.168.3.60:3306/accommodationdata  --username root --password Password123$ --table table3_5 --num-mappers 1 --export-dir /accommodationhive1 --input-fields-terminated-by ','
```

