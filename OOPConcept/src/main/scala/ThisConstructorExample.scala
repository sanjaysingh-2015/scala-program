class ThisConstructorClass(name: String) {
  var id:Int = 0
  def this(name:String, id: Int){
    this(name)
    this.id = id
  }
  def show(){
    println(id+" "+name)
  }
}

object ThisConstructorExample{
  def main(args:Array[String]){
    var t = new ThisConstructorClass("Sanjay", 101)
    t.show()
  }
}
