package org.example

import java.io.FileInputStream

fun main() {
    val parser = MulParser()
    val computer = MulComputer()

    parser.parse(FileInputStream("input.txt").buffered())
        .let { computer.compute(it, toggleMuls = false) }
        .let { println("Sum of multiples: $it") }

    parser.parse(FileInputStream("input.txt").buffered())
        .let { computer.compute(it, toggleMuls = true) }
        .let { println("Sum of multiples, acknowledging dos and don'ts: $it") }
}