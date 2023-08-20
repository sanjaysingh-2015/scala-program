class Employee{
  val name:String = "Sanjay"
  val salary:Float = 10000.0F
  def getBonus(): Int = {
    5
  }
  def showDetails(designation: String, bonus: Int): Unit = {
    println("Name: "+name+", Salary: "+salary+", "+designation+", Bonus("+bonus+"%) : "+(salary * (bonus/100.0)))
  }
}

class Programmer extends Employee {
  override def getBonus():Int = {
   20
  }
}

class Account extends Employee  {
  override def getBonus(): Int = {
    10
  }
}

class Sales extends Employee  {
  override def getBonus(): Int = {
    22
  }
}

object InheritanceExample {
  def main(args:Array[String]): Unit = {
    val employee = new Employee()
    employee.showDetails("Employee",employee.getBonus())
    val programmer = new Programmer()
    employee.showDetails("Programmer",programmer.getBonus())
    val account = new Account()
    account.showDetails("Account",account.getBonus())
    val sales = new Sales()
    sales.showDetails("Sales",sales.getBonus())
  }
}
