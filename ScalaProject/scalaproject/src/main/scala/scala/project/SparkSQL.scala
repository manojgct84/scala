package scala.project

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.sql.functions._

object SparkSQL {

	def readJson(sparkSession: SparkSession, filePath: String): DataFrame = {
		return sparkSession.read.json(filePath)
	}

	def readTextFile(sparkSession: SparkSession, path: String): RDD[String] = {
		sparkSession.sparkContext.textFile(path)
	}

	def main(args: Array[String]): Unit = {

		val sparkSession = new SparkSessionClazz().getSparkSession()

		val carsDF = readJson(sparkSession, "src/main/resources/cars.json")

		//This is to display car details.
		carsDF.show()

		//If you want to see n rows of DataFrame in a tabular form then use the following command
		carsDF.show(2)

		//take(n) Returns the first n rows in the DataFrame.
		carsDF.take(2).foreach(println)

		//Returns the number of rows
		carsDF.count()

		//Group by  return number of group for column
		carsDF.groupBy("speed").count().show()

		//head () is used to returns first row.
		val headRow = carsDF.head(3)
		println(headRow.mkString(","))

		//Returns the first row.
		val firstRow = carsDF.first()
		println(firstRow.mkString(","))

		//Returns an array that contains all of Rows in this DataFrame.
		val array = carsDF.collect()
		println(array.mkString(","))

		//If you want to see the Structure (Schema) of the DataFrame
		carsDF.printSchema()


		//Converting the RDD to DF (DataFrame)
		val fruits = readTextFile(sparkSession, "src/main/resources/Fruits.txt")

		//fruits.map(x => x.split(";")).map(f => Fruits(f(0).toInt, f(1), f(2).toInt)).toDF()

		val rdd = fruits.map(_.split(",")).map { x => org.apache.spark.sql.Row(x: _*) }

		// Create a dynamic schema
		//val schemaString = "id name quantity"

		//val schema = StructType(schemaString.split(" ").map(fieldName =>StructField(fieldName, StringType, true)))


		val structFruits = new StructType().add(StructField("ID", IntegerType, true)).add(StructField("Name", StringType,
			true)).add(StructField("quantity", IntegerType, true))

		// This is not working
		//	val fruitDF = sparkSession.createDataFrame(rdd, structFruits)
		//	fruitDF.show()

		/*val fruitsDF = fruits.map(_.split(","))
			.map(f => Fruit(f(0).trim.toInt, f(1), f(2).trim.toInt))
			.toDF().show()*/


		//Returns all column names and their data types as an array
		carsDF.dtypes.foreach(println)

		//Returns all column names as an array.
		carsDF.columns.foreach(println)

		//cache() explicitly to store the data into memory. Or data stored in a distributed way in the memory by default.
		val resultCache = carsDF.filter(carsDF("speed") > 300)

		resultCache.cache().show()

		//Returns a new DataFrame sorted by the given expressions.

		carsDF.sort(desc("speed")).show()

		//Returns a new DataFrame sorted by the specified column(s).
		carsDF.orderBy(desc("speed")).show()

		//counting the number of cars who are of the same speed
		carsDF.groupBy("speed").count().show()

		//Returns a DataFrameNaFunctions for working with missing data.
		carsDF.na.drop().show()

		//Returns a new DataFrame with an alias set.
		carsDF.select(avg("speed").as("avg_speed")).show()

		//Returns a new DataFrame with an alias set. Same as as
		carsDF.select(avg("weight").alias("avg_weight")).show()

		//To fetch speed-column among all columns from the DataFrame.
		carsDF.select("*").show()

		//filter the cars whose speed is greater than 300 (speed > 300)
		carsDF.filter(carsDF("speed") > 300).show()

		//Filters age using the given SQL expression (where).
		carsDF.where(carsDF("speed") > 100).show()

		//Aggregates on the entire DataFrame without groups.
		carsDF.agg(max("speed")).show()

		//Returns a new DataFrame by adding a column or replacing the existing column that has the same name.


	}
}
