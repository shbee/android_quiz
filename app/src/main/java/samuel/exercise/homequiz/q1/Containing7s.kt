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

    fun gOptimize(n: Int): Int {
        if (n < 10) {
            return if (n < 7) 0 else 1
        }

        var quotient = n
        var power = 10
        var base7s = 1
        var remainder = 0
        var count = 0
        while (quotient >= 10) {
            // remainder to be count
            remainder += (quotient % 10) * power / 10
            quotient /= 10

            if (quotient < 10) {
                count = when {
                    quotient < 7 -> quotient * base7s  // ex. 1xx
                    quotient > 7 -> (quotient - 1) * base7s + power  // ex. 8xx
                    else -> {
                        // when quotient equal 7
                        var subtotal = remainder + 1  // add all remainder, 7xx
                        // calculate as 699
                        subtotal += (quotient - 1) * base7s
                        remainder = power - 1
                        subtotal
                    }
                }
            } else {
                base7s = 9 * base7s + power
                power *= 10
            }
        }
        count += gOptimize(remainder)

        return count
    }
}