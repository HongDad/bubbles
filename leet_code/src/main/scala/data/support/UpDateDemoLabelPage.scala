//package data.support
//
//import org.apache.hadoop.io.{LongWritable, Text}
//import org.apache.hadoop.mapred.TextInputFormat
//import org.apache.spark.rdd.RDD
//import org.apache.spark.sql.types.{DataTypes, StructField, StructType}
//import org.apache.spark.sql.{Row, SparkSession}
//
//import scala.collection.mutable.ArrayBuffer
//
///** *
//  * Demo标签配置数据、页面配置数据修改
//  */
//object UpDateDemoLabelPage extends Application {
//  def main(args: Array[String]): Unit = {
//    val ss = createSparkSession(s"lemon.DwdDmpAccessResolver")
//    //读取标签配置数据
//    val labeFile = "file:///C:\\Users\\Administrator\\Desktop\\工作\\Demo\\Demo标签配置.csv"
//    val labeInputFile = readCSV(ss, "TRUE", ArrayBuffer("metea_id", "id", "name"), "GBK", labeFile)
//    //标签配置数据处理
//    val labelResult = labeInputFile.select("id", "name").rdd.map(x => (x.getString(0), x.getString(1))).collect()
//    // upDateLabelConfig(labelResult)
//
//    //读取页面配置数据
//    val pageFile = "file:///C:\\Users\\Administrator\\Desktop\\工作\\Demo\\Demo页面配置.csv"
//    val pageInputFile = readCSV(ss, "TRUE", ArrayBuffer("id", "url", "cutOffFlag", "urlName", "pageType", "cdjStage", "attributeId", "customerId", "createTime", "updateTime", "siteUrl", "siteId"), "GBK", pageFile)
//    //页面配置数据处理
//    val pageResult = pageInputFile.select("id", "urlName").rdd.map(x => (x.getString(0), x.getString(1))).collect()
//    upDatePageConfig(pageResult)
//    ss.stop()
//  }
//
//  //解析csv文件，解决中文乱码问题
//  def readCSV(spark: SparkSession, headerSchema: String, mySchema: ArrayBuffer[String], code: String, file: String) = {
//    val rddArr: RDD[Array[String]] = spark.sparkContext.hadoopFile(file, classOf[TextInputFormat],
//      classOf[LongWritable], classOf[Text]).map(
//      pair => new String(pair._2.getBytes, 0, pair._2.getLength, code))
//      //处理同一个单元格 同时出现 引号 逗号串列问题 切割
//      .map(_.trim.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1))
//    val fieldArr = rddArr.first()
//    //Row.fromSeq(_) 如果只是 map(Row(_)),会导致 spark.createDataFrame(rddRow,schema)错误
//    val rddRow = rddArr.filter(!_.reduce(_ + _).equals(fieldArr.reduce(_ + _))).map(Row.fromSeq(_))
//    val schemaList = ArrayBuffer[StructField]()
//    if ("TRUE".equals(headerSchema)) {
//      for (i <- 0 until fieldArr.length) {
//        println("fieldArr(i)=" + fieldArr(i))
//        schemaList.append(StructField(mySchema(i), DataTypes.StringType))
//      }
//    } else {
//      for (i <- 0 until fieldArr.length) {
//        schemaList.append(StructField(s"_c$i", DataTypes.StringType))
//        println("fieldArr(i)=" + fieldArr(i))
//      }
//    }
//    val schema = StructType(schemaList)
//    spark.createDataFrame(rddRow, schema)
//  }
//
//  //修改页面配置数据
//  def upDatePageConfig(tuple: Array[(String, String)]) = {
//    for ((id, name) <- tuple) {
//      val conn = newMySQLSession("demo_databank")
//      try {
//        val sql =
//          s"""
//             |Update   page_config  SET  urlName =?
//             |where  id =?
//           """.stripMargin
//        val data = Seq(Seq(name, id))
//        operateMysqlWithSession(sql, data, conn)
//        logInfo(s"Insert Sql: ${sql}")
//
//      } catch {
//        case e: Throwable => logError(s"Data is: ${id},${name}")
//          e.printStackTrace()
//      }
//    }
//  }
//
//  //修改标签配置数据
//  def upDateLabelConfig(tuple: Array[(String, String)]) = {
//    for ((id, name) <- tuple) {
//      val conn = newMySQLSession("demo_label")
//      try {
//        val sql =
//          s"""
//             |Update  t_label_user_behavior  SET  name =?
//             |where  id =?
//           """.stripMargin
//        val data = Seq(Seq(name, id))
//        operateMysqlWithSession(sql, data, conn)
//        logInfo(s"Insert Sql: ${sql}")
//
//      } catch {
//        case e: Throwable => logError(s"Data is: ${id},${name}")
//          e.printStackTrace()
//      }
//    }
//  }
//
//  //修改标签配置url数据
//  def upDateLabelUrlConfig() = {
//    val conn = newMySQLSession("demo_label")
//    try {
//      val sql =
//        s"""
//           |UPDATE  page_config   SET   url=REPLACE(url,'tiffany','yoyidemo')   WHERE  customerId = '23446838'
//           """.stripMargin
//      val data = Seq(Seq())
//      operateMysqlWithSession(sql, data, conn)
//      logInfo(s"Insert Sql: ${sql}")
//
//    } catch {
//      case e: Throwable => logError(s"Data is: ")
//        e.printStackTrace()
//    }
//  }
//}
