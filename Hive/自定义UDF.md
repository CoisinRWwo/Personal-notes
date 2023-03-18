## 关于自定义UDF函数的使用



```java
//首先需要继承UDF类，重载 evaluate 方法

//案例一:返回三个数的最大值
import org.apache.hadoop.hive.ql.exec.UDF;

public class MyMaxUDF extends UDF {
    public int evaluate(int a, int b, int c) {
        return max(max(a, b), c);
    }

    public static int max(int a, int b) {
        return (a > b ? a:b);
    }
}


//案例二:自定义数值的区间
import org.apache.hadoop.hive.ql.exec.UDF;

public class DivideScore extends UDF {
    public String evaluate(String raw){
        if(raw!=null){
            double num = Double.parseDouble(raw);
            if(num>4.5&num<=5){
                return "4.5-5分";
            }else if(num>4&num<=4.5){
                return "4-4.5分";
            }else if(num>3.5&num<=4){
                return "3.5-4分";
            }else if(num>3&num<=3.5){
                return "3-3.5分";
            }else if(num<=3){
                return "<3分";
            }
        }
        return raw;
    }
}
```

### 2. 打包上传集群

使用 **Maven / package** 方式打包

然后在hive中导入

```sql
hive> add jar /chinaskills/cn.SHF.MyProJect-1.0-SNAPSHOT.jar;
Added [/chinaskills/cn.SHF.MyProJect-1.0-SNAPSHOT.jar] to class path
Added resources: [/chinaskills/cn.SHF.MyProJect-1.0-SNAPSHOT.jar]
```

创建函数

```sql
hive> create temporary function getmax as 'MyMaxUDF';
OK
Time taken: 0.005 seconds

// temporary 关键词为临时函数
```

最后直接使用

```sql
hive> select Id,getmax(aa,bb,cc) from externaltableTest1;
OK
haha	23451
aaa	45234
lashd	6235
aidsn	2346
pjf	3234
sdf	3452
sdifn	6734
Time taken: 0.101 seconds, Fetched: 7 row(s)
hive> 
```

