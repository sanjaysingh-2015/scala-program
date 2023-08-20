class Vehicle{
  val speed:Int = 60
}

class Bike extends Vehicle {
  override val speed: Int = 100 // Override keyword
  def show() {
    println(speed)
  }
}

object FieldOverriding {
  def main(args:Array[String]): Unit = {
    var b = new Bike()
    b.show()
  }
}
