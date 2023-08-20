import scala.collection.mutable

class TreeNode(var _value: Int) {
  var left: TreeNode = null
  var right: TreeNode = null
}

object BinaryTreeLevelOrderTraversal {
  def levelOrder(root: TreeNode): List[List[Int]] = {
    if (root == null) return List()

    val result = scala.collection.mutable.ListBuffer[List[Int]]()
    val queue = mutable.Queue[TreeNode]()

    queue.enqueue(root)

    while (queue.nonEmpty) {
      val currentLevel = queue.size
      val levelValues = scala.collection.mutable.ListBuffer[Int]()

      for (_ <- 0 until currentLevel) {
        val node = queue.dequeue()
        levelValues += node._value

        if (node.left != null) queue.enqueue(node.left)
        if (node.right != null) queue.enqueue(node.right)
      }

      result += levelValues.toList
    }
    result.toList
  }

  def main(args: Array[String]): Unit = {
    // Construct a binary tree
    val root = new TreeNode(3)
    root.left = new TreeNode(9)
    root.right = new TreeNode(20)
    root.right.left = new TreeNode(15)
    root.right.right = new TreeNode(7)

    val traversalResult = levelOrder(root)
    traversalResult.foreach(level => println(level.mkString("[", ", ", "]")))
  }
}
