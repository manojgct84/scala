package scala.project

import org.apache.spark.sql.SparkSession

class SparkSessionClazz {

	def getSparkSession(): SparkSession = {

		return SparkSession.builder
			.master("local[4]")
			.appName("spark session example")
			.config("spark.driver.host", "localhost")
			.config("spark.testing.memory", "471859200")
			.getOrCreate()
	}
}
