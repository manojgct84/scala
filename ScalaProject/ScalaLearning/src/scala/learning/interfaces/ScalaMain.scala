package scala.learning.interfaces

object ScalaMain {

	def main(args: Array[String]): Unit = {

		val employee = new Employee();
		employee.getPeopleDetails("Manoj", 32)
		val address = new Address("02", "PO Box 371680", "Denver", "CO 80237-5680", "US", 5687)
		employee.getAddressDetails(address)

		try {
			employee.exceptionHandeling()
		} catch {
			case ex: Exception => println(ex.fillInStackTrace())
		}
	}
}
