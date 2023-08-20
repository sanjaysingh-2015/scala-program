// Define a trait for logging
trait Logger {
  def log(message: String): Unit = {
    println(s"[LOG] $message")
  }
}

// Define a class that mixes in the Logger trait
class UserService extends Logger {
  def createUser(name: String): Unit = {
    log(s"Creating user: $name")
    // Logic for creating a user
  }
}

object TraitMixinExample {
  def main(args:Array[String]): Unit = {
    val userService = new UserService()
    userService.createUser("John Doe")
  }
}
