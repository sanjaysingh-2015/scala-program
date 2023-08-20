trait Printable1{
  def print()
  def show(): Unit = {
    println("This is showable");
  }
}

class A6 extends Printable1{
  def print(){
    println("This is printable")
  }
}

object TraintExample02{
  def main(args:Array[String]){
    var a = new A6()
    a.print()
    a.show()
  }
}