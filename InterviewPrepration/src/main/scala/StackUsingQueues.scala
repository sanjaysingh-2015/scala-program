import scala.collection.immutable.Nil.isEmpty
import scala.collection.mutable

class StackUsingQueues {
  private val queue1 = mutable.Queue[Int]()
  private val queue2 = mutable.Queue[Int]()

  def push(x: Int): Unit = {
    if (queue1.isEmpty) {
      queue2.enqueue(x)
    } else {
      queue1.enqueue(x)
    }
  }

  def pop(): Int = {
    if (isEmpty) {
      throw new NoSuchElementException("Stack is empty")
    }

    if (queue1.isEmpty) {
      while (queue2.size > 1) {
        queue1.enqueue(queue2.dequeue())
      }
      queue2.dequeue()
    } else {
      while (queue1.size > 1) {
        queue2.enqueue(queue1.dequeue())
      }
      queue1.dequeue()
    }
  }

  def top(): Int = {
    if (isEmpty) {
      throw new NoSuchElementException("Stack is empty")
    }

    if (queue1.isEmpty) {
      queue2.last
    } else {
      queue1.last
    }
  }

  def isEmpty: Boolean = queue1.isEmpty && queue2.isEmpty
}


object Main1 extends App {
  val stack = new StackUsingQueues()

  stack.push(1)
  stack.push(2)
  stack.push(3)

  println(stack.pop()) // Output: 3
  println(stack.pop()) // Output: 2

  stack.push(4)
  println(stack.top()) // Output: 4
  println(stack.pop()) // Output: 4

  println(stack.isEmpty) // Output: false
}