package org.example

import kotlin.math.absoluteValue

private enum class Direction {
    INCREASING,
    DECREASING,
    FLAT,
    SINGLE,
    EMPTY
}

/**
 * Problem: Figure out which reports are "safe". "Safe" is when both of the following are true:
 * 1. Levels are either _all increasing_ or _all decreasing_.
 * 2. Any two adjacent levels differ by  _at least one_ and _at most three_.
 * "Dampening" allows each report to accept at most one problematic level.
 *
 * Solution: Assert that the direction is correct for all levels in the report, and that the diff between each two
 * adjacent levels is within the 1 to 3 range, inclusive. For dampening, if the `indexOfUnsafe` function returns an
 * index, check the same for when the `index - 1`, `index`, and `index + 1` values are removed.
 */
class SafeReportAnalyzer {
    fun isSafe(report: List<Int>, dampen: Boolean = false): Boolean =
        indexOfUnsafe(report)
            ?.let { dampen && listOf(it - 1, it, it + 1).any { i ->
                isSafeExcludingIndex(report, i)
            } }
            ?: true

    private fun isSafeExcludingIndex(report: List<Int>, index: Int): Boolean {
        val newList = mutableListOf<Int>().apply {
            addAll(report.slice(0 until index))
            addAll(report.slice(index + 1 until report.size))
        }
        return indexOfUnsafe(newList) == null
    }

    private fun indexOfUnsafe(report: List<Int>): Int? {
        val direction = direction(report)
        when (direction) {
            Direction.EMPTY, Direction.SINGLE -> return null
            Direction.FLAT -> return 0
            else -> Unit
        }

        return report
            .zipWithNext()
            .withIndex()
            .firstOrNull { indexed ->
                val (a, b) = indexed.value
                direction(listOf(a, b)) != direction || (a - b).absoluteValue !in 1..3
            }
            ?.index
    }

    private fun direction(values: List<Int>): Direction =
        when {
            values.isEmpty() -> Direction.EMPTY
            values.size == 1 -> Direction.SINGLE
            values.first() < values.last() -> Direction.INCREASING
            values.first() > values.last() -> Direction.DECREASING
            else -> Direction.FLAT
        }
}