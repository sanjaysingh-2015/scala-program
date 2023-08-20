trait Printable{
  def print()
}

class A4 extends Printable{
  def print(){
    println("Hello")
  }
}

object TraitExample01{
  def main(args:Array[String]){
    var a = new A4()
    a.print()
  }
}