class MySingleton {
  private def show(): Unit = {
    println("Companion Class")
  }
}

object MySingleton {
  def createInstance(): Unit = {
    new MySingleton().show()
    println("Companion Object")
  }
}

object SingletonClassWithCompanionExample {
  def main(args: Array[String]): Unit = {
    val mySingleton = MySingleton.createInstance()
  }
}
