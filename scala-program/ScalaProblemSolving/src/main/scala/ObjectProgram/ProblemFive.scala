package ObjectProgram
//Write a Scala program that creates a class BankAccount with properties accountNumber and balance.
// Implement methods to deposit and withdraw money from the account.

class BankAccount(private var accountNumber: String,private var balance: Double) {
  def deposit(amt: Double): String = {
    balance += amt
    s"Transaction done - Available Balance: $balance"
  }

  def withdraw(amt : Double): String = {
    if(balance < amt) "Insufficient balance for transaction"
    else {
      balance -= amt
      s"Transaction done - Available Balance: $balance"
    }
  }
}
object ProblemFive extends App {
  val account = new BankAccount("12345", 100.0)
  var result = account.deposit(234.5)
  println(s"$result")
  result = account.withdraw(500.0)
  println(s"$result")
  result = account.withdraw(100.0)
  println(s"$result")
}
