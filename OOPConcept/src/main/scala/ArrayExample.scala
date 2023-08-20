class ArrayExample{
  def show(arr:Array[Int]){
    arr.foreach(e => println(e))
    println("Fourth Element = "+ arr(3))
  }
}

object ArrayExample{
  def main(args:Array[String]){
    var arr = Array(1, 2, 3, 4, 5, 6)
    var a = new ArrayExample()
    a.show(arr)
  }
}