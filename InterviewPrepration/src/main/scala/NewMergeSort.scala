object NewMergeSort extends App {
  def mergeSort(arr: Array[Int]): Array[Int] = {
    if (arr.length <= 1) arr
    else {
      var mid = arr.length / 2
      var left = mergeSort(arr.slice(0, mid))
      var right = mergeSort(arr.slice(mid, arr.length))
      merge(left, right)
    }
  }

  def merge(left: Array[Int], right: Array[Int]): Array[Int] = {
    var merged: Array[Int] = Array.empty[Int]
    var i = 0
    var j = 0

    while (i < left.length && j < right.length) {
      if (left(i) < right(j)) {
        merged = merged :+ left(i)
        i += 1
      } else {
        merged = merged :+ right(j)
        j += 1
      }
    }
    while (i < left.length) {
      merged = merged :+ left(i)
      i += 1
    }

    while (j < right.length) {
      merged = merged :+ right(j)
      j += 1
    }
    merged
  }

  val arr = Array(9, 3, 1, 5, 7, 11, 8, 2)
  val sortedArr = mergeSort(arr)
  println(s"Original Array: ${arr.mkString(", ")}")
  println(s"Sorted Array: ${sortedArr.mkString(", ")}")
}
