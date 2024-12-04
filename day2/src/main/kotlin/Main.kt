package org.example

import java.io.FileInputStream

fun main() {
    useRawReports("input.txt") { println(countSafeReports(it)) }
    useRawReports("input.txt") { println(countSafeReports(it, dampen = true)) }
}

fun useRawReports(from: String, block: (Sequence<String>) -> Unit) {
    FileInputStream(from).bufferedReader().useLines(block)
}

fun countSafeReports(rawReports: Sequence<String>, dampen: Boolean = false): Int =
    SafeReportAnalyzer().let { analyzer ->
        rawReports.count { report ->
            report
                .split(" ")
                .map(String::toInt)
                .let { analyzer.isSafe(it, dampen) }
        }
    }