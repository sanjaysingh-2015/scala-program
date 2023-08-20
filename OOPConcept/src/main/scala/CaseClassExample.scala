import SimpleAndCaseClassExample.CasePerson

case class CaseClass(name:String, age:Int)

object CaseClassExample{
  def main(args:Array[String]){
    var caseClass =  CaseClass("Sanjay",20)       // Creating object of case class
    println("Name = "+caseClass.name+ ""+"Age = "+caseClass.age)
    var updatedNameCaseClass = caseClass.copy(name= "Pankaj")
    println("Name Updated -> Name = "+updatedNameCaseClass.name+ ""+"Age = "+updatedNameCaseClass.age)
    var updatedAgeCaseClass = updatedNameCaseClass.copy(age= 40)
    println("Age Updated -> Name = "+updatedAgeCaseClass.name+ ""+"Age = "+updatedAgeCaseClass.age)

    //Pattern Matching
    processCasePerson(updatedAgeCaseClass)
    processCasePerson(caseClass)
  }
  //Pattern Matching
  def processCasePerson(cp: CaseClass): Unit = cp match {
    case CaseClass("Pankaj", _) => println("Pankaj found!")
    case _ => println("Unknown case person")
  }
}
