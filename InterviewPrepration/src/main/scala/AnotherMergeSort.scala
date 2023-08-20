object AnotherMergeSort extends App {
  def merge(left: Array[Int], right: Array[Int]) : Array[Int] = {
    var merged: Array[Int] = Array.empty
    var i = 0
    var j = 0

    while(i < left.length && j < right.length) {
      if(left(i) < right(j)) {
        merged = merged :+ left(i)
        i += 1
      } else {
        merged = merged :+ right(j)
        j += 1
      }
    }

    while(i < left.length) {
      merged = merged :+ left(i)
      i += 1
    }

    while(j < right.length) {
      merged = merged :+ right(j)
      j += 1
    }
    merged
  }

  def mergeSort(arr: Array[Int]): Array[Int] = {
    if(arr.length <= 1) arr
    else {
      val mid = arr.length / 2
      val left = mergeSort(arr.slice(0, mid))
      val right = mergeSort(arr.slice(mid, arr.length))
      merge(left, right)
    }
  }

  val arr = Array(3,4,1,2,9,8,2,5,4,0,6,7)
  println("Original: "+ arr.mkString(", "))
  println("Sorted:   "+ mergeSort(arr).mkString(", "))
}
