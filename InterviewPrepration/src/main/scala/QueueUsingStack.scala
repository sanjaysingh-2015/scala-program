import scala.collection.mutable

class QueueUsingStack {
  private val stack1 = mutable.Stack[Int]()
  private val stack2 = mutable.Stack[Int]()

  def isEmpty: Boolean = stack1.isEmpty && stack2.isEmpty

  def enqueue(x: Int): Unit = {
      stack1.push(x)
  }


  def dequeue(): Int = {
    if(isEmpty) {
      throw new NoSuchElementException("Queue is empty")
    }

    if(stack2.isEmpty) {
      while(stack1.nonEmpty) {
        stack2.push(stack1.pop())
      }
    }
    stack2.pop()
  }

  def front(): Int = {
    if(isEmpty) {
      throw new NoSuchElementException ("Queue is empty")
    }

    if (stack2.isEmpty) {
      while (stack1.nonEmpty) {
        stack2.push(stack1.pop())
      }
    }
    stack2.top
  }
}

object Main extends App {
  val queue = new QueueUsingStack()

  queue.enqueue(1)
  queue.enqueue(2)
  queue.enqueue(3)

  println(queue.dequeue()) // Output: 3
  println(queue.dequeue()) // Output: 2

  queue.enqueue(4)

  println((queue.front()))
  println(queue.dequeue()) // Output: 4
  println(queue.isEmpty) // Output: false
}