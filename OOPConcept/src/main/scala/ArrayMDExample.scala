class ArrayExampleMD{
  var arr1 = Array(Array(1, 2, 3, 4, 5), Array(6, 7, 8, 9, 10)) // Creating multidimensional array
  var arr2 = Array(Array(1, 2, 3, 4, 5), Array(6, 7, 8, 9, 10))
  var arr3 = Array.ofDim[Int](2, 5)

  def show() {
    for (i <- 0 to 1) { // Traversing elements using loop
      for (j <- 0 to 4) {
        arr3(i)(j) = arr1(i)(j) + arr2(i)(j)
        print(" " + arr3(i)(j))
      }
      println()
    }
  }
}

object ArrayMDExample {
  def main(args:Array[String]){
    var a = new ArrayExampleMD()
    a.show()
  }
}
