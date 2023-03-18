# sorted

- 对一个集合进行自然排序，通过传递隐式的Ordering
- 无需参数

```scala
def sorted[B >: A](implicit ord: Ordering[B]): Repr
```

```scala
scala> val a = List(10, 5, 8, 1, 7).sorted
a: List[Int] = List(1, 5, 7, 8, 10)

scala> val b = List("banana", "pear", "apple", "orange").sorted
b: List[String] = List(apple, banana, orange, pear)
```

**自定义类型的排序**
如果序列持有的类型没有隐式类型，则无法用 对它进行排序
其中`empList.sorted.foreach(println)`等价于

```
empList.sorted(Ordering[Emp]).foreach(println)
```

利用隐式参数完成排序

```scala
object _SortDemo4 extends App {
  val firstEmp: Emp = Emp(1, "michael", 1000.00)
  val secondEmp: Emp = Emp(2, "james", 12000.00)
  val thirdEmp = Emp(3, "shaun", 9000.00)
  val empList = List(firstEmp, secondEmp, thirdEmp)
  //传参的时候不需要传入隐式参数
  empList.sorted.foreach(println)
}

case class Emp(id: Int, name: String, salary: Double) {
}

object Emp {
//定义一个隐式变量
  implicit val suibian: Ordering[Emp] = new Ordering[Emp] {
    override def compare(x: Emp, y: Emp): Int = {
      val rs: Int = (x.salary - y.salary).toInt
      rs
    }
  }
}
```

隐式参数可以不用传递,系统会自动寻找

```scala
object _02Simple {
  def main(args: Array[String]): Unit = {
    val firstEmp: Emp = Emp(1, "michael", 12000.00)
    val secondEmp: Emp = Emp(2, "james", 12000.00)
    val thirdEmp = Emp(3, "shaun", 12000.00)
    val empList=List(firstEmp,secondEmp,thirdEmp)
    empList.sorted.foreach(println)
  }
}

case class Emp(id: Int, name: String, salary: Double) extends Ordered[Emp] {
  override def compare(that: Emp): Int = this.name.compareTo(that.name)
}
```

---

# SortWith

要传入一个比较函数,且该函数的返回值为布尔类型

```scala
def sortWith(lt: (A, A) => Boolean): Repr
```

```scala
scala> val list=List("a","d","F","B","e")
list: List[String] = List(a, d, F, B, e)

scala> list.sortWith((x,y)=>x.toLowerCase<y.toLowerCase())
res0: List[String] = List(a, B, d, e, F)
```

```scala
scala>  List("Steve", "Tom", "John", "Bob").sortWith(_.compareTo(_) < 0)
res1: List[String] = List(Bob, John, Steve, Tom)
```

**自定义类的排序**

比较简单,不用定义隐式什么的

```scala
object _02Simple {
  def main(args: Array[String]): Unit = {
    val firstEmp: Emp = Emp(1, "michael", 12000.00)
    val secondEmp: Emp = Emp(2, "james", 12000.00)
    val thirdEmp = Emp(3, "shaun", 12000.00)
    val empList = List(firstEmp, secondEmp, thirdEmp)
    empList.sortWith((p1, p2) => {
      p1.name < p2.name
    }).foreach(println)
  }
}

case class Emp(id: Int, name: String, salary: Double) {}
```

---

# SortBy

依据隐式的排序方法进行排序

```scala
def sortBy[B](f: A => B)(implicit ord: Ordering[B]): Repr
```

**一维排序例子**

```scala
scala> val words = "The quick brown fox jumped over the lazy dog".split(' ')
words: Array[String] = Array(The, quick, brown, fox, jumped, over, the, lazy, dog)
//依据字符串长度进行排序
scala> words.sortBy(x=>x.length)
res0: Array[String] = Array(The, fox, the, dog, over, lazy, quick, brown, jumped)
//先依据字符串长度,再依据首字母排序
scala> words.sortBy(x=>x.length,x.head)
<console>:26: error: too many arguments for method sortBy: (f: String => B)(implicit ord: scala.math.Ordering[B])Array[String]
       words.sortBy(x=>x.length,x.head)
                   ^

scala> words.sortBy(x=>(x.length,x.head))
res2: Array[String] = Array(The, dog, fox, the, lazy, over, brown, quick, jumped)
//依据字典排序
scala> words.sortBy(x=>x)
res3: Array[String] = Array(The, brown, dog, fox, jumped, lazy, over, quick, the)
```

**二维排序例子**

```scala
scala> val a = List(("a",2),("b",44),("c",20),("a",20),("a",1))
a: List[(String, Int)] = List((a,2), (b,44), (c,20), (a,20), (a,1))
//依据数字进行排序
scala> a.sortBy(x=>x._2)
res8: List[(String, Int)] = List((a,1), (a,2), (c,20), (a,20), (b,44))
//先依据字母排序,字母相同的依据数字排序
scala> a.sortBy(x=>(x._1,x._2))
res9: List[(String, Int)] = List((a,1), (a,2), (a,20), (b,44), (c,20))

scala> a.sortBy(x=>x._1)
res10: List[(String, Int)] = List((a,2), (a,20), (a,1), (b,44), (c,20))
//先依据数字排序,数字相同的依据字母排序
scala> a.sortBy(x=>(x._2,x._1))
res11: List[(String, Int)] = List((a,1), (a,2), (a,20), (c,20), (b,44))
```

sortBy源码底层调用了sorted

```scala
def sortBy[B](f: A => B)(implicit ord: Ordering[B]): Repr = sorted(ord on f)
```

```scala
object _02Simple {
  def main(args: Array[String]): Unit = {
    val firstEmp: Emp = Emp(1, "michael", 10000.00)
    val secondEmp: Emp = Emp(2, "james", 18000.00)
    val thirdEmp = Emp(3, "shaun", 12000.00)
    val empList = List(firstEmp, secondEmp, thirdEmp)
    empList.sortBy(p => p.salary)(Ordering[Double]).foreach(println)
  }
}

case class Emp(id: Int, name: String, salary: Double) {}
```

---

# 总结



sorted排序无需传参,默认字典排序,自动根据类型寻找隐式类进行排序,比如元素为String,就去找String的隐式排序

sortWith 需要自己传递一个比较函数,返回值为布尔类型

SortBy需要自己传入一个比较字段.sorted传不了

自定义类的排序用SortWith与SortBy会比较方便

多字段排序用SortBy比较方便.
