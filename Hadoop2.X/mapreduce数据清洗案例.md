## 数据清洗案列

### Mapper的基本操作

```java
public class GMapper extends Mapper<LongWritable, Text, Text, NullWritable> {

    @Override
    protected void map(LongWritable k1, Text v1, Context context) throws IOException, InterruptedException {

        //得到数据
        String data = v1.toString();
        //剔除标题
        if (data.startsWith("STN")) {
            return;
        }
        //分割数据
        String[] str = data.split(" ");
        //声明并创建一个新的字符串数组
        String newWords = "";
        //循环遍历数组
        for (int i = 0; i < str.length; i++) {
            //剔除空的字符串
            if (str[i].equals("")) {
                continue;
            }
            //剔除*
            if (str[i].endsWith("*")) {
                str[i] = str[i].replace("*", "");
            }
            //剔除A-I
            if (str[i].matches(".*[A-I]")) {
                str[i] = str[i].substring(0, str[i].length() - 1);
            }
            //更换缺失字段
            if (str[i].equals("9999.9") || str[i].equals("999.9") ||
                    str[i].equals("99.9")) {
                str[i] = "0.0";
            }
            //拼接新字符串
            if (i == str.length) {
                newWords += str[i];
            } else {
                newWords += str[i] + "/";
            }
        }
        //输出  不能使用new NullWritable()来定义，获取空值只能NullWritable.get()来获取
        context.write(new Text(newWords),NullWritable.get());
    }
}
```

---



## 完整版数据清洗案例一

**删除数据源中缺失值大于3个字段的数据记录**

**Mapper**

```java
package Simulation_volume_six.task_1_1;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class hotelMapper extends Mapper<LongWritable, Text,Text, NullWritable> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();

        boolean result = parseline(line,context);

        if (!result){
            return;
        }

        context.write(value,NullWritable.get());

    }

    private boolean parseline(String value, Context context) {

        int count = 0;
        String[] fields = value.split(",");

        for (int i =0 ; i < fields.length ; i++){
            if (fields[0].equals("NULL")){
                count++;
            }
        }
        if (count <3){
            context.getCounter("map","合法的数据条目为：").increment(1L);
            return true;
        }else {
            context.getCounter("map","不合法的数据条目为：").increment(1L);
            return false;
        }
    }
}

```

Diver

```java
package Simulation_volume_six.task_1_1;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class hotelDiver {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        args = new String[]{"Data/hoteldata.csv", "Date/output"};

        Configuration entries = new Configuration();
        Job instance = Job.getInstance(entries);

        instance.setJarByClass(hotelDiver.class);

        instance.setMapperClass(hotelMapper.class);

        instance.setOutputKeyClass(Text.class);
        instance.setOutputValueClass(NullWritable.class);

        instance.setNumReduceTasks(0);

        FileInputFormat.setInputPaths(instance,new Path(args[0]));
        Path outPath = new Path(args[1]);
        FileSystem fileSystem = outPath.getFileSystem(entries);
        if (fileSystem.exists(outPath)){
            fileSystem.delete(outPath,true);
        }
        FileOutputFormat.setOutputPath(instance,new Path(args[1]));

        instance.waitForCompletion(true);

    }
}
```

---

## 完整版数据清洗案例二

**将字段{星级、评论数、评分}中任意字段为空的数据删除，并打印输出删除条目数**

Mapper

```java
package Simulation_volume_six.task_1_2;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class hotelMapper extends Mapper<LongWritable, Text,Text, NullWritable> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();

        boolean result = parseLine(line,context);

        if (!result){
            return;
        }

        context.write(value,NullWritable.get());
    }

    private boolean parseLine(String line, Context context) {
        String[] flieds = line.split(",");
        int count = 0;

        if (flieds[6].equals("NULL")){
            count++;
        }else if (flieds[10].equals("NULL")){
            count++;
        }else if (flieds[11].equals("NULL")){
            count++;
        }

        if (count >= 1){
            context.getCounter("map","不合法的数据条目数：").increment(1L);
            return false;
        }else {
            context.getCounter("map","合法的数据条目数：").increment(1L);
            return true;
        }

    }
}
```

Diver

```java
package Simulation_volume_six.task_1_2;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class hotelDiver {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {

        Configuration entries = new Configuration();
        Job instance = Job.getInstance(entries);

        instance.setJarByClass(hotelDiver.class);

        instance.setMapperClass(hotelMapper.class);

        instance.setOutputKeyClass(Text.class);
        instance.setOutputValueClass(NullWritable.class);

        instance.setNumReduceTasks(0);

        FileInputFormat.setInputPaths(instance,new Path(args[0]));
        Path outpath = new Path(args[1]);
        FileSystem fileSystem = outpath.getFileSystem(entries);
        if (fileSystem.exists(outpath)){
            fileSystem.delete(outpath,true);
        }
        FileOutputFormat.setOutputPath(instance,new Path(args[1]));

        instance.waitForCompletion(true);
    }
}
```

---

## 完整版数据清洗案例三

**以直销拒单率升序排列并输出前10条统计结果**

Bean

```java
package Simulation_volume_eight.task_2_2;


import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class hotelBean implements WritableComparable<hotelBean> {
    private double CountA;

    public hotelBean() {
    }

    public hotelBean(double count) {
        this.CountA = count;
    }

    //排序（正序）
    @Override
    public int compareTo(hotelBean o) {
        int result;
        if (CountA > o.getCount()){
            result = -1;
        }else if (CountA < o.getCount()){
            result = 1;
        }else {
            result = 0;
        }
        return result;
    }

    public double getCount() {
        return CountA;
    }

    public void setCount(double count) {
        this.CountA = count;
    }


    //序列化方法
    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeDouble(getCount());
    }

    //反序列方法
    @Override
    public void readFields(DataInput dataInput) throws IOException {
        CountA = dataInput.readDouble();
    }

    //重写toString方法
    @Override
    public String toString() {
        return  "\t\t" + CountA + "%";
    }
}
```

Mapper

```java
package Simulation_volume_eight.task_2_2;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class hotelMapper extends Mapper<LongWritable,Text,Text, hotelBean> {

    hotelBean hotelbean = new hotelBean();
    Text v = new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {


        //获取一行数据
        String line = value.toString();

        //切割数据
        String[] fields = line.split(",");

        //分装对象
        String nowname = fields[0];
        double count = Double.parseDouble(fields[4]);

        hotelbean.setCount(count);
        v.set(nowname);

        //输出
        context.write(v,hotelbean);
    }
}
```

Diver

```scala
package Simulation_volume_eight.task_2_2;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class hotelDriver {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        args = new String[]{"Data/accommodationhive1/part-r-00000-5a74839d-5141-4d85-93f8-1c36982746eb.csv","Data/output"};
        Configuration entries = new Configuration();
        Job instance = Job.getInstance(entries);

        instance.setJarByClass(hotelDriver.class);

        instance.setMapperClass(hotelMapper.class);

        instance.setOutputKeyClass(hotelBean.class);
        instance.setOutputValueClass(Text.class);

        instance.setNumReduceTasks(0);

        FileInputFormat.setInputPaths(instance,new Path(args[0]));
        Path outpath = new Path(args[1]);
        FileSystem fileSystem = outpath.getFileSystem(entries);
        if (fileSystem.exists(outpath)){
            fileSystem.delete(outpath,true);
        }

        FileOutputFormat.setOutputPath(instance,outpath);

        instance.waitForCompletion(true);

    }
}
```



---



## 将清洗结果输出至MySQL中



**map**

```java
package Test1.task2_3;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class hotelMapper extends Mapper<LongWritable, Text,Text,Text> {
    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context) throws IOException, InterruptedException {
        String line = value.toString();

        if(line.startsWith("SEQ")){
            return;
        }

        String[] fields = line.split(",");

        String province = fields[3];
        String Judanlv = fields[24].replace("%","");

        String values = Judanlv + "\t" + new IntWritable(1);

        context.write(new Text(province),new Text(values));
    }
}
```



**reduce**

```java
package Test1.task2_3;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.*;

public class hotelreduce extends Reducer<Text, Text, hotelBean, NullWritable> {
    Map<String,Double> map = new HashMap<String,Double>();
    @Override
    protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, hotelBean, NullWritable>.Context context) throws IOException, InterruptedException {
        double cityCount = 0;
        double judanlvCount = 0;
        for (Text value:values){
            String[] fields = value.toString().split("\t");
            judanlvCount += Double.parseDouble(fields[0]);
            cityCount += Double.parseDouble(fields[1]);
        }
        double result = (judanlvCount/cityCount)*1000;
        String province = key.toString();

        map.put(province,result);
    }

    @Override
    protected void cleanup(Reducer<Text, Text, hotelBean, NullWritable>.Context context) throws IOException, InterruptedException {
        LinkedList<Map.Entry<String, Double>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return (int) (o1.getValue()-o2.getValue());
            }
        });
        hotelBean hotelBean = new hotelBean();
        for (int i = 0 ; i < list.size() ; i++){
            hotelBean.setProvince(list.get(i).getKey());
            hotelBean.setJudanlv(((list.get(i).getValue())/1000));
            context.write(hotelBean,NullWritable.get());
        }
    }
}
```



javaBean

```java
package Test1.task2_3;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapred.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class hotelBean implements Writable, DBWritable {

    private String province;
    private double Judanlv;

    public hotelBean() {
    }

    public hotelBean(String province, double judanlv) {
        this.province = province;
        this.Judanlv = judanlv;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public double getJudanlv() {
        return Judanlv;
    }

    public void setJudanlv(double judanlv) {
        this.Judanlv = judanlv;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(this.province);
        out.writeDouble(this.Judanlv);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.province = in.readUTF();
        this.Judanlv = in.readDouble();
    }

    @Override
    public void write(PreparedStatement statement) throws SQLException {
        statement.setString(1,this.province);
        statement.setDouble(2,this.Judanlv);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {
        this.province = resultSet.getString(1);
        this.Judanlv = resultSet.getDouble(2);
    }
}

```

Driver

```java
package Test1.task2_3;

import Analog_volume_2_Test.task6_test2.jdDriver;
import Analog_volume_2_Test.task6_test2.jdreduce;
import Analog_volume_2_Test.task6_test2.jsMapper;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class hotelDriver {
    public static String driverClass = "com.mysql.jdbc.Driver";
    public static String dbUrl = "jdbc:mysql://192.168.3.60:3306/accommodationdata";
    public static String userName = "root";
    public static String passwd = "Password123$";
    public static String inputFilePath = "hdfs://192.168.3.60:9000/accommodationsparktask2/p*";
    public static String tableName = "table3_2";
    public static String [] fields = {"province","Jvdanlv"};

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {

        Configuration entries = new Configuration();
        DBConfiguration.configureDB(entries,driverClass,dbUrl,userName,passwd);
        Job instance = Job.getInstance(entries);

        instance.setJarByClass(hotelDriver.class);

        instance.setMapperClass(hotelMapper.class);
        instance.setReducerClass(hotelreduce.class);

        instance.setMapOutputKeyClass(Text.class);
        instance.setMapOutputValueClass(Text.class);

//        instance.setOutputKeyClass(Text.class);
//        instance.setOutputValueClass(DoubleWritable.class);

        FileInputFormat.setInputPaths(instance,new Path(inputFilePath));
        DBOutputFormat.setOutput(instance,tableName,fields);

//        Path output = new Path(args[1]);
//        FileSystem fileSystem = output.getFileSystem(entries);
//
//        if (fileSystem.exists(output)){
//            fileSystem.delete(output,true);
//        }
//        FileOutputFormat.setOutputPath(instance,output);

        instance.waitForCompletion(true);

    }
}
```

