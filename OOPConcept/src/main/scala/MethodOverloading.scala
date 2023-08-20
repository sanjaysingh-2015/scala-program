class Arithmetic {
  def add(a: Int, b: Int): Unit = {
    println(a+" + "+b+" = "+ (a+b))
  }

  def add(a: Int, b: Int, c: Int): Unit = {
    println(a + " + " + b + " + "+c+" = " + (a + b+ c))
  }

  def add(a: Double, b: Double): Unit = {
    println(a + " + " + b + " = " + (a + b))
  }

  def add(a: Double, b: Double, c: Double): Unit = {
    println(a + " + " + b + " + " + c + " = " + (a + b + c))
  }

}

object MethodOverloading {
  def main(args: Array[String]): Unit = {
    val obj = new Arithmetic()
    obj.add(10,20)
    obj.add(10,20,30)
    obj.add(10.10, 20.29)
    obj.add(10.40, 20.34, 30.45)
  }
}
