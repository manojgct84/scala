import org.apache.spark.api.java.function.ForeachFunction
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types._

/**
	* @Author : Manojkumar M
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


		println("******************This is a user defined schema for csv file******************************")

		val structData = new StructType().add("transactionId", "String", true).add("customerId", IntegerType, true)
			.add("itemId", IntegerType, true).add("amountPaid", FloatType, true)

		val schemaDF = sparkSession.read.format("csv").option("header", "true")
			.schema(structData).load("src/main/resources/sales.csv")

		schemaDF.show()

		//val peopleDF = sparkSession.read.json("examples/src/main/resources/people.json")
		//Writting a file into parquet file
		//schemaDF.select("transactionId", "customerId", "itemId", "amountPaid").write.save("src/main/resources/sales" +
		//	".parquet")

		//To write a parquet file
		//schemaDF.write.parquet("src/main/resources/sales.parquet")

		val sqlDF = sparkSession.read.parquet("src/main/resources/sales.parquet")
		sqlDF.show()

		//Creating a rdd in sparksession  in 2.x - This is because rdd is part of the spark context and dataFrame and
		// dataset is part of the sparksession

		val rddData = sparkSession.sparkContext.textFile("src/main/resources/data.txt")

		val count = rddData.countByValue();

		rddData.foreach(items => println("Print the value  %s".format(items)))

		sqlDF.foreach(rows => println("This is data frame printing : %s".format(rows)))

		println("The count by value using RDD %s".format(count))


	}
}
