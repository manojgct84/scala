package scala.learning.interfaces


import scala.learning.exception._

class Employee extends People with Adress {
	override def getPeopleDetails(name: String, age: Int): Unit = {
		println("Name : for ", name, " Age : for ", age)

	}

	override def getAddressDetails(address: Address): Unit = {
		println("Address : for ", address.flatNo)
		println("Address : for ", address.streeAdress)
		println("Address : for ", address.landMark)
		println("Address : for ", address.city)
		println("Address : for ", address.state)
		println("Address : for ", address.pincode)

		val john = new MatchCase("John", 28)
		val snow = new MatchCase("Jhon Snow", 34)
		val aarya = new MatchCase("Arya stak", 20)
		for (x <- List(john, snow, aarya)) {

			x match {
				case MatchCase("John", 28) => println(" Hi Jhon")
				case MatchCase("Jhon Snow", 34) => println("Hi Snow")
				case MatchCase("Arya stak", 20) => println("Hi Stak")
				case _ => println("Found none")
			}

		}
	}

	def exceptionHandeling(): Unit = {

		try {
			throw new IllegalArgumentException("Runtime exception created")
		} catch {
			case ex: IllegalArgumentException =>
				throw new RunTimeException("This is runtime exception ", ex.fillInStackTrace())
		} finally {
			println("Please check you maths calc")
		}

	}
}

trait People {

	def getPeopleDetails(name: String, age: Int)
}

trait Adress {

	def getAddressDetails(address: Address)
}


