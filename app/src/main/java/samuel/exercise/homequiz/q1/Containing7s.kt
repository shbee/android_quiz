package samuel.exercise.homequiz.q1

object Containing7s {

    fun g(n: Int): Int {
        var count = 0
        for (i in 1..n) {
            if (has7s(i)) count++
        }
        return count
    }

    fun has7s(number: Int): Boolean {
        var quotient = number
        while (quotient > 0) {
            val remainder = quotient % 10
            if (remainder == 7) {
                return true
            }
            quotient /= 10
        }
        return false
    }
}