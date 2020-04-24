//package data.support
//
//object Demo extends Application {
//  def main(args: Array[String]): Unit = {
//    val ss = createSparkSession(s"lemon.${this.getClass.getSimpleName}")
//    //从mysql中获取广告主列表
//    val data = args(0)
//    val config = args(1)
//    val tuples = getCustomers4Module(data, config).toArray
//    setMonitorName2(tuples, 4)
//    setMonitorName2(tuples, 16)
//    ss.stop()
//  }
//
//  def setMonitorName2(monitors: Array[(Int, String, String, String, String, String, String, String, String, String)], ca: Int) = {
//    val conn = newMySQLSession("demo_dmp")
//
//    for ((config_id, res_type, res_name, cnt_type, pv_cnt, uv_cnt, bizdate, customer_id, bizhour, data_source) <- monitors) {
//      try {
//        val sql =
//          s"""
//             |INSERT IGNORE INTO adtrack_result(config_id,res_type,res_name,cnt_type,pv_cnt,uv_cnt,bizdate,customer_id,bizhour,data_source)
//             |values(?,?,?,?,?,?,?,?,?,?)
//           """.stripMargin
//        val data = Seq(Seq(ca, res_type, res_name, cnt_type, pv_cnt, uv_cnt, bizdate, "23435697", bizhour, data_source))
//        operateMysqlWithSession(sql, data, conn)
//        logInfo(s"Insert Sql: ${sql}")
//
//      } catch {
//        case e: Throwable => logError(s"Data is:")
//          e.printStackTrace()
//      }
//    }
//
//  }
//
//  def getCustomers4Module(data: String, config: String) = {
//    newMySQLSession("dmp")
//    val sql = s"select  config_id,res_type,res_name,cnt_type,pv_cnt,uv_cnt,bizdate,customer_id,bizhour,data_source  from   adtrack_result  where  bizdate = '${data}'  and   config_id='${config}'"
//    DB readOnly { implicit session =>
//      SQL(s"${sql}").map(x => {
//        (x.int(1),
//          x.string(2),
//          x.string(3),
//          x.string(4),
//          x.string(5),
//          x.string(6),
//          x.string(7),
//          x.string(8),
//          x.string(9),
//          x.string(10))
//      }).list().apply()
//    }
//  }
//}
