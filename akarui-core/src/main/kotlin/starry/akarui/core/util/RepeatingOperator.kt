package starry.akarui.core.util

import starry.akarui.core.Parser
import starry.akarui.core.ParserState

class RepeatingOperator<S, R>(val element: Parser<S, R>, val min: Int = 0, val max: Int = Int.MAX_VALUE) : Parser<S, List<R>> {

    context(state: ParserState<S>)
    override fun parse(): List<R> {
        val list = mutableListOf<R>()
        if (max == 0 || max < min)
            throw makeError("Maximum times must be greater than 0 and not less than minimum times.")
        for (number in 1..max) {
            val optional = +(element.optional())
            val result = optional.getOrNull()
            if (optional.isFailure && list.size < min) throw makeError(
                "Expected at least $min times, but got ${list.size}",
                optional.exceptionOrNull()
            )
            if (optional.isFailure || result == null) break
            else list += result
        }
        if (list.size < min)
            throw makeError("Expected at least $min times, but got ${list.size}")
        return list.toList()
    }

}

fun <S, R> Parser<S, R>.repeat(min: Int = 0, max: Int = Int.MAX_VALUE) =
    RepeatingOperator(this, min, max)

fun <S, R> Parser<S, R>.repeat(range: IntRange) =
    RepeatingOperator(this, range.start, range.endInclusive)

