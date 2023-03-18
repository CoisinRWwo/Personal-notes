# Spark中读取数据

在spark中读取数据的方式可分为SparkCore和SparkSQL。

SparkCore所读取的数据类型是类似字符串类型，不带任何结构与数据类型的一条数据。

SparkSQL所读取的文件是带结构带数据类型的table，其更好操作



## SparkCore读取文件

这里读取的文件数据类型的**RDD**格式

```scala
val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("task1")
val sc = new SparkContext(conf)
sc.textFile(path)
```



## SparkSQL读取文件

SparkSQL所读取的文件数据类型为**Dataframe**，是一个带结构的table，

也可将其转换为**DataSet**，就变成一个带结构和数据类型的table。



以下两种方式是读取csv文件格式并设置第一行为表头

```scala
val df: DataFrame = spark.read
      .option("header",true)         //设置第一列为表头
      .option("inferSchema",true)    //设置自动推断类型
      .csv("Data/hoteldata.csv")
      
val df1: DataFrame = spark.read
      .format("csv")
      .option("header", true)
      .option("inferSchema", true)
      .load("Data/hoteldata.csv")
```

同理也可读取json格式的文件

```scala
 val jsonData: DataFrame = spark.read.json("Data/wm_5902.json")
```



### option 参数详解



在SparkSQL下读取文件的时候可需求设置多个参数（option）。

| 参数                      | 解释                                                         |
| ------------------------- | ------------------------------------------------------------ |
| sep                       | 默认是`,` 指定单个字符分割字段和值                           |
| encoding                  | 默认是`uft-8`通过给定的编码类型进行解码                      |
| quote                     | 默认是`“`，其中分隔符可以是值的一部分，设置用于转义带引号的值的单个字符。如果您想关闭引号，则需要设置一个空字符串，而不是`null`。 |
| escape                    | 默认(`\`)设置单个字符用于在引号里面转义引号                  |
| charToEscapeQuoteEscaping | 默认是转义字符（上面的`escape`）或者`\0`，当转义字符和引号(`quote`)字符不同的时候，默认是转义字符(escape)，否则为`\0` |
| comment                   | 默认是空值，设置用于跳过行的单个字符，以该字符开头。默认情况下，它是禁用的 |
| header                    | 默认是`false`，将第一行作为列名                              |
| enforceSchema             | 默认是`true`， 如果将其设置为`true`，则指定或推断的模式将强制应用于数据源文件，而`CSV`文件中的标头将被忽略。 如果选项设置为`false`，则在`header`选项设置为`true`的情况下，将针对CSV文件中的所有标题验证模式。模式中的字段名称和CSV标头中的列名称是根据它们的位置检查的，并考虑了*`spark.sql.caseSensitive`。虽然默认值为`true`，但是建议禁用 `enforceSchema`选项，以避免产生错误的结果 |
| inferSchema               | inferSchema`（默认为`false`）：从数据自动推断输入模式。 *需要对数据进行一次额外的传递 |
| samplingRatio             | 默认为`1.0`,定义用于模式推断的行的分数                       |
| ignoreLeadingWhiteSpace   | 默认为`false`,一个标志，指示是否应跳过正在读取的值中的前导空格 |
| ignoreTrailingWhiteSpace  | 默认为`false`一个标志，指示是否应跳过正在读取的值的结尾空格  |
| nullValue                 | 默认是空的字符串,设置null值的字符串表示形式。从2.0.1开始，这适用于所有支持的类型，包括字符串类型 |
| emptyValue                | 默认是空字符串,设置一个空值的字符串表示形式                  |
| nanValue                  | 默认是`Nan`,设置非数字的字符串表示形式                       |
| positiveInf               | 默认是`Inf`                                                  |
| negativeInf               | 默认是`-Inf` 设置负无穷值的字符串表示形式                    |
| dateFormat                | 默认是`yyyy-MM-dd`,设置指示日期格式的字符串。自定义日期格式遵循`java.text.SimpleDateFormat`中的格式。这适用于日期类型 |
| timestampFormat           | 默认是`yyyy-MM-dd'T'HH:mm:ss.SSSXXX`，设置表示时间戳格式的字符串。自定义日期格式遵循`java.text.SimpleDateFormat`中的格式。这适用于时间戳记类型 |
| maxColumns                | 默认是`20480`定义多少列数目的硬性设置                        |
| maxCharsPerColumn         | 默认是`-1`定义读取的任何给定值允许的最大字符数。默认情况下为-1，表示长度不受限制 |
| mode                      | 默认（允许）允许一种在解析过程中处理损坏记录的模式。它支持以下不区分大小写的模式。请注意，`Spark`尝试在列修剪下仅解析`CSV`中必需的列。因此，损坏的记录可以根据所需的字段集而有所不同。可以通过`spark.sql.csv.parser.columnPruning.enabled`（默认启用）来控制此行为。 |



## SparkSQL与SparkCore的转换

### RDD与Dataframe的转换

