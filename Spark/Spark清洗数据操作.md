## 对数据的更改删除操作



### 去重操作

```scala
df.distinct()           //随机删除重复数据
df.dropDuplicates("age")     //指定列的删除 
list.distinctBy()
```

---

### 缺失值删除操作

```scala
//丢弃包含 null 和 NaN 的行
//当某行数据所有值都是 null 或者 NaN 的时候丢弃此行
df.na.drop("all").show()

//当某行中特定列所有值都是 null 或者 NaN 的时候丢弃此行
df.na.drop("all", List("pm", "id")).show()

//当某行数据任意一个字段为 null 或者 NaN 的时候丢弃此行
df.na.drop().show()
df.na.drop("any").show()

//当某行中特定列任意一个字段为 null 或者 NaN 的时候丢弃此行
df.na.drop(List("pm", "id")).show()
df.na.drop("any", List("pm", "id")).show()
```

---



### 将列中的不规则字符删除

```scala
df.withColumn("最低价格",regexp_replace(col("最低价格"),"￥",""))
```

---



### 用replace将特殊值替换为好处理的值

**这里使用replace将字符串的NA、null进行了替换，虽然形式匹配，但是依旧是字符串型的数据，还需要再进行类型转换**

```scala
valwords=df.na.replace("国家",Map("中国"->"俄罗斯"))
```

---

### **填充包含** **null** **和** NaN  的列

```scala
填充所有包含 null 和 NaN 的列
df.na.fill(0).show()

填充特定包含 null 和 NaN 的列
df.na.fill(0, List("pm")).show()

根据包含 null 和 NaN 的列的不同来填充
import scala.collection.JavaConverters._
df.na.fill(Map[String, Any]("pm" -> 0).asJava).show
```

---

### 删选出带字段的数据

```scala
//Contains

f2.filter(df2("_c6").contains(null)||df2("_c6").contains("二星")||df2("_c6").contains("星")||df2("_c6").contains("四星")||df2("_c6").contains("五星"))
```

---

### 对数据保留指定小数

```scala
/**
     * 使用round保留小数，超出部分截取，不够的的不会不领
     */
    val result2: DataFrame = sourceDF.select('Sid, 'Sname, expr("round(Score,2)") as "Score")
    //展示处理后的数据
    result2.show()

    /**
     * 这种类型会补零
     */
    val result3: DataFrame = sourceDF.select('Sid, 'Sname, expr("cast(Score as decimal(18,2))") as "Score")
    result3.show()
```

---

### 计算时间差

```scala
datatime.select((unix_timestamp('updated_at) - unix_timestamp('created_at)) / 60).collect()
//除以60是分钟，除以3600s时
```





## 对数据的类型转换操作





### 对时间格式转换

```scala
df.withColumn("创建时间", from_unixtime(unix_timestamp(col("创建时间"), "yyyy-HH-dd hh:mm:ss"), "yyyy-HH-dd"))
```

---

### spark列转换数据类型

```scala
df2.select(df2("_c0").cast("string"),df2("_c1").cast("int"),df2("_c2").cast("double"),df2("_c3").cast("int"))
```

---

## 添加列操作

### **row_number()over()、rank()over()和dense_rank()over()函数的使用**

```sql
select * from                                                                     
    (                                                                          
    select name,class,s , row_number() over(partition by class order by s desc) mm from t2
    )  
    where mm=1;
```

```properties
1        95        1  --95有两名但是只显示一个
2        92        1
3        99        1 --99有两名但也只显示一个
```

 注：partition by为分组，如无需分组可去掉

在求partition by有相同数据的时候，不能用row_number()，因为如果有两个一样的，row_number()只返回一个结果;

rank()和dense_rank()可以将所有的都查找出来：

rank()和dense_rank()区别：

--rank()是跳跃排序，有两个一样时接下来就是第三个开始；

```sql
select name,class,s,rank()over(partition by class order by s desc) mm from t2
```

```properties
dss        1        95        1
ffd        1        95        1
fda        1        80        3 --直接就跳到了第三
gds        2        92        1
cfe        2        74        2
gf         3        99        1
ddd        3        99        1
3dd        3        78        3
asdf       3        55        4
adf        3        45        5
```

--dense_rank()是连续排序，有两个一样时仍然跟着是第二

```sql
select name,class,s,dense_rank() over(partition by class order by s desc) mm from t2
```

```properties
dss        1        95        1
ffd        1        95        1
fda        1        80        2 --连续排序（仍为2）
gds        2        92        1
cfe        2        74        2
gf         3        99        1
ddd        3        99        1
3dd        3        78        2
asdf       3        55        3
adf        3        45        4
```

--sum()over（）的使用根据班级进行分数求和

```sql
select name,class,s, sum(s)over(partition by class order by s desc) mm from t2
```

```properties
dss        1        95        190  --由于两个95都是第一名，所以累加时是两个第一名的相加
ffd        1        95        190
fda        1        80        270  --第一名加上第二名的
gds        2        92        92
cfe        2        74        166
gf         3        99        198
ddd        3        99        198
3dd        3        78        276
asdf       3        55        331
adf        3        45        376
```

---



## 添加行操作



### 将一列数据拆分多行数据

```scala
//第一种方法：
select `city_name`,`type` from data movies lateral view explode(split(city_rst_info,'/t')) as type

//第二种方法：
data.withColumn("city_rst_info", explode(split(col("city_rst_info"), "/t")))
```

---



### 将一列数据拆分多列数据

```scala
.withColumn("split", split(col("city_rst_info"), ":"))
.select(col("city_name"),
        col("split")(0).as("A_rst_name"),
        col("split")(1) as ("status"),
        col("split")(2) as ("order"))
```



---



### 合并操作

```scala
.withColumn("city_rst_info",concat_ws(":",col("A_rst_name"),col("order"),col("status")))
//concat_ws是带分隔符的合并列，可以直接使用concat()是不带分隔符的

.groupBy("city_name").agg(concat_ws("/t",collect_set("city_rst_info")))
//按照某个字段分组合并行 ===> GROUP_CONCAT()，collect_listb
```



### 计算时间差（天数和秒数）

```scala
//天数时间差
table.select(datediff(table.col("Start Time"), table.col("End Time"))).show()

//描述时间差
import org.apache.spark.sql.functions._

//For $notation columns // Spark 2.0
import spark.implicits._

table.withColumn("date_diff", 
   (unix_timestamp($"Start Time") - unix_timestamp($"End Time"))
).show()

```

