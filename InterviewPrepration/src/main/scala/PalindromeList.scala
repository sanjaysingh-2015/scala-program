import java.util

class ListNode(var _value: Int) {
  var next: ListNode = null
}

object PalindromeLinkedList {
  def isPalindrome(head: ListNode): Boolean = {
    def reverseList(node: ListNode): ListNode = {
      var prev: ListNode = null
      var current = node
      while (current != null) {
        val nextTemp = current.next
        current.next = prev
        prev = current
        current = nextTemp
      }
      prev
    }

    var slow = head
    var fast = head

    while (fast != null && fast.next != null) {
      slow = slow.next
      fast = fast.next.next
    }
    var secondHalf = reverseList(slow)
    var firstHalf = head

    while (secondHalf != null) {
      if (firstHalf._value != secondHalf._value) {
        return false
      }
      firstHalf = firstHalf.next
      secondHalf = secondHalf.next
    }
    true
  }

}
