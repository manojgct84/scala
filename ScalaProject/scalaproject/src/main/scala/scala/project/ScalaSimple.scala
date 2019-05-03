import org.apache.spark.sql.SparkSession
//import org.apache.hadoop.mapreduce.v2.app.webapp.App

/**
	* Hello world!
	*
	*/
object ScalaSimple {

	def main(args: Array[String]): Unit = {

		val sparkSession = SparkSession.builder
			.master("local[4]")
			.appName("spark session example")
			.config("spark.driver.host", "localhost")
			.config("spark.testing.memory", "471859200")
			.getOrCreate()


		val df = sparkSession.read
			.option("header", "true")
			.csv("src/main/resources/sales.csv")

		val dataFrameCSV = sparkSession.read.format("csv")
			.option("header", "true")
			.option("inferSchema", "true").load("src/main/resources/sales.csv")

		df.show()
		dataFrameCSV.show()
	}

}
