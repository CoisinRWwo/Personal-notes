## 函数类型

​      无论Hive还是SparkSQL分析处理数据时，往往需要使用函数，SparkSQL模块本身自带很多实现公共功能的函数，在

org.apache.spark.sql.functions中。SparkSQL与HIVE一样支持定义函数：UDF和UDAF，尤其UDF函数在实际项目中使用最为广泛。

1. UDF（User-Defined-function）函数

   一对一的关系，输入一个值经过函数以后输出一个值

2. UDAF（User-Defined Aggregation Function）聚合函数

   多对一的关系，输入多个值输出一个值，通常与 groupBy 联合使用

3. UDTF（User-Defined Table-Generating Functions）函数

   一对多的关系，输入一个值输出多个值（一行变为多行）

   用户自定义生成函数，有点像flatMap

---

## UDF函数案例



```scala
 //方式一 : 通过函数变量的形式
    val len_code = (str: String, addr: String) => str.length + addr.length
    val strlen = udf(len_code)
 
    //方式二 ：注册UDF
    val concat_w = (arg1: String, arg2: String) => {
      arg1 + arg2
    }
    spark.udf.register("cont", concat_w)


//测试
    val df = spark
      .createDataFrame(orgRDD)
    val df2 = df.withColumn("len", strlen(df("city"), df("name")))
    df2.show()
    
    val resultDF2 = spark.sql("select cont(name,city) from user")
    resultDF2.show()
```



```scala
 //TODO ======SQL
    //TODO 自定义UDF函数
    spark.udf.register("small2big",(value:String)=>{
      value.toUpperCase()
    })
    ds.createOrReplaceTempView("t_word")
    val sql:String =
      """
        |select value,small2big(value) as bigValue
        |from t_word
        |""".stripMargin
    spark.sql(sql).show()
```

```scala
//TODO ======DSL
    //TODO 自定义UDF函数
    import org.apache.spark.sql.functions._
    val small2big2: UserDefinedFunction = udf((value:String)=>{
      value.toUpperCase()
    })
    ds.select('value,small2big2('value).as("bigValue")).show()
```

```scala
 df.createOrReplaceTempView("user")

        spark.udf.register("prefixName", (name:String) => {
            "Name: " + name
        })

        spark.sql("select age, prefixName(username) from user").show

```

---

## UDAF函数案例

```scala
spark.udf.register("ageAvg", new MyAvgUDAF())

        spark.sql("select ageAvg(age) from user").show
        
         /*
     自定义聚合函数类：计算年龄的平均值
     1. 继承UserDefinedAggregateFunction
     2. 重写方法(8)
     */
    class MyAvgUDAF extends UserDefinedAggregateFunction{
        // 输入数据的结构 : Int
        override def inputSchema: StructType = {
            StructType(
                Array(
                    StructField("age", LongType)
                )
            )
        }
        // 缓冲区数据的结构 : Buffer
        override def bufferSchema: StructType = {
            StructType(
                Array(
                    StructField("total", LongType),
                    StructField("count", LongType)
                )
            )
        }

        // 函数计算结果的数据类型：Out
        override def dataType: DataType = LongType

        // 函数的稳定性
        override def deterministic: Boolean = true

        // 缓冲区初始化
        override def initialize(buffer: MutableAggregationBuffer): Unit = {
            //buffer(0) = 0L
            //buffer(1) = 0L

            buffer.update(0, 0L)
            buffer.update(1, 0L)
        }

        // 根据输入的值更新缓冲区数据
        override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
            buffer.update(0, buffer.getLong(0)+input.getLong(0))
            buffer.update(1, buffer.getLong(1)+1)
        }

        // 缓冲区数据合并
        override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
            buffer1.update(0, buffer1.getLong(0) + buffer2.getLong(0))
            buffer1.update(1, buffer1.getLong(1) + buffer2.getLong(1))
        }

        // 计算平均值
        override def evaluate(buffer: Row): Any = {
            buffer.getLong(0)/buffer.getLong(1)
        }
    }
```

