## DOM4J介绍



dom4j是一个Java的XML API，是jdom的升级品，用来读写XML文件的。dom4j是一个十分优秀的JavaXML API，具有性能优异、功能强大和极其易使用的特点，它的性能超过sun公司官方的dom技术，同时它也是一个开放源代码的软件，可以在SourceForge上找到它。

Dom4j是一个易用的、开源的库，用于XML，XPath和XSLT。它应用于Java平台，采用了Java集合框架并完全支持DOM，SAX和JAXP。



### XML文件

```xml
<Response T='203' T1='6' TaskID='20130800001963' MediaNum='3' Result = '1' Desc='查询成功!' >
    <Media Name='IMG_20130425_141838.jpg' Mediasource ='1' Type ='1' Code='/9j/4AAQSkZJRgABAQA0'>图片1</Media>
    <Media Name='IMG_20130425_141838.jpg' Mediasource ='2' Type ='1' Code='/9j/4AAQSkZJRgABAQA0'>图片2</Media>
    <Media Name='IMG_20130425_141838.jpg' Mediasource ='3' Type ='1' Code='/9j/4AAQSkZJRgABAQA0'>图片3</Media>
</Response>
```



## DOM4J使用详解



### 步骤1：加载xml文件



加载xml可以分为主要的两种方式

1、直接加载文件所在的路径地址

2、加载字符串形式的xml(此方式主要是用在服务器返回结果中)

#### 直接加载文件路径

```java
SAXReader reader = new SAXReader();
Document document = null;
try {
     document = reader.read(new File("E:\\CZBK\\day01\\caseUp.xml"));
} catch (DocumentException e) {
    e.printStackTrace();
    }
```

#### 加载字符串形式的xml

```java
 SAXReader reader = new SAXReader();
    Document document = null;
    try {
        //result是需要解析的字符串 
        //解析字符串需要转换成流的形式，可以指定转换字符编码
        document = reader.read(new ByteArrayInputStream(result.getBytes("UTF-8")));
    } catch (DocumentException  e) {
        e.printStackTrace();
    }
```



### 步骤2：解析XML

在解析XML之前，我们先来介绍下XML的结构称呼，清楚下面4个问题对解析XML很有帮助

什么是节点(node)？什么是元素(element)？什么是属性(attribute)？什么是文本值(value)？

**节点**：**“Response”、“Media”这些称之为节点**

**元素**：**以一个完整的标签结束称之为元素，包含整个元素内容。例如：**

```xml
<Media Name='IMG_20130425_141838.jpg' Mediasource ='1' Type ='1' Code='/9j/4AAQSkZJRgABAQA0'>图片1</media>
```

**属性**：**节点的属性值，对节点内容加之说明。例如：**

```xml
T='203' T1='6' TaskID='20130800001963' MediaNum='3' Result = '1' Desc='查询成功!'
```

**文本值**：**“图片1”称之为文本值。**

在项目中无非就是围绕元素、属性和文本值进行操作，所以掌握好这三部分的取值方法，也就掌握了XML解析。



#### 获取根节点

```java
//获取整个文档
Element rootElement = document.getRootElement();
```

rootElement包含整个xml文档的内容，也就是Response标签包含的所有内容



#### 获取Response节点的属性值

```java
//获取Response节点的Result属性值
String responseResult = rootElement.attributeValue("Result");
```



#### 获取Media元素

```java
//获取第一个Media元素
Element mediaElement = rootElement.element("Media");
//获取所有的Media元素
List allMeidaElements = rootElement.elements("Media");
```



#### 获取Media标签的文本值

```java
 //获取第一个Meida元素的文本值
String value = mediaElement.getText();
```





## 完整代码

```java
import java.io.File;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Textxml {
    public void xml() {
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(new File("E:\\CZBK\\day01\\caseUp.xml"));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        //获取整个文档
        Element rootElement = document.getRootElement();
        System.out.println("整个文档:"+rootElement.asXML());
        
        //获取Response节点的Result属性值
        String responseResult = rootElement.attributeValue("Result");
        System.out.println("Response节点的Result属性值:"+responseResult);
        
        //获取第一个Media元素
        Element mediaElement = rootElement.element("Media");
        System.out.println("第一个Media元素:"+mediaElement.asXML());
        
        //获取所有的Media元素
        List allMeidaElements = rootElement.elements("Media");
        
        //获取第一个Media元素的Name属性值
        String mediaName = mediaElement.attributeValue("Name");
        System.out.println("第一个Media元素的Name属性值:"+mediaName);
        
        //遍历所有的Media元素的Name属性值
        for (int i = 0; i < allMeidaElements.size(); i++) {
            Element element = (Element) allMeidaElements.get(i);
            String name = element.attributeValue("Name");
        }
        
        //获取第一个Meida元素的文本值
        String value = mediaElement.getText();
        System.out.println("第一个Meida元素的文本值:"+value);
    }

    public static void main(String[] args) {
        Textxml textxml = new Textxml();
        textxml.xml();
    }
}
```

## 运行结果

```properties
整个文档:<Response T="203" T1="6" TaskID="20130800001963" MediaNum="3" Result="1" Desc="查询成功!">
<Media Name="IMG_20130425_141838.jpg" Mediasource="1" Type="1" Code="/9j/4AAQSkZJRgABAQA0">图片1</Media>
    <Media Name="IMG_20130425_141838.jpg" Mediasource="2" Type="1" Code="/9j/4AAQSkZJRgABAQA0">图片2</Media>
    <Media Name="IMG_20130425_141838.jpg" Mediasource="3" Type="1" Code="/9j/4AAQSkZJRgABAQA0">图片3</Media>
</Response>
Response节点的Result属性值:1
第一个Media元素:<Media Name="IMG_20130425_141838.jpg" Mediasource="1" Type="1" Code="/9j/4AAQSkZJRgABAQA0">图片1</Media>
第一个Media元素的Name属性值:IMG_20130425_141838.jpg
第一个Meida元素的文本值:图片1
```

