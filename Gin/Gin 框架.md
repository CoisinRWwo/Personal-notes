## 一、Gin 介绍

Gin 是一个 Go (Golang) 编写的轻量级 http web 框架，运行速度非常快，如果你是性能和高效的追求者，我们推荐你使用 Gin 框架。

Gin 最擅长的就是 Api 接口的高并发，如果项目的规模不大，业务相对简单，这个时候我们也推荐您使用 Gin。

当某个接口的性能遭到较大挑战的时候，这个还是可以考虑使用 Gin 重写接口

Gin 也是一个流行的 golang Web 框架，Github Strat 量已经超过了 50k



Gin 的官网：https://gin-gonic.com/zh-cn/ 

Gin Github 地址：https://github.com/gin-gonic/gin



## 二、Gin 环境搭建

要安装 Gin 软件包，需要先安装 Go 并设置 Go 工作区。

1.下载并安装 gin：

```go
$ go get -u github.com/gin-gonic/gin
```

2.将 gin 引入到代码中：

```go
import "github.com/gin-gonic/gin"
```

3. （可选）如果使用诸如 http.StatusOK 之类的常量，则需要引入 net/http 包：

```go
import "net/http"
```

4、新建 Main.go 配置路由

```go
package main

import (
	"github.com/gin-gonic/gin"
)

func main() {
	// 创建一个默认的路由引擎
	r := gin.Default()
	// 配置路由
	r.GET("/", func(c *gin.Context) {
		c.JSON(200, gin.H{ // c.JSON：返回 JSON 格式的数据
			"message": "Hello world!"})
	})
	// 启动 HTTP 服务，默认在 0.0.0.0:8080 启动服务
	r.Run()
}

```

5、运行你的项目

```
 go run main.go
```

6、要改变默认启动的端口

```go
r.Run(":9000")
```



如果 go get 失败请参考:

http://bbs.itying.com/topic/5ed08edee7c0790f8475e276



## 三、golang 程序的热加载

所谓热加载就是当我们对代码进行修改时，程序能够自动重新加载并执行，这在我们开发中是非常便利的，可以快速进行代码测试，省去了每次手动重新编译

beego 中我们可以使用官方给我们提供的 bee 工具来热加载项目，但是gin 中并没有官方提供的热加载工具，这个时候我们要实现热加载就可以借助第三方的工具。

工具 1（推荐）：https://github.com/gravityblast/fresh

```
go get github.com/pilu/fresh
D:\gin_demo>fresh
```

工具 2：https://github.com/codegangsta/gin

```
go get -u github.com/codegangsta/gin
D:\gin_demo>gin run main.go
```



## 四、Gin 框架中的路由

### 4.1、路由概述

路由（Routing）是由一个 URI（或者叫路径）和一个特定的 HTTP 方法（GET、POST 等）组成的，涉及到应用如何响应客户端对某个网站节点的访问。

RESTful API 是目前比较成熟的一套互联网应用程序的 API 设计理论，所以我们设计我们的路由的时候建议参考 RESTful API 指南。

在 RESTful 架构中，每个网址代表一种资源，不同的请求方式表示执行不同的操作：

|  GET（SELECT）   |         从服务器取出资源（一项或多项）         |
| :--------------: | :--------------------------------------------: |
|  POST（CREATE）  |              在服务器新建一个资源              |
|  PUT（UPDATE）   | 在服务器更新资源（客户端提供改变后的完整资源） |
| DELETE（DELETE） |                从服务器删除资源                |



### 4.2、简单的路由配置

**简单的路由配置**(可以通过 postman 测试)



当用 GET 请求访问一个网址的时候，做什么事情：

```go
r.GET("网址", func(c *gin.Context) {
    c.String(200, "Get")
})
```



当用 POST 访问一个网址的时候，做什么事情：

```go
r.POST("网址", func(c *gin.Context) {
    c.String(200, "POST")
})
```



当用 PUT 访问一个网址的时候，执行的操作：

```go
r.PUT("网址", func(c *gin.Context) {
    c.String(200, "PUT")
})
```



当用 DELETE 访问一个网址的时候，执行的操作：

```go
r.DELETE("网址", func(c *gin.Context) {
    c.String(200, "DELETE")
})
```



**路由里面获取 Get 传值 **

域名/news?aid=20

```go
r.GET("/news", func(c *gin.Context) {
    aid := c.Query("aid")
    c.String(200, "aid=%s", aid)
})
```





### 4.3、 c.String() c.JSON() c.JSONP() c.XML() c.HTML()

返回一个字符串

```go
r.GET("/news", func(c *gin.Context) {
    aid := c.Query("aid")
    c.String(200, "aid=%s", aid)
})
```



**返回一个 JSON 数据**

```go
package main

import (
	"github.com/gin-gonic/gin"
	"net/http"
)

func main() {
	r := gin.Default()
	// gin.H 是 map[string]interface{}的缩写
	r.GET("/someJSON", func(c *gin.Context) {
		// 方式一：自己拼接 JSON
		c.JSON(http.StatusOK, gin.H{"message": "Hello world!"})
	})
	r.GET("/moreJSON", func(c *gin.Context) {
		// 方法二：使用结构体
		var msg struct {
			Name string `json:"user"` 
            Message string
			Age int
		}
		msg.Name = "IT 营学院"
		msg.Message = "Hello world!"
		msg.Age = 18
		c.JSON(http.StatusOK, msg)
	})
	r.Run(":8080")
}

```



**JSOPN**

```go
package main

import (
	"github.com/gin-gonic/gin"
	"net/http"
)

func main() {
	r := gin.Default()
	r.GET("/JSONP", func(c *gin.Context) {
		data := map[string]interface{}{"foo": "bar"}
		// /JSONP?callback=x
		// 将输出：x({\"foo\":\"bar\"})
		c.JSONP(http.StatusOK, data)
	})
	// 监听并在 0.0.0.0:8080 上启动服务
	r.Run(":8080")
}

```



**返回 XML 数据**

```go
package main

import (
	"github.com/gin-gonic/gin"
	"net/http"
)

func main() {
	r := gin.Default()
	// gin.H 是 map[string]interface{}的缩写
	r.GET("/someXML", func(c *gin.Context) {
		// 方式一：自己拼接 JSON
		c.XML(http.StatusOK, gin.H{"message": "Hello world!"})
	})
	r.GET("/moreXML", func(c *gin.Context) {
		// 方法二：使用结构体
		type MessageRecord struct {
			Name    string
			Message string
			Age     int
		}
		var msg MessageRecord
		msg.Name = "IT 营学院"
		msg.Message = "Hello world!"
		msg.Age = 18
		c.XML(http.StatusOK, msg)
	})
	r.Run(":8080")
}

```



**渲染模板**

```go
router.GET("/", func(c *gin.Context) {
    c.HTML(http.StatusOK, "default/index.html", map[string]interface{}{ 
        "title": "前台首页"
	})
})
```





## 五、Gin HTML 模板渲染

### 5.1、全部模板放在一个目录里面的配置方法

1、我们首先在项目根目录新建 templates 文件夹，然后在文件夹中新建index.html

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <h1>这是一个 html 模板</h1>
    <h3>{{.title}}</h3>
</body>
</html>
```



2、Gin 框架中使用 c.HTML 可以渲染模板，渲染模板前需要使用LoadHTMLGlob()或者LoadHTMLFiles()方法加载模板。

```go
router.GET("/", func(c *gin.Context) {
		c.HTML(http.StatusOK, "default/index.html", map[string]interface{}{
            "title": "前台首页"})
	})
	router.GET("/", func(c *gin.Context) {
		c.HTML(http.StatusOK, "index.html", gin.H{
            "title": "Main website"})
	})
```

```go
package main

import (
	"github.com/gin-gonic/gin"
	"net/http"
)

func main() {
	router := gin.Default()
	router.LoadHTMLGlob("templates/*")
	//router.LoadHTMLFiles("templates/template1.html", "templates/template2.html")
	router.GET("/", func(c *gin.Context) {
		c.HTML(http.StatusOK, "index.html", gin.H{
            "title": "Main website"
        })
	})
	router.Run(":8080")
}

```



### 5.2、模板放在不同目录里面的配置方法

Gin 框架中如果不同目录下面有同名模板的话我们需要使用下面方法加载模板

**注意**：定义模板的时候需要通过 define 定义名称



**templates/admin/index.html**

<!-- 相当于给模板定义一个名字 define end 成对出现-->

```html
{{ define "admin/index.html" }}
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Document</title>
    </head>
    <body>
        <h1>后台模板</h1>
        <h3>{{.title}}</h3>
    </body>
    </html>
{{ end }}
```



**业务逻辑**

```go
package main

import (
	"github.com/gin-gonic/gin"
	"net/http"
)

func main() {
	router := gin.Default()
	router.LoadHTMLGlob("templates/**/*")
	router.GET("/", func(c *gin.Context) {
		c.HTML(http.StatusOK, "default/index.html", gin.H{
            "title": "前台首页"
        })
	})
	router.GET("/admin", func(c *gin.Context) {
		c.HTML(http.StatusOK, "admin/index.html", gin.H{
            "title": "后台首页"
        })
	})
	router.Run(":8080")
}

```

**注意**：如果模板在多级目录里面的话需要这样配置 r.LoadHTMLGlob("templates/**  /** /*") /**表示目录



### 5.3、gin 模板基本语法

#### 1、{{.}} 输出数据

模板语法都包含在{{和}}中间，其中{{.}}中的点表示当前对象。

当我们传入一个结构体对象时，我们可以根据.来访问结构体的对应字段。例如：

**业务逻辑**

```go
package main

import (
	"github.com/gin-gonic/gin"
	"net/http"
)

type UserInfo struct {
	Name   string
	Gender string
	Age    int
}

func main() {
	router := gin.Default()
	router.LoadHTMLGlob("templates/**/*")
	user := UserInfo{
		Name: "张三", 
        Gender: "男", 
        Age: 18}
	router.GET("/", func(c *gin.Context) {
		c.HTML(http.StatusOK, "default/index.html", map[string]interface{}{
            "title": "前台首页", 
            "user": user
        })
	})
	router.Run(":8080")
}

```



**模板**

<!-- 相当于给模板定义一个名字 define end 成对出现-->

```html
{{ define "default/index.html" }}
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Document</title>
    </head>
    <body>
        <h1>前台模板</h1>
        <h3>{{.title}}</h3>
        <h4>{{.user.Name}}</h4>
        <h4>{{.user.Age}}</h4>
    </body>
    </html>
{{end}}
```



#### 2、注释

```go
{{/* a comment */}}
```

注释，执行时会忽略。可以多行。注释不能嵌套，并且必须紧贴分界符始止。



#### 3、变量

我们还可以在模板中声明变量，用来保存传入模板的数据或其他语句生成的结果。具体语法如下：

```html
<h4>{{$obj := .title}}</h4>
```

```html
<h4>{{$obj}}</h4>
```



#### 4、移除空格

有时候我们在使用模板语法的时候会不可避免的引入一下空格或者换行符，这样模板最终渲染出来的内容可能就和我们想的不一样，这个时候可以使用{{-语法去除模板内容左侧的所有空白符号， 使用-}}去除模板内容右侧的所有空白符号。

例如：

```html
{{- .Name -}}
```

**注意**：-要紧挨{{和}}，同时与模板值之间需要使用空格分隔。



#### 5、比较函数

布尔函数会将任何类型的零值视为假，其余视为真

下面是定义为函数的二元比较运算的集合：

| eq   | 如果 arg1 == arg2 则返回真 |
| ---- | -------------------------- |
| ne   | 如果 arg1 != arg2 则返回真 |
| lt   | 如果 arg1 < arg2 则返回真  |
| le   | 如果 arg1 <= arg2 则返回真 |
| gt   | 如果 arg1 > arg2 则返回真  |
| ge   | 如果 arg1 >= arg2 则返回真 |



#### 6、条件判断

Go 模板语法中的条件判断有以下几种:

```
{{if pipeline}} T1 {{end}}

{{if pipeline}} T1 {{else}} T0 {{end}}

{{if pipeline}} T1 {{else if pipeline}} T0 {{end}}

{{if gt .score 60}}
及格
{{else}}
不及格
{{end}}

{{if gt .score 90}}
优秀
{{else if gt .score 60}}
及格
{{else}}
不及格
{{end}
```



#### 6、range

Go 的模板语法中使用 range 关键字进行遍历，有以下两种写法，其中pipeline 的值必须是数组、切片、字典或者通道。

```html
{{range $key,$value := .obj}}
    {{$value}}
{{end}}
```

如果 pipeline 的值其长度为 0，不会有任何输出

```go
router.GET("/", func(c *gin.Context) {
    c.HTML(http.StatusOK, "default/index.html", map[string]interface{}{ 
        "hobby": []string{"吃饭", "睡觉", "写代码"}, })
})

{{range $key,$value := .hobby}}
<p>{{$value}}</p>
{{end}}
```



#### 7、With

```go
user := UserInfo{
		Name: "张三", 
		Gender: "男", 
		Age: 18}
	router.GET("/", func(c *gin.Context) {
		c.HTML(http.StatusOK, "default/index.html", map[string]interface{}{
			"user": user,
		})
	})
```

以前要输出数据：

```html
<h4>{{.user.Name}}</h4>
<h4>{{.user.Gender}}</h4>
<h4>{{.user.Age}}</h4>
```

现在要输出数据：

```html
{{with .user}}
    <h4>姓名：{{.Name}}</h4>
    <h4>性别：{{.user.Gender}}</h4>
    <h4>年龄：{{.Age}}</h4>
{{end}}
```

简单理解：相当于 var .=.user





#### 8、预定义函数 （了解）

执行模板时，函数从两个函数字典中查找：首先是模板函数字典，然后是全局函数字典。一般不在模板内定义函数，而是使用 Funcs 方法添加函数到模板里。

预定义的全局函数如下：

**and** 函数返回它的第一个 empty 参数或者最后一个参数； 就是说"and x y"等价于"if x then y else x"；所有参数都会执行；

**or** 返回第一个非 empty 参数或者最后一个参数； 亦即"or x y"等价于"if x then x else y"；所有参数都会执行；

**not** 返回它的单个参数的布尔值的否定

**en** 返回它的参数的整数类型长度

**index** 执行结果为第一个参数以剩下的参数为索引/键指向的值； 如"index x 1 2 3"返回 x[1][2][3]的值；每个被索引的主体必须是数组、切片或者字典。

**print** 即 fmt.Sprint

**printf** 即 fmt.Sprintf

**println** 即 fmt.Sprintln

**html** 返回与其参数的文本表示形式等效的转义 HTML。 这个函数在 html/template 中不可用。

**urlquery** 以适合嵌入到网址查询中的形式返回其参数的文本表示的转义值。这个函数在 html/template 中不可用。

**js** 返回与其参数的文本表示形式等效的转义 JavaScript。

**call** 执行结果是调用第一个参数的返回值，该参数必须是函数类型，其余参数作为调用该函数的参数；

​	如"call .X.Y 1 2"等价于 go 语言里的 dot.X.Y(1, 2)； 

​	其中 Y 是函数类型的字段或者字典的值，或者其他类似情况； 

​	call 的第一个参数的执行结果必须是函数类型的值（和预定义函数如print 明显不同）；

​	该函数类型值必须有 1 到 2 个返回值，如果有 2 个则后一个必须是error 接口类型；

​	如果有 2 个返回值的方法返回的 error 非 nil，模板执行会中断并返回给调用模板执行者该错误；

```go
{{len .title}}
{{index 
```



#### 9、自定义模板函数

```go
router.SetFuncMap(template.FuncMap{ 
    "formatDate": formatAsDate, 
})
```

```go
package main

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"net/http"
	"time"
)

func formatAsDate(t time.Time) string {
	year, month, day := t.Date()
	return fmt.Sprintf("%d/%02d/%02d", year, month, day)
}

func main() {
	router := gin.Default()
    
	//注册全局模板函数 注意顺序，注册模板函数需要在加载模板上面
    router.SetFuncMap(template.FuncMap{ "formatDate": formatAsDate, })
    
	//加载模板
	router.LoadHTMLGlob("templates/**/*")
    
	router.GET("/", func(c *gin.Context) {
		c.HTML(http.StatusOK, "default/index.html", map[string]interface{}{
            "title": "前台首页", 
            "now": time.Now()
        })
	})
	router.Run(":8080")
}

```

模板里面的用法

```go
{{.now | formatDate}}
或者
{{formatDate .now }}
```



### 5.4、嵌套 template

**1、新建 templates/deafult/page_header.html**

```html
{{ define "default/page_header.html" }}
    <h1>这是一个头部</h1>
{{end}}
```

**2、外部引入**

**注意：**

1、引入的名字为 page_header.html 中定义的名字 

2、引入的时候注意最后的点（.）

```html
{{template "default/page_header.html" .}}
```

```html
<!-- 相当于给模板定义一个名字 define end 成对出现-->
{{ define "default/index.html" }}
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Document</title>
    </head>
    <body>
        {{template "default/page_header.html" .}}
    </body>
    </html>
{{end}}
```



## 六、静态文件服务

当我们渲染的 HTML 文件中引用了静态文件时,我们需要配置静态 web 服务

r.Static("/static", "./static") 	前面的/static 表示路由 	后面的./static 表示路径

```go
func main() {
    r := gin.Default()
    r.Static("/static", "./static")
    r.LoadHTMLGlob("templates/**/*")
    // ... r.Run(":8080")
}
```

```html
<link rel="stylesheet" href="/static/css/base.css" />
```



## 七、路由详解

路由（Routing）是由一个 URI（或者叫路径）和一个特定的 HTTP 方法（GET、POST 等）组成的，涉及到应用如何响应客户端对某个网站节点的访问。



### 7.1、GET POST 以及获取 Get Post 传值



#### 7.1.1、Get 请求传值

```
GET /user?uid=20&page=1
```

```go
router.GET("/user", func(c *gin.Context) {
    uid := c.Query("uid")
    page := c.DefaultQuery("page", "0")
    c.String(200, "uid=%v page=%v", uid, page)
})
```



#### 7.1.2、动态路由传值

```
域名/user/20
```

```go
r.GET("/user/:uid", func(c *gin.Context) {
    uid := c.Param("uid")
    c.String(200, "userID=%s", uid)
})
```



#### 7.1.3、Post 请求传值 获取 form 表单数据

定义一个 add_user.html 的页面

```html
{{ define "default/add_user.html" }}
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Document</title>
    </head>
    <body>
        <form action="/doAddUser" method="post">
            用户名：<input type="text" name="username" />
            密码: <input type="password" name="password" />
            <input type="submit" value="提交">
        </form>
    </body>
    </html>
{{end}}
```

通过 c.PostForm 接收表单传过来的数据

```go
    router.GET("/addUser", func(c *gin.Context) {
            c.HTML(200, "default/add_user.html", gin.H{})
        })
	router.POST("/doAddUser", func(c *gin.Context) {
		username := c.PostForm("username")
		password := c.PostForm("password")
		age := c.DefaultPostForm("age", "20")
		c.JSON(200, gin.H{
            "usernmae": username, 
            "password": password, "age": age
        })
	})
```



#### 7.1.4、获取 GET POST 传递的数据绑定到结构体

为了能够更方便的获取请求相关参数，提高开发效率，我们可以基于请求的Content-Type识别请求数据类型并利用反射机制自动提取请求中 QueryString、form 表单、JSON、XML 等参数到结构体中。 下面的示例代码演示了.ShouldBind()强大的功能，它能够基于请求自动提取 JSON、form 表单和 QueryString 类型的数据，并把值绑定到指定的结构体对象。

```go
//注意首字母大写
type Userinfo struct {
	Username string `form:"username" json:"user"` 
    Password string `form:"password" json:"password"` 
}
```



**Get 传值绑定到结构体**

```powershell
/?username=zhangsan&password=123456
```

```go
router.GET("/", func(c *gin.Context) {
    var userinfo Userinfo
    if err := c.ShouldBind(&userinfo); err == nil {
        c.JSON(http.StatusOK, userinfo)
    } else {
        c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
    }
})
```

返回数据

```json
{"user":"zhangsan","password":"123456"}
```



**Post 传值绑定到结构体**

```go
router.POST("/doLogin", func(c *gin.Context) {
    var userinfo Userinfo
    if err := c.ShouldBind(&userinfo); err == nil {
        c.JSON(http.StatusOK, userinfo)
    } else {
        c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
	}
})
```

返回数据

```json
{"user":"zhangsan","password":"123456"}
```





#### 7.1.5、获取 Post Xml 数据

在 API 的开发中，我们经常会用到 JSON 或 XML 来作为数据交互的格式，这个时候我们可以在 gin 中使用 c.GetRawData()获取数据。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<article>
    <content type="string">我是张三</content>
    <title type="string">张三</title>
</article>
```

![image-20230805192727116](./Gin%20%E6%A1%86%E6%9E%B6.assets/image-20230805192727116.png)

```go
type Article struct {
    Title string `xml:"title"` 
    Content string `xml:"content"` 
}


router.POST("/xml", func(c *gin.Context) {
    b, _ := c.GetRawData() // 从 c.Request.Body 
    
    article := &Article{}
    
    if err := xml.Unmarshal(b, &article); err == nil {
        c.JSON(http.StatusOK, article)
    } else {
        c.JSON(http.StatusBadRequest, err.Error())
    }
})
```



### 7.2、简单的路由组

https://gin-gonic.com/zh-cn/docs/examples/grouping-routes/

```go
unc main() {
	router := gin.Default()
	// 简单的路由组: v1
	v1 := router.Group("/v1")
	{
		v1.POST("/login", loginEndpoint)
		v1.POST("/submit", submitEndpoint)
		v1.POST("/read", readEndpoint)
	}
	// 简单的路由组: v2
	v2 := router.Group("/v2")
	{
		v2.POST("/login", loginEndpoint)
		v2.POST("/submit", submitEndpoint)
		v2.POST("/read", readEndpoint)
	}
	router.Run(":8080")
}
```



### 7.3、Gin 路由文件 分组

7.3.1、新建 routes 文件夹，routes 文件下面新建 adminRoutes.go、apiRoutes.go、defaultRoutes.go

**1、新建 adminRoutes.go**

```go
package routes

import (
	"github.com/gin-gonic/gin"
	"net/http"
)

func AdminRoutesInit(router *gin.Engine) {
	adminRouter := router.Group("/admin")
	{
		adminRouter.GET("/user", func(c *gin.Context) {
			c.String(http.StatusOK, "用户")
		})
		adminRouter.GET("/news", func(c *gin.Context) {
			c.String(http.StatusOK, "news")
		})
	}
}

```

**2、新建 apiRoutes.go**

```go
package routes

import (
	"github.com/gin-gonic/gin"
	"net/http"
)

func ApiRoutesInit(router *gin.Engine) {
	apiRoute := router.Group("/api")
	{
		apiRoute.GET("/user", func(c *gin.Context) {
			c.JSON(http.StatusOK, gin.H{
                "username": "张三",
				"age": 20
            })
		})
		apiRoute.GET("/news", func(c *gin.Context) {
			c.JSON(http.StatusOK, gin.H{
                "title": "这是新闻"
            })
		})
	}
}

```



**3、新建 defaultRoutes.go**

```go
package routes

import (
	"github.com/gin-gonic/gin"
)

func DefaultRoutesInit(router *gin.Engine) {
	defaultRoute := router.Group("/")
	{
		defaultRoute.GET("/", func(c *gin.Context) {
			c.String(200, "首页")
		})
	}
}

```



**7.3.2 、配置 main.go**

```go
package main

import (
	"gin_demo/routes"
	"github.com/gin-gonic/gin"
)

//注意首字母大写
type Userinfo struct {
	Username string `form:"username" json:"user"` 
    Password string `form:"password" json:"password"`
}

func main() {
	r := gin.Default()
	routes.AdminRoutesInit(r)
	routes.ApiRoutesInit(r)
	routes.DefaultRoutesInit(r)
	r.Run(":8080")
}

```

访问 /api/user /admin/user 测试



## 八、Gin 中自定义控制器



### 8.1、控制器分组

当我们的项目比较大的时候有必要对我们的控制器进行分组

**新建 controller/admin/NewsController.go**

```go
package admin

import (
	"github.com/gin-gonic/gin"
	"net/http"
)

type NewsController struct {
}

func (c NewsController) Index(ctx *gin.Context) {
	ctx.String(http.StatusOK, "新闻首页")
}

```



**新建 controller/admin/UserController.go**

```go
package admin

import (
	"github.com/gin-gonic/gin"
	"net/http"
)

type UserController struct {
}

func (c UserController) Index(ctx *gin.Context) {
	ctx.String(http.StatusOK, "这是用户首页")
}
func (c UserController) Add(ctx *gin.Context) {
	ctx.String(http.StatusOK, "增加用户")
}

```

....



**配置对应的路由 --adminRoutes.go**

其他路由的配置方法类似

```go
package routes

import (
	"gin_demo/controller/admin"
	"github.com/gin-gonic/gin"
)

func AdminRoutesInit(router *gin.Engine) {
	adminRouter := router.Group("/admin")
	{
		adminRouter.GET("/user", admin.UserController{}.Index)
		adminRouter.GET("/user/add", admin.UserController{}.Add)
		adminRouter.GET("/news", admin.NewsController{}.Add)
	}
}

```



### 8.2、控制器的继承

**1、新建 controller/admin/BaseController.go**

```go
package admin

import (
	"github.com/gin-gonic/gin"
	"net/http"
)

type BaseController struct {
}

func (c BaseController) Success(ctx *gin.Context) {
	ctx.String(http.StatusOK, "成功")
}
func (c BaseController) Error(ctx *gin.Context) {
	ctx.String(http.StatusOK, "失败")
}
```



**2、NewsController 继承 BaseController**

继承后就可以调用控制器里面的公共方法了

```go
package admin

import (
	"github.com/gin-gonic/gin"
)

type NewsController struct {
	BaseController
}

func (c NewsController) Index(ctx *gin.Context) {
	c.Success(ctx)
}

```



## 九、Gin 中间件

Gin 框架允许开发者在处理请求的过程中，加入用户自己的钩子（Hook）函数。这个钩子函数就叫中间件，中间件适合处理一些公共的业务逻辑，比如登录认证、权限校验、数据分页、记录日志、耗时统计等。

**通俗的讲**：中间件就是匹配路由前和匹配路由完成后执行的一系列操作



### 9.1、路由中间件

#### 9.1.1、初识中间件

Gin 中的中间件必须是一个 gin.HandlerFunc 类型，配置路由的时候可以传递多个func 回调函数，最后一个 func 回调函数前面触发的方法都可以称为中间件。

```go
package main

import (
	"fmt"
	"github.com/gin-gonic/gin"
)

func initMiddleware(ctx *gin.Context) {
	fmt.Println("我是一个中间件")
}
func main() {
	r := gin.Default()
	r.GET("/", initMiddleware, func(ctx *gin.Context) {
		ctx.String(200, "首页--中间件演示")
	})
	r.GET("/news", initMiddleware, func(ctx *gin.Context) {
		ctx.String(200, "新闻页面--中间件演示")
	})
	r.Run(":8080")
}

```



#### 9.1.2、ctx.Next()

调用该请求的剩余处理程序

中间件里面加上 ctx.Next()可以让我们在路由匹配完成后执行一些操作。比如我们统计一个请求的执行时间。

```go
package main

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"time"
)

func initMiddleware(ctx *gin.Context) {
	fmt.Println("1-执行中中间件")
	start := time.Now().UnixNano()
	// 调用该请求的剩余处理程序
	ctx.Next()
	fmt.Println("3-程序执行完成 计算时间")
	// 计算耗时 Go 语言中的 Since()函数保留时间值，并用于评估与实际时间的差异
	end := time.Now().UnixNano()
	fmt.Println(end - start)
}

func main() {
	r := gin.Default()
	r.GET("/", initMiddleware, func(ctx *gin.Context) {
		fmt.Println("2-执行首页返回数据")
		ctx.String(200, "首页--中间件演示")
	})
	r.GET("/news", initMiddleware, func(ctx *gin.Context) {
		ctx.String(200, "新闻页面--中间件演示")
	})
	r.Run(":8080")
}

```



#### 9.1.3、一个路由配置多个中间件的执行顺序

```go
package main

import (
	"fmt"
	"github.com/gin-gonic/gin"
)

func initMiddlewareOne(ctx *gin.Context) {
	fmt.Println("initMiddlewareOne--1-执行中中间件")
	// 调用该请求的剩余处理程序
	ctx.Next()
	fmt.Println("initMiddlewareOne--2-执行中中间件")
}

func initMiddlewareTwo(ctx *gin.Context) {
	fmt.Println("initMiddlewareTwo--1-执行中中间件")
	// 调用该请求的剩余处理程序
	ctx.Next()
	fmt.Println("initMiddlewareTwo--2-执行中中间件")
}

func main() {
	r := gin.Default()
	r.GET("/", initMiddlewareOne, initMiddlewareTwo, func(ctx *gin.Context) {
		fmt.Println("执行路由里面的程序")
		ctx.String(200, "首页--中间件演示")
	})
	r.Run(":8080")
}

```

**控制台内容：**

```go
initMiddlewareOne--1-执行中中间件
initMiddlewareTwo--1-执行中中间件
执行路由里面的程序
initMiddlewareTwo--2-执行中中间件
initMiddlewareOne--2-执行中中间件
```



#### 9.1.4、 c.Abort()--（了解）

Abort 是终止的意思， c.Abort() 表示终止调用该请求的剩余处理程序

```go
package main

import (
	"fmt"
	"github.com/gin-gonic/gin"
)

func initMiddlewareOne(ctx *gin.Context) {
	fmt.Println("initMiddlewareOne--1-执行中中间件")
	// 调用该请求的剩余处理程序
	ctx.Next()
	fmt.Println("initMiddlewareOne--2-执行中中间件")
}

func initMiddlewareTwo(ctx *gin.Context) {
	fmt.Println("initMiddlewareTwo--1-执行中中间件")
	// 终止调用该请求的剩余处理程序
	ctx.Abort()
	fmt.Println("initMiddlewareTwo--2-执行中中间件")
}

func main() {
	r := gin.Default()
	r.GET("/", initMiddlewareOne, initMiddlewareTwo, func(ctx *gin.Context) {
		fmt.Println("执行路由里面的程序")
		ctx.String(200, "首页--中间件演示")
	})
	r.Run(":8080")
}

```

```go
initMiddlewareOne--1-执行中间件
initMiddlewareTwo--1-执行中间件
initMiddlewareTwo--2-执行中间件
initMiddlewareOne--2-执行中间件
```



### 9.2、全局中间件

```go
package main

import (
	"fmt"
	"github.com/gin-gonic/gin"
)

func initMiddleware(ctx *gin.Context) {
	fmt.Println("全局中间件 通过 r.Use 配置")
	// 调用该请求的剩余处理程序
	ctx.Next()
}

func main() {
	r := gin.Default()
	r.Use(initMiddleware)
	r.GET("/", func(ctx *gin.Context) {
		ctx.String(200, "首页--中间件演示")
	})
	r.GET("/news", func(ctx *gin.Context) {
		ctx.String(200, "新闻页面--中间件演示")
	})
	r.Run(":8080")
}

```



### 9.3、在路由分组中配置中间件

**1、为路由组注册中间件有以下两种写法**



写法 1：

```go
shopGroup := r.Group("/shop", StatCost())
{
    shopGroup.GET("/index", func(c *gin.Context) {...})
    ... 
}
```



写法 2：

```go
shopGroup := r.Group("/shop")
shopGroup.Use(StatCost())
{
    shopGroup.GET("/index", func(c *gin.Context) {...})
    ... 
}
```



**2、分组路由 AdminRoutes.go 中配置中间件**

```go
package routes

import (
	"fmt"
	"gin_demo/controller/admin"
	"github.com/gin-gonic/gin"
	"net/http"
)

func initMiddleware(ctx *gin.Context) {
	fmt.Println("路由分组中间件")
	// 调用该请求的剩余处理程序
	ctx.Next()
}
func AdminRoutesInit(router *gin.Engine) {
	adminRouter := router.Group("/admin", initMiddleware)
	{
		adminRouter.GET("/user", admin.UserController{}.Index)
		adminRouter.GET("/user/add", admin.UserController{}.Add)
		adminRouter.GET("/news", func(c *gin.Context) {
			c.String(http.StatusOK, "news")
		})
	}
}

```



### 9.4、中间件和对应控制器之间共享数据

设置值

```go
ctx.Set("username", "张三")
```

获取值

```go
username, _ := ctx.Get("username")
```

中间件设置值

```go
package main

import (
	"fmt"
	"github.com/gin-gonic/gin"
)

func InitAdminMiddleware(ctx *gin.Context) {
	fmt.Println("路由分组中间件")
	// 可以通过 ctx.Set 在请求上下文中设置值，后续的处理函数能够取到该值
    ctx.Set("username", "张三")
	// 调用该请求的剩余处理程序
	ctx.Next()
}

```

控制器获取值

```go
func (c UserController) Index(ctx *gin.Context) {
    username, _ := ctx.Get("username")
    fmt.Println(username)
    ctx.String(http.StatusOK, "这是用户首页 111")
}
```



### 9.5、中间件注意事项

**gin 默认中间件**

gin.Default()默认使用了 Logger 和 Recovery 中间件，其中：

• Logger 中间件将日志写入 gin.DefaultWriter，即使配置了 GIN_MODE=release。

• Recovery 中间件会 recover 任何 panic。如果有 panic 的话，会写入500 响应码。

如果不想使用上面两个默认的中间件，可以使用 gin.New()新建一个没有任何默认中间件的路由



**gin 中间件中使用 goroutine**

当在中间件或 handler 中启动新的 goroutine 时，不能使用原始的上下文（c *gin.Context），必须使用其只读副本（c.Copy()）

```go
r.GET("/", func(c *gin.Context) {
    cCp := c.Copy()
    go func() {
        // simulate a long task with time.Sleep(). 5 seconds
        time.Sleep(5 * time.Second)
        
        // 这里使用你创建的副本
        fmt.Println("Done! in path " + cCp.Request.URL.Path)
    }()
    c.String(200, "首页")
})
```



## 十、Gin 中自定义Model



### 10.1、关于 Model

如果我们的应用非常简单的话，我们可以在 Controller 里面处理常见的业务逻辑。但是如果我们有一个功能想在多个控制器、或者多个模板里面复用的话，那么我们就可以把公共的功能单独抽取出来作为一个模块（Model）。 Model 是逐步抽象的过程，一般我们会在Model 里面封装一些公共的方法让不同 Controller 使用，也可以在 Model 中实现和数据库打交道



### 10.2、Model 里面封装公共的方法

**1、新建 models/ tools.go**

```go
package models

import (
	"time"
)

//时间戳转换成日期
func UnixToTime(timestamp int) string {
	t := time.Unix(int64(timestamp), 0)
	return t.Format("2006-01-02 15:04:05")
}

//日期转换成时间戳 2020-05-02 15:04:05
func DateToUnix(str string) int64 {
	template := "2006-01-02 15:04:05"
	t, err := time.ParseInLocation(template, str, time.Local)
	if err != nil {
		return 0
	}
	return t.Unix()
}

//获取时间戳
func GetUnix() int64 {
	return time.Now().Unix()
}

//获取当前的日期
func GetDate() string {
	template := "2006-01-02 15:04:05"
	return time.Now().Format(template)
}

//获取年月日
func GetDay() string {
	template := "20060102"
	return time.Now().Format(template)
}

```



### 10.3、控制器中调用 Model

```go
package controllers
import ( 
    "gin_demo/models"
)

day := models.GetDay()
```



### 10.4、调用 Model 注册全局模板函数

**models/tools.go**

//时间戳间戳转换成日期

```go
func UnixToDate(timestamp int64) string {
    t := time.Unix(timestamp, 0)
    return t.Format("2006-01-02 15:04:05")
}
```

**main.go**

//注册全局模板函数 注意顺序，注册模板函数需要在加载模板上面

```go
r := gin.Default()
r.SetFuncMap(template.FuncMap{ 
    "unixToDate": models.UnixToDate, 
})
```

**控制器**

```go
func (c UserController) Add(ctx *gin.Context) {
    ctx.HTML(http.StatusOK, "admin/user/add.html", gin.H{ 
        "now": models.GetUnix(), 
    })
}
```

**模板**

```html
<h2>{{.now | unixToDate}}</h2>
```



### 10.5、Golang Md5 加密

打开 golang 包对应的网站：https://pkg.go.dev/，搜索 md5

方法一：

```go
data := []byte("123456")
has := md5.Sum(data)
md5str := fmt.Sprintf("%x", has)
fmt.Println(md5str)
```

方法二：

```go
h := md5.New()
io.WriteString(h, "123456")
fmt.Printf("%x\n", h.Sum(nil))
```





## 十一、Gin 文件上传

注意：需要在上传文件的 form 表单上面需要加入 enctype="multipart/form-data"



### 11.1、单文件上传

https://gin-gonic.com/zh-cn/docs/examples/upload-file/single-file/

**官方示例：**

```go
package models

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"log"
	"net/http"
)

func main() {
	router := gin.Default()
	// 为 multipart forms 设置较低的内存限制 (默认是 32 MiB)
	router.MaxMultipartMemory = 8 << 20 // 8 MiB
	router.POST("/upload", func(c *gin.Context) {
		// 单文件
		file, _ := c.FormFile("file")
		log.Println(file.Filename)
		// 上传文件至指定目录
		c.SaveUploadedFile(file, dst)
		c.String(http.StatusOK, fmt.Sprintf("'%s' uploaded!", file.Filename))
	})
	router.Run(":8080")
}

```



**项目中实现文件上传**

**1、定义模板 需要在上传文件的 form 表单上面需要加入 enctype="multipart/form-data"**

```html
<!-- 相当于给模板定义一个名字 define end 成对出现-->
{{ define "admin/user/add.html" }}
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Document</title>
    </head>
    <body>
        <form action="/admin/user/doAdd" method="post" enctype="multipart/form-data">
            用户名： <input type="text" name="username" placeholder="用户名"> 
            <br> 
            <br>
            头 像：<input type="file" name="face"><br> <br>
        	<input type="submit" value="提交">
        </form>
    </body>
    </html>
{{ end }}
```



**2、定义业务逻辑**

```go
package models

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"net/http"
	"path"
)

func (c UserController) DoAdd(ctx *gin.Context) {
	username := ctx.PostForm("username")
	file, err := ctx.FormFile("face")
	if err != nil {
		ctx.JSON(http.StatusInternalServerError, gin.H{
            "message": err.Error()
        })
		return
	}
    
	// 上传文件到指定的目录
	dst := path.Join("./static/upload", file.Filename)
	fmt.Println(dst)
	ctx.SaveUploadedFile(file, dst)
	ctx.JSON(http.StatusOK, gin.H{
        "message": fmt.Sprintf("'%s' uploaded!", file.Filename), 
        "username": username
    })
}
```



### 11.2、多文件上传--不同名字的多个文件

**1、定义模板 需要在上传文件的 form 表单上面需要加入 enctype="multipart/form-data"**

```html
<!-- 相当于给模板定义一个名字 define end 成对出现-->
{{ define "admin/user/add.html" }}
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Document</title>
    </head>
    <body>
        <form action="/admin/user/doAdd" method="post" enctype="multipart/form-data">
            用户名： <input type="text" name="username" placeholder="用户名">
            <br> 
            <br>
            头 像 1：<input type="file" name="face1">
            <br> 
            <br>
            头 像 2：<input type="file" name="face2">
            <br> 
            <br>
            <input type="submit" value="提交">
        </form>
    </body>
    </html>
{{ end }}
```

**2、定义业务逻辑**

```go
package models

import (
	"github.com/gin-gonic/gin"
	"net/http"
	"path"
)

func (c UserController) DoAdd(ctx *gin.Context) {
	username := ctx.PostForm("username")
	face1, err1 := ctx.FormFile("face1")
	face2, err2 := ctx.FormFile("face2")
	// 上传文件到指定的目录
	if err1 == nil {
		dst1 := path.Join("./static/upload", face1.Filename)
		ctx.SaveUploadedFile(face1, dst1)
	}
	if err2 == nil {
		dst2 := path.Join("./static/upload", face2.Filename)
		ctx.SaveUploadedFile(face2, dst2)
	}
	ctx.JSON(http.StatusOK, gin.H{
		"message": "文件上传成功", "username": username})
	// ctx.String(200, username)
}

```



### 11.3、多文件上传--相同名字的多个文件

参考：https://gin-gonic.com/zh-cn/docs/examples/upload-file/multiple-file/

**1、定义模板 需要在上传文件的 form 表单上面需要加入 enctype="multipart/form-data"**

```html
<!-- 相当于给模板定义一个名字 define end 成对出现-->
{{ define "admin/user/add.html" }}
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Document</title>
    </head>
    <body>
        <form action="/admin/user/doAdd" method="post" enctype="multipart/form-data">
            用户名： <input type="text" name="username" placeholder="用户名"> 
			<br> 
			<br>
			头 像 1：<input type="file" name="face[]">
            <br> 
            <br>
            头 像 2：<input type="file" name="face[]">
            <br> 
            <br>
            <input type="submit" value="提交">
        </form>
    </body>
    </html>
{{ end }}
```

**2、定义业务逻辑**

```go
package models

import (
	"github.com/gin-gonic/gin"
	"net/http"
	"path"
)

func (c UserController) DoAdd(ctx *gin.Context) {
	username := ctx.PostForm("username")
    
	// Multipart form
	form, _ := ctx.MultipartForm()
	files := form.File["face[]"]
    
	// var dst;
	for _, file := range files {
		// 上传文件至指定目录
		dst := path.Join("./static/upload", file.Filename)
		ctx.SaveUploadedFile(file, dst)
	}
	ctx.JSON(http.StatusOK, gin.H{
        "message": "文件上传成功", 
        "username": username
    })
}

```





### 11.4、文件上传 按照日期存储

**1、定义模板 需要在上传文件的 form 表单上面需要加入 enctype="multipart/form-data"**

```html
<!-- 相当于给模板定义一个名字 define end 成对出现-->
{{ define "admin/user/add.html" }}
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Document</title>
    </head>
    <body>
        <form action="/admin/user/doAdd" method="post" enctype="multipart/form-data">
            用户名： <input type="text" name="username" placeholder="用户名"> 
            <br> 
            <br>
            头 像： <input type="file" name="face">
            <br> 
            <br>
            <input type="submit" value="提交">
        </form>
    </body>
    </html>
{{ end }}
```

**2、定义业务逻辑**

```go
package main

import (
	"github.com/gin-gonic/gin"
	"net/http"
	"os"
	"path"
	"strconv"
)

func (c UserController) DoAdd(ctx *gin.Context) {
	username := ctx.PostForm("username")
	//1、获取上传的文件
	file, err1 := ctx.FormFile("face")
	if err1 == nil {
		//2、获取后缀名 判断类型是否正确 .jpg .png .gif .jpeg
		extName := path.Ext(file.Filename)
		allowExtMap := map[string]bool{".jpg": true, ".png": true, ".gif": true, ".jpeg": true}
		if _, ok := allowExtMap[extName]; !ok {
			ctx.String(200, "文件类型不合法")
			return
		}
		//3、创建图片保存目录 static/upload/20200623
		day := models.GetDay()
		dir := "./static/upload/" + day
		if err := os.MkdirAll(dir, 0666); err != nil {
			log.Error(err)
		}
		//4、生成文件名称 144325235235.png
		fileUnixName := strconv.FormatInt(models.GetUnix(), 10)
		//static/upload/20200623/144325235235.png
		saveDir := path.Join(dir, fileUnixName+extName)
		ctx.SaveUploadedFile(file, saveDir)
	}
	ctx.JSON(http.StatusOK, gin.H{"message": "文件上传成功", "username": username})
	// ctx.String(200, username)
}
```



**3、models/tools.go**

```go
package models

import (
	"time"
)

//时间戳转换成日期
func UnixToTime(timestamp int) string {
	t := time.Unix(int64(timestamp), 0)
	return t.Format("2006-01-02 15:04:05")
}

//日期转换成时间戳 2020-05-02 15:04:05
func DateToUnix(str string) int64 {
	template := "2006-01-02 15:04:05"
	t, err := time.ParseInLocation(template, str, time.Local)
	if err != nil {
		return 0
	}
	return t.Unix()
}

//获取时间戳
func GetUnix() int64 {
	return time.Now().Unix()
}

//获取当前的日期
func GetDate() string {
	template := "2006-01-02 15:04:05"
	return time.Now().Format(template)
}

//获取年月日
func GetDay() string {
	template := "20060102"
	return time.Now().Format(template)
}

```



## 十二、Gin 中的Cookie



### 12.1、Cookie 介绍

● HTTP 是无状态协议。简单地说，当你浏览了一个页面，然后转到同一个网站的另一个页面，服务器无法认识到这是同一个浏览器在访问同一个网站。每一次的访问，都是没有任何关系的。如果我们要实现多个页面之间共享数据的话我们就可以使用Cookie 或者Session实现

● cookie 是存储于访问者计算机的浏览器中。可以让我们用同一个浏览器访问同一个域名的时候共享数据。



### 12.2、Cookie 能实现的功能

1、保持用户登录状态 

2、保存用户浏览的历史记录 

3、猜你喜欢，智能推荐 

4、电商网站的加入购物车



### 12.3、设置和获取 Cookie

https://gin-gonic.com/zh-cn/docs/examples/cookie/

**设置 Cookie**

```go
c.SetCookie(name, value string, maxAge int, path, domain string, secure, httpOnly bool)
```

第一个参数 key 

第二个参数 value 

第三个参数 过期时间.如果只想设置 Cookie 的保存路径而不想设置存活时间，可以在第三个参数中传递 nil 

四个参数 cookie 的路径 

第五个参数 cookie 的路径 Domain 作用域 本地调试配置成 localhost , 正式上线配置成域名

第六个参数是 secure ，当 secure 值为 true 时，cookie 在 HTTP 中是无效，在HTTPS 中才有效 

第七个参数 httpOnly，是微软对 COOKIE 做的扩展。如果在 COOKIE 中设置了“httpOnly”属性，则通过程序（JS 脚本、applet 等）将无法读取到 COOKIE 信息，防止XSS 攻击产生



**获取 Cookie**

```go
cookie, err := c.Cookie("name")
```



**完整 demo**

```go
package main

import (
	"gin_demo/models"
	"github.com/gin-gonic/gin"
	"html/template"
)

func main() {
	r := gin.Default()
	r.SetFuncMap(template.FuncMap{"unixToDate": models.UnixToDate})
	r.GET("/", func(c *gin.Context) {
		c.SetCookie("usrename", "张三", 3600, "/", "localhost", false, true)
		c.String(200, "首页")
	})
	r.GET("/user", func(c *gin.Context) {
		username, _ := c.Cookie("usrename")
		c.String(200, "用户-"+username)
	})
	r.Run(":8080")
}

```



### 12.4 、多个二级域名共享 cookie

1、分别把 a.itying.com 和 b.itying.com 解析到我们的服务器

2、我们想的是用户在 a.itying.com 中设置 Cookie 信息后在 b.itying.com 中获取刚才设置的cookie，也就是实现多个二级域名共享 cookie

这时候的话我们就可以这样设置 cookie

```go
c.SetCookie("usrename", "张三", 3600, "/", ".itying.com", false, true)
```





## 十三、Gin 中的Session



### 13.1、Session 简单介绍

session 是另一种记录客户状态的机制，不同的是 Cookie 保存在客户端浏览器中，而session保存在服务器上。



### 13.2、Session 的工作流程

当客户端浏览器第一次访问服务器并发送请求时，服务器端会创建一个session 对象，生成一个类似于 key,value 的键值对，然后将 value 保存到服务器 将 key(cookie)返回到浏览器(客户)端。浏览器下次访问时会携带 key(cookie)，找到对应的 session(value).



### 13.3、Gin 中使用 Session

Gin 官方没有给我们提供 Session 相关的文档，这个时候我们可以使用第三方的Session 中间件来实现

https://github.com/gin-contrib/sessions



gin-contrib/sessions 中间件支持的存储引擎：

• cookie 

• memstore 

• redis 

• memcached 

• mongodb



### 13.4、基于 Cookie 存储 Session

1、安装 session 包

```go
go get github.com/gin-contrib/sessions
```

2、基本的 session 用法

```go
package main

import (
	"github.com/gin-contrib/sessions"
	"github.com/gin-gonic/gin"
)

func main() {
	r := gin.Default()
	// 创建基于 cookie 的存储引擎，secret11111 参数是用于加密的密钥store := cookie.NewStore([]byte("secret11111"))
	// 设置 session 中间件，参数 mysession，指的是 session 的名字，也是cookie 的名字// store 是前面创建的存储引擎，我们可以替换成其他存储引擎r.Use(sessions.Sessions("mysession", store))
	r.GET("/", func(c *gin.Context) {
		//初始化 session 对象
		session := sessions.Default(c)
		//设置过期时间
		session.Options(sessions.Options{
			MaxAge: 3600 * 6, // 6hrs
		})
		//设置 Session
		session.Set("username", "张三")
		session.Save()
		c.JSON(200, gin.H{"msg": session.Get("username")})
	})
	r.GET("/user", func(c *gin.Context) {
		// 初始化 session 对象
		session := sessions.Default(c)
		// 通过 session.Get 读取 session 值
		username := session.Get("username")
		c.JSON(200, gin.H{"username": username})
	})
	r.Run(":8000")
}

```



### 13.5、基于 Redis 存储 Session

如果我们想将 session 数据保存到 redis 中，只要将 session 的存储引擎改成redis 即可。

使用 redis 作为存储引擎的例子：

首先安装 redis 存储引擎的包

```go
go get github.com/gin-contrib/sessions/redis
```

例子：

```go
package main

import (
	"github.com/gin-contrib/sessions"
	"github.com/gin-contrib/sessions/redis"
	"github.com/gin-gonic/gin"
)

func main() {
	r := gin.Default()
	// 初始化基于 redis 的存储引擎
	// 参数说明：
	// 第 1 个参数 - redis 最大的空闲连接数
	// 第 2 个参数 - 数通信协议 tcp 或者 udp
	// 第 3 个参数 - redis 地址, 格式，host:port
	// 第 4 个参数 - redis 密码
	// 第 5 个参数 - session 加密密钥
	store, _ := redis.NewStore(10, "tcp", "localhost:6379", "", []byte("secret"))
	r.Use(sessions.Sessions("mysession", store))
	r.GET("/", func(c *gin.Context) {
		session := sessions.Default(c)
		session.Set("username", "李四")
		session.Save()
		c.JSON(200, gin.H{"username": session.Get("username")})
	})
	r.GET("/user", func(c *gin.Context) {
		// 初始化 session 对象
		session := sessions.Default(c)
		// 通过 session.Get 读取 session 值
		username := session.Get("username")
		c.JSON(200, gin.H{"username": username})
	})
	r.Run(":8000")
}

```



## 十四、Gin 中使用GORM操作mysql 数据库



### 14.1、GORM 简单介绍

GORM 是 Golang 的一个 orm 框架。简单说，ORM 就是通过实例对象的语法，完成关系型数据库的操作的技术，是"对象-关系映射"（Object/Relational Mapping）的缩写。使用ORM框架可以让我们更方便的操作数据库。

**GORM 官方支持的数据库类型有： MySQL, PostgreSQL, SQlite, SQL Server**

![image-20230810214800600](./Gin%20%E6%A1%86%E6%9E%B6.assets/image-20230810214800600.png)

**特性**

- 全功能 ORM
- 关联 (Has One，Has Many，Belongs To，Many To Many，多态，单表继承)
- Create，Save，Update，Delete，Find 中钩子方法
- 支持 `Preload`、`Joins` 的预加载
- 事务，嵌套事务，Save Point，Rollback To Saved Point
- Context、预编译模式、DryRun 模式
- 批量插入，FindInBatches，Find/Create with Map，使用 SQL 表达式、Context Valuer 进行 CRUD
- SQL 构建器，Upsert，数据库锁，Optimizer/Index/Comment Hint，命名参数，子查询
- 复合主键，索引，约束
- Auto Migration
- 自定义 Logger
- 灵活的可扩展插件 API：Database Resolver（多数据库，读写分离）、Prometheus…
- 每个特性都经过了测试的重重考验
- 开发者友好

官方文档：https://gorm.io/zh_CN/docs/index.html



### 14.2、Gin 中使用 GORM

#### **1、安装**

如果使用 go mod 管理项目的话可以忽略此步骤

```go
go get -u gorm.io/gorm
go get -u gorm.io/driver/mysql
```



#### **2、Gin 中使用 Gorm 连接数据库**

在 models 下面新建 core.go ，建立数据库链接

```go
package models

import (
	"fmt"
	"gorm.io/gorm"
)

var DB *gorm.DB
var err error

func init() {
	dsn := "root:123456@tcp(192.168.0.6:3306)/gin?charset=utf8mb4&parseTime=True&loc=L
	ocal
	"DB, err = gorm.Open(mysql.Open(dsn), &gorm.Config{})
	if err != nil {
		fmt.Println(err)
	}
}

```



#### **3、定义操作数据库的模型**

Gorm 官方给我们提供了详细的：

https://gorm.io/zh_CN/docs/models.html

虽然在 gorm 中可以指定字段的类型以及自动生成数据表，但是在实际的项目开发中，我们是先设计数据库表，然后去实现编码的。



**在实际项目中定义数据库模型注意以下几点：**

**1、结构体的名称必须首字母大写** ，并和数据库表名称对应。例如：表名称为user 结构体名称定义成 User，表名称为 article_cate 结构体名称定义成 ArticleCate

2、结构体中的**字段名称首字母必须大写**，并和数据库表中的字段一一对应。例如：下面结构体中的 Id 和数据库中的 id 对应,Username 和数据库中的 username 对应，Age 和数据库中的 age 对应，Email 和数据库中的 email 对应，AddTime 和数据库中的add_time 字段对应

3、**默认情况表名是结构体名称的复数形式**。如果我们的结构体名称定义成User，表示这个模型默认操作的是 users 表。

4、我们可以使用结构体中的自定义方法 TableName 改变结构体的默认表名称，如下:

```go
func (User) TableName() string {
    return "user"
}
```

表示把 User 结构体默认操作的表改为 user 表



**定义 user 模型：**

```go
package models

type User struct { // 默认表名是 `users`
	Id       int
	Username string
	Age      int
	Email    string
	AddTime  int
}

func (User) TableName() string {
	return "user"
}

```

**关于更多模型定义的方法参考：https://gorm.io/zh_CN/docs/conventions.html**



**gorm.Model**

GORM 定义一个 gorm.Model 结构体，其包括字段 ID、CreatedAt、UpdatedAt、DeletedAt

// gorm.Model 的定义

```go
type Model struct {
	ID uint `gorm:"primaryKey"` 
    CreatedAt time.Time
    UpdatedAt time.Time
    DeletedAt gorm.DeletedAt `gorm:"index"` 
}
```



### 14.3、Gin GORM CURD

找到要操作数据库表的控制器，然后引入 models 模块



#### **1、增加**

增加成功后会返回刚才增加的记录

```go
func (con UserController) Add(c *gin.Context) {
    user := models.User{
        Username: "itying.com", 
        Age: 18, 
        Email: "itying@qq.com", 
        AddTime: int(time.Now().Unix()), 
    }
    
    result := models.DB.Create(&user) // 通过数据的指针来创建
    
    if result.RowsAffected > 1 {
        fmt.Print(user.Id)
    }
    
    fmt.Println(result.RowsAffected)
    fmt.Println(user.Id)
    c.String(http.StatusOK, "add 成功")
}
```

更多增加语句:https://gorm.io/zh_CN/docs/create.html



#### **2、查找**

查找全部

```go
func (con UserController) Index(c *gin.Context) {
    user := []models.User{}
    models.DB.Find(&user)
    c.JSON(http.StatusOK, gin.H{ 
        "success": true, 
        "result": user, 
    })
}
```

指定条件查找

```go
func (con UserController) Index(c *gin.Context) {
    user := []models.User{}
    models.DB.Where("username=?", "王五").Find(&user)
    c.JSON(http.StatusOK, gin.H{ 
        "success": true, 
        "result": user, 
    })
}
```

更多查询语句：https://gorm.io/zh_CN/docs/query.html



#### 3、修改

```go
func (con UserController) Edit(c *gin.Context) {
    user := models.User{Id: 7}
    models.DB.Find(&user)
    user.Username = "gin gorm" 
    user.Age = 1
    models.DB.Save(&user)
    c.String(http.StatusOK, "Edit")
}
```

更多修改的方法参考：https://gorm.io/zh_CN/docs/update.html



#### 4、删除

```go
func (con UserController) Delete(c *gin.Context) {
    user := models.User{Id: 8}
    models.DB.Delete(&user)
    c.String(http.StatusOK, "Delete")
}
```

更多删除的方法参考:https://gorm.io/zh_CN/docs/delete.html



#### 5、批量删除

```go
db.Where("email LIKE ?", "%jinzhu%").Delete(Email{})
// DELETE from emails where email LIKE "%jinzhu%";

db.Delete(Email{}, "email LIKE ?", "%jinzhu%")
// DELETE from emails where email LIKE "%jinzhu%";

func (con UserController) DeleteAll(c *gin.Context) {
    user := models.User{}
    models.DB.Where("id>9").Delete(&user)
    c.String(http.StatusOK, "DeleteAll")
}
```

更多删除的方法参考:https://gorm.io/zh_CN/docs/delete.html



### 14.4、Gin GORM 查询语句详解

https://gorm.io/zh_CN/docs/query.html

1、Where 

= 

< 

<= 

\>= 

!= 

IS 

NOT NULL 

IS NULL 

BETWEEN AND 

NOT BETWEEN AND 

IN 

OR 

AND 

NOT 

LIKE

```go
nav := []models.Nav{}
models.DB.Where("id<3").Find(&nav)
c.JSON(http.StatusOK, gin.H{ 
    "success": true, 
    "result": nav, })

var n = 5
nav := []models.Nav{}
models.DB.Where("id>?", n).Find(&nav)
c.JSON(http.StatusOK, gin.H{ 
    "success": true, 
    "result": nav, 
})
```

```go
var n1 = 3
var n2 = 9
nav := []models.Nav{}
models.DB.Where("id > ? AND id < ?", n1, n2).Find(&nav)
c.JSON(http.StatusOK, gin.H{ 
    "success": true, 
    "result": nav, 
})
```

```go
nav := []models.Nav{}
models.DB.Where("id in (?)", []int{3, 5, 6}).Find(&nav)
c.JSON(http.StatusOK, gin.H{ 
    "success": true, 
    "result": nav, 
})
```

```go
nav := []models.Nav{}
models.DB.Where("title like ?", "%会%").Find(&nav)
c.JSON(http.StatusOK, gin.H{ 
    "success": true, 
    "result": nav, 
})
```

```go
nav := []models.Nav{}
models.DB.Where("id between ? and ?", 3, 6).Find(&nav)
c.JSON(http.StatusOK, gin.H{ 
    "success": true, 
    "result": nav, 
})
```



#### 2、Or 条件

```go
nav := []models.Nav{}
models.DB.Where("id=? OR id=?", 2, 3).Find(&nav)
```

```go
nav := []models.Nav{}
models.DB.Where("id=?", 2).Or("id=?", 3).Or("id=4").Find(&nav)
```



#### 3、选择字段查询

```go
nav := []models.Nav{}
models.DB.Select("id, title,url").Find(&nav)
```



#### 4、排序 Limit 、Offset

```go
nav := []models.Nav{}
models.DB.Where("id>2").Order("id Asc").Find(&nav)

nav := []models.Nav{}
models.DB.Where("id>2").Order("sort Desc").Order("id Asc").Find(&nav)

nav := []models.Nav{}
odels.DB.Where("id>1").Limit(2).Find(&nav)
```

**跳过 2 条查询 2 条**

```go
nav := []models.Nav{}
models.DB.Where("id>1").Offset(2).Limit(2).Find(&nav)
```



#### 5、获取总数

```go
nav := []models.Nav{}
var num int
models.DB.Where("id > ?", 2).Find(&nav).Count(&num)
```



#### 6、Distinct

从模型中选择不相同的值

```go
nav := []models.Nav{}
models.DB.Distinct("title").Order("id desc").Find(&nav)
c.JSON(200, gin.H{ 
    "nav": nav, 
})
```

```sql
SELECT DISTINCT `title` FROM `nav` ORDER BY id desc
```



#### 7、Scan

```go
type Result struct {
    Name string
    Age int
}
var result Result
db.Table("users").Select("name", "age").Where("name = ?", "Antonio").Scan(&result)

// 原生 SQL
db.Raw("SELECT name, age FROM users WHERE name = ?", "Antonio").Scan(&result)

var result []models.User
models.DB.Raw("SELECT * FROM user").Scan(&result)
fmt.Println(result)
```



#### 8、Join 

```go
type result struct {
    Name string
    Email string
}

db.Model(&User{}).Select("users.name, emails.email").Joins("left join emails on emails.user_id = users.id").Scan(&result{})
```

```sql
SELECT users.name, emails.email FROM `users` left join emails on emails.user_id = users.id
```



### 14.4、Gin GORM 查看执行的sql

```go
func init() {
    dsn :="root:123456@tcp(192.168.0.6:3306)/gin?charset=utf8mb4&parseTime=True&loc=Local" 
    
    DB, err = gorm.Open(mysql.Open(dsn), &gorm.Config{
        QueryFields: true, 
    })
    
    // DB.Debug()
    if err != nil {
        fmt.Println(err)
    }
}
```



## 十五、原生SQL 和SQL 生成器

更多使用方法参考：

https://gorm.io/zh_CN/docs/sql_builder.html



### 1、使用原生 sql 删除 user 表中的一条数据

```go
result := models.DB.Exec("delete from user where id=?", 3)
fmt.Println(result.RowsAffected)
```



### 2、使用原生 sql 修改 user 表中的一条数据

```go
result := models.DB.Exec("update user set username=? where id=2", "哈哈")
fmt.Println(result.RowsAffected)
```



### 3、查询 uid=2 的数据

```go
var result models.User
models.DB.Raw("SELECT * FROM user WHERE id = ?", 2).Scan(&result)
fmt.Println(result)
```



### 4、查询 User 表中所有的数据

```go
var result []models.User
models.DB.Raw("SELECT * FROM user").Scan(&result)
fmt.Println(result)
```



### 5、统计 user 表的数量

```go
var count int
row := models.DB.Raw("SELECT count(1) FROM user").Row(&count )
row.Scan(&count)
```

