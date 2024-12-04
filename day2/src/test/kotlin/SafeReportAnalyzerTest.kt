import org.example.SafeReportAnalyzer
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SafeReportAnalyzerTest {
    private val analyzer = SafeReportAnalyzer()

    @Test
    fun isSafe() {
        listOf(
            Pair(listOf(7, 6, 4, 2, 1), true),
            Pair(listOf(1, 2, 7, 8, 9), false),
            Pair(listOf(9, 7, 6, 2, 1), false),
            Pair(listOf(1, 3, 2, 4, 5), false),
            Pair(listOf(8, 6, 4, 4, 1), false),
            Pair(listOf(1, 3, 6, 7, 9), true),
        ).forEach { (report, expected) ->
            val actual = analyzer.isSafe(report)
            assertEquals(expected, actual, "Expected $report to be $expected")
        }
    }

    @Test
    fun isSafeWithDampen() {
        listOf(
            Pair(listOf(), true),
            Pair(listOf(1), true),
            Pair(listOf(5, 6, 4, 2, 1), true),
            Pair(listOf(10, 6, 4, 2, 1), true),
            Pair(listOf(7, 6, 4, 2, 1), true),
            Pair(listOf(1, 2, 7, 8, 9), false),
            Pair(listOf(9, 7, 6, 2, 1), false),
            Pair(listOf(1, 3, 2, 4, 5), true),
            Pair(listOf(8, 6, 4, 4, 1), true),
            Pair(listOf(1, 3, 6, 7, 9), true),
            Pair(listOf(1, 3, 6, 7, 14), true),
            Pair(listOf(1, 3, 6, 7, 6), true),
        ).forEach { (report, expected) ->
            val actual = analyzer.isSafe(report, dampen = true)
            assertEquals(expected, actual, "Expected $report to be $expected")
        }
    }
}