```java
public class CleanMap extends Mapper<LongWritable,Text,Text,NullWritable> {
    private static Text text = new Text();
    public void map(LongWritable key,Text value,Context context) throws IOException, InterruptedException {
        String line = value.toString();
        //判断读取的行是否包含“[” 和 “]” 或读取的行为空
        if (line.indexOf("[")==0 || line.indexOf("]")==0 || line.trim().isEmpty()){
            return;
        }
        //删除逗号
        line = line.substring(0, line.length()-1);
        //删除逗号时可能会删掉每行最后的"}"就是判断数据是否符合json格式这里我们要加一层判断否则解析json数据会出错
        if (!line.endsWith("}")){
            line = line.concat("}");
        }
        //解析json数据
        JSONObject jsonObject = JSONObject.parseObject(line);
        String[] data = new String[5];
        String name = jsonObject.getString("name");
        判断name字段是否为空
        if (name==null || name.trim().isEmpty()) {
            return;
        }
        data[0] = jsonObject.getString("rank");
        data[1] = name.trim();
        data[2] = jsonObject.getString("actors");
        data[3] = jsonObject.getString("time");
        data[4] = jsonObject.getString("score");
        //循环判空
        for(String i : data) {
            if(i==null||i.equals("")) {
                return;
            }
        }
        //分隔数据
        String end = "";
        for (String item: data){
            end = end + item + "|";
        }
        end = end.substring(0, end.length()-1);
        //将数据转为text类型并作为key输出
        text.set(end);
        context.write(text, NullWritable.get());
    }
}
```

