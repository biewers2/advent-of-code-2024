import org.example.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MulParserTest {
    private val parser = MulParser()

    @Test
    fun parsesMul() {
        val input = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"

        val nodes = parser.parse(input.byteInputStream())

        assertEquals(4, nodes.size)
        nodes
            .zip(listOf(
                MulNode(MulFactorNode(2), MulFactorNode(4)),
                MulNode(MulFactorNode(5), MulFactorNode(5)),
                MulNode(MulFactorNode(11), MulFactorNode(8)),
                MulNode(MulFactorNode(8), MulFactorNode(5))
            ))
            .forEach { (actual, expected) ->
                assertEquals(expected, actual)
            }
    }

    @Test
    fun parsesDosAndDonts() {
        val input = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"

        val nodes = parser.parse(input.byteInputStream())

        assertEquals(6, nodes.size)
        nodes
            .zip(listOf(
                MulNode(MulFactorNode(2), MulFactorNode(4)),
                DontNode(),
                MulNode(MulFactorNode(5), MulFactorNode(5)),
                MulNode(MulFactorNode(11), MulFactorNode(8)),
                DoNode(),
                MulNode(MulFactorNode(8), MulFactorNode(5))
            ))
            .forEach { (actual, expected) ->
                assertEquals(expected, actual)
            }
    }
}