package demo

object T {
	def run = {
		val dog = new Dog("dog") with Animal
		val cat = new Cat("cat")

		println(dog.name)
		println(cat.name)

		dog.run
		cat.run

		new Animal_0 {
			override def run: Unit = println("animal is running")
		}.run
	}
}

trait Animal {
	val name: String
	def run = println(name + " is running.")
}

trait Animal_0 {
	val name: String = "animal"
	def run: Unit
}

class Dog (val name: String)
class Cat (val name: String) extends Animal


