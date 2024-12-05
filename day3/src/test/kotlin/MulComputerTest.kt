import org.example.MulComputer
import org.example.MulParser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MulComputerTest {
    private val parser = MulParser()
    private val computer = MulComputer()

    @Test
    fun computesMuls() {
        val input = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"

        val output = computer.compute(parser.parse(input.byteInputStream()), toggleMuls = false)

        assertEquals(161, output)
    }

    @Test
    fun computesDosAndDonts() {
        val input = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"

        val output = computer.compute(parser.parse(input.byteInputStream()), toggleMuls = true)

        assertEquals(48, output)
    }
}