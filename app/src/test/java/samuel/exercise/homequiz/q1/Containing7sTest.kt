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
        assertEquals(65, Containing7s.g(370))
        assertEquals(71, Containing7s.g(376))
        assertEquals(72, Containing7s.g(377))
        assertEquals(73, Containing7s.g(378))
        assertEquals(271, Containing7s.g(1000))
        assertEquals(476, Containing7s.g(1771))
        assertEquals(496, Containing7s.g(1791))
    }

    @Test
    fun has7s() {
        assertFalse(Containing7s.has7s(1))
        assertFalse(Containing7s.has7s(18))
        assertTrue(Containing7s.has7s(7))
        assertTrue(Containing7s.has7s(700))
    }

    @Test
    fun gOptimize() {
        assertEquals(0, Containing7s.gOptimize(-1))
        assertEquals(0, Containing7s.gOptimize(0))
        assertEquals(0, Containing7s.gOptimize(1))
        assertEquals(1, Containing7s.gOptimize(7))
        assertEquals(2, Containing7s.gOptimize(20))
        assertEquals(8, Containing7s.gOptimize(70))
        assertEquals(19, Containing7s.gOptimize(100))
        assertEquals(38, Containing7s.gOptimize(200))
        assertEquals(55, Containing7s.gOptimize(280))
        assertEquals(19, Containing7s.gOptimize(100))
        assertEquals(38, Containing7s.gOptimize(200))
        assertEquals(55, Containing7s.gOptimize(280))
        assertEquals(65, Containing7s.gOptimize(370))
        assertEquals(71, Containing7s.gOptimize(376))
        assertEquals(72, Containing7s.gOptimize(377))
        assertEquals(73, Containing7s.gOptimize(378))
        assertEquals(271, Containing7s.gOptimize(1000))
        assertEquals(476, Containing7s.gOptimize(1771))
        assertEquals(496, Containing7s.gOptimize(1791))
    }
}