## 案列一

```scala
import org.apache.spark.mllib.clustering._
import org.apache.spark.mllib.linalg._

    //新建k-means模型，并训练
    val initMode = "k-means||"
    val numClusters = 5
    val numIterations = 200
    val model = new KMeans()
      .setInitializationMode(initMode)
      .setK(numClusters)
      .setMaxIterations(numIterations)
      .run(value)

     //循环输出所属聚类与聚类中心
    for (elem <- value.collect() ; centos <- model.clusterCenters) {
      val i: Int = model.predict(elem)
      println(s"=cluster ${i}: 聚类中心为[${centos}]")
    }

```

---



## 案例二

```scala
package KMeansDemo

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.clustering._
import org.apache.spark.mllib.linalg.Vectors

object KMeansTest2 {
  def main(args: Array[String]): Unit = {

    //构建spark对象
    val spark = new SparkConf().setMaster("local[*]").setAppName("K-Means")
    val sc = new SparkContext(spark)

    //读取数据
    val df = sc.textFile("Data/HelloSpark.txt")
    val data = df.map( x => {
      Vectors.dense(x.split(",").map(_.toDouble))
    })
      .cache()

    //新建KMeans聚类模型，并训练
    val initMode = "k-means||"
    val numClusters = 5
    val numIterations = 500
    val model = new KMeans()
      .setInitializationMode(initMode)
      .setK(numClusters)
      .setMaxIterations(numIterations)
      .run(data)

    //打印数据模型中心点
    val centers = model.clusterCenters
    centers.foreach(
      centers => println( "Clustering Center:" + centers)
    )

    //通过predict()方法来确定每个样本所属的聚类
    data.collect().foreach(
      sample =>{
        val predictedCluster = model.predict(sample)
        println(sample.toString + " belongs to cluster " + predictedCluster)
      }
    )

    //使用误差平方之和来评估数据模型（度量聚类的有效性）
    val WSSSE = model.computeCost(data)
    println("within Set sum of Squared Errors = " + WSSSE)

    //保存模型
//    model.save(sc,"/Data/kMeansTest")
//    val model1 = KMeansModel.load(sc, "/Data/kMeansTest")

  }
}
```

