package org.example

import java.io.InputStream

class MulParser {
    private enum class Keyword(val bytes: ByteArray) {
        DO("do".toByteArray()),
        MUL("mul".toByteArray()),
        DONT("don't".toByteArray())
    }

    fun parse(input: InputStream): List<AstNode> {
        val buf = if (!input.markSupported()) input.buffered() else input

        val nodes = mutableListOf<AstNode>()
        while (buf.available() > 0) {
            val keyword = detectKeyword(buf)
            if (keyword == null) {
                buf.skip(1)
                continue
            }

            buf.mark(0)
            when (keyword) {
                Keyword.DO -> parseDo(buf)
                Keyword.MUL -> parseMul(buf)
                Keyword.DONT -> parseDont(buf)
            }
                ?.let { nodes.add(it) }
                ?:let {
                    buf.reset()
                    buf.skip(1)
                }
        }
        return nodes
    }

    private fun detectKeyword(input: InputStream): Keyword? {
        input.mark(0)
        val bs = input.readNBytes(5)

        try {
            if (bs.size >= 5 && bs.slice(0..4).toByteArray().contentEquals(Keyword.DONT.bytes))
                return Keyword.DONT

            if (bs.size >= 3 && bs.slice(0..2).toByteArray().contentEquals(Keyword.MUL.bytes))
                return Keyword.MUL

            if (bs.size >= 2 && bs.slice(0..1).toByteArray().contentEquals(Keyword.DO.bytes))
                return Keyword.DO

            return null
        } finally {
            input.reset()
        }
    }

    private fun parseDo(input: InputStream): DoNode? {
        val bytes = input.readNBytes(3)
        if (!bytes.contentEquals("do(".toByteArray()))
            return null

        if (input.read() != ')'.code)
            return null

        return DoNode()
    }

    private fun parseDont(input: InputStream): DontNode? {
        val bytes = input.readNBytes(6)
        if (!bytes.contentEquals("don't(".toByteArray()))
            return null

        if (input.read() != ')'.code)
            return null

        return DontNode()
    }

    private fun parseMul(input: InputStream): MulNode? {
        val bytes = input.readNBytes(4)
        if (!bytes.contentEquals("mul(".toByteArray()))
            return null

        val leftFactor = parseFactor(input) ?: return null
        if (input.read() != ','.code)
            return null

        val rightFactor = parseFactor(input) ?: return null
        if (input.read() != ')'.code)
            return null

        return MulNode(leftFactor, rightFactor)
    }

    private fun parseFactor(input: InputStream): MulFactorNode? =
        StringBuilder()
            .apply {
                for (i in 0..3) {
                    input.mark(0)
                    val b = input.read()
                    if (b == -1 || b !in '0'.code..'9'.code) {
                        input.reset()
                        break
                    }
                    append(b.toChar())
                }
            }
            .toString()
            .toIntOrNull()
            ?.let { MulFactorNode(it) }
}