class ThisClass {
  var id:Int = 0
  var name: String = ""
  def this(id:Int, name:String){
    this()
    this.id = id
    this.name = name
  }
  def show(){
    println(id+" "+name)
  }
}

object ThisExample{
  def main(args:Array[String]){
    var t = new ThisClass(101,"Martin")
    t.show()
  }
}
