package samuel.exercise.homequiz.q1

import org.junit.Assert.*
import org.junit.Test

class Containing7sTest {

    @Test
    fun g() {
        assertEquals(0, Containing7s.g(-1))
        assertEquals(0, Containing7s.g(0))
        assertEquals(0, Containing7s.g(1))
        assertEquals(1, Containing7s.g(7))
        assertEquals(2, Containing7s.g(20))
        assertEquals(8, Containing7s.g(70))
        assertEquals(19, Containing7s.g(100))
        assertEquals(38, Containing7s.g(200))
        assertEquals(55, Containing7s.g(280))
        assertEquals(271, Containing7s.g(1000))
    }

    @Test
    fun has7s() {
        assertFalse(Containing7s.has7s(1))
        assertFalse(Containing7s.has7s(18))
        assertTrue(Containing7s.has7s(7))
        assertTrue(Containing7s.has7s(700))
    }
}