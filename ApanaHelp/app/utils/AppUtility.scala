package utils

import scala.util.Random

class AppUtility {
  def generateRandomInt(n: Int): Int = {
    require(n > 0, "Number of digits must be greater than 0")

    val minValue = math.pow(10, n - 1).toInt
    val maxValue = math.pow(10, n).toInt - 1

    val randomInt = Random.nextInt(maxValue - minValue + 1) + minValue
    randomInt
  }
}
