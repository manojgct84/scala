package scala.learning.interfaces

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
	}
}

trait People {

	def getPeopleDetails(name: String, age: Int)
}

trait Adress {

	def getAddressDetails(address: Address)
}


