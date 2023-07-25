## 2、定义变量、fmt 包、Print、Println、Printf、Go 语言注释



### 1、Go 语言定义变量 

这里我们为了演示代码期间给大家先简单介绍一下变量，后面的教程还会详细讲解。关于变量：程序运行过程中的数据都是保存在内存中，我们想要在代码中操作某个数据时就需要去内存上找到这个变量，但是如果我们直接在代码中通过内存地址去操作变量的话，代码的可读性会非常差而且还容易出错，所以我们就利用变量将这个数据的内存地址保存起来，以后直接通过这个变量就能找到内存上对应的数据了。



Golang 中常见的变量定义方法如下： 

1、var 定义变量

```go
var 变量名 类型 = 表达式
```

```go
var name string = "zhangsan"
```



2、类型推导方式定义变量 

a 在函数内部，可以使用更简略的 := 方式声明并初始化变量。 

**注意**：短变量只能用于声明局部变量，不能用于全局变量的声明

```go
变量名 := 表达式
```

```go
n := 10
```



### 2、fmt 包、Print、Println、Printf

Go 中要打印一个值需要引入 fmt 包

```go
import "fmt"
```

fmt 包里面给我们提供了一些常见的打印数据的方法，比如：Print 、Println、Printf，在我们实际开发中 Println、Printf 用的非常多。



1、Print 和 Println 区别：

一次输入多个值的时候 Println 中间有空格 Print 没有

```go
fmt.Println("go", "python", "php", "javascript") // go python php javascript
fmt.Print("go", "python", "php", "javascript") // gopythonphpjavascript
```

Println 会自动换行，Print 不会

```go
package main
import "fmt"
func main() {
    fmt.Println("hello")
    fmt.Println("world")
    // hello
    // world
    fmt.Print("hello")
    fmt.Print("world")
    // helloworld
}
```



2、Println 和 Printf 区别

Printf 是格式化输出，在很多场景下比 Println 更方便，举个例子：

```go
a := 10
b := 20
c := 30
fmt.Println("a=", a, ",b=", b, ",c=", c) //a= 10 ,b= 20 ,c= 30
fmt.Printf("a=%d,b=%d,c=%d", a, b, c) //a=10,b=20,c=30
```



### 3、Go 语言中的注释

win 下面 ctrl+/ 可以快速的注释一样，mac 下面 command+/ 也可以快速的注释一样

```go
/* 
	这是一个注释
*/

//这是一个注释
```

