package StringProgramming

//Write a Scala program to create a new string from a given string
// swapping the last two characters of the given string.
// The length of the given string must be two or more.
object Problem20 extends App {
  def swapLastTwo(str: String): String = {
    val sp1 = str.substring(0, str.length - 2)
    val sp2 = str.substring(str.length-2, str.length)
    sp1 + sp2.reverse
  }

  println(swapLastTwo("Sanjay"))
}
