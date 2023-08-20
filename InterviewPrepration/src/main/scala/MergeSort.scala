object MergeSort {
  def mergeSort(arr: Array[Int]): Array[Int] = {
    def merge(left: Array[Int], right: Array[Int]): Array[Int] = {
      var merged = Array.empty[Int]
      var i = 0
      var j = 0

      /*
      This while loop compares elements from the left and right arrays and adds the
      smaller element to the merged array. It iterates until either i reaches the end of
      the left array or j reaches the end of the right array.

      The if statement compares the current elements at indices i and j in the left and right
       arrays. If the element in the left array is smaller, it's appended to the merged array,
       and i is incremented. Otherwise, the element from the right array is appended,
       and j is incremented.
       */
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

    if (arr.length <= 1) arr
    else {
      val mid = arr.length / 2
      val left = mergeSort(arr.slice(0, mid))
      val right = mergeSort(arr.slice(mid, arr.length))
      merge(left, right)
    }
  }

  def main(args: Array[String]): Unit = {
    val arr = Array(9, 3, 1, 5, 7, 11, 8, 2)
    val sortedArr = mergeSort(arr)
    println(s"Original Array: ${arr.mkString(", ")}")
    println(s"Sorted Array: ${sortedArr.mkString(", ")}")
  }
}
