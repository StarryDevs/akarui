package starry.akarui.core.operator

import starry.akarui.core.Parser
import starry.akarui.core.ParserState

class ListOperator<S, E>(
    val element: Parser<S, E>,
    val prefix: Parser<S, *>? = null,
    val suffix: Parser<S, *>? = null,
    val separator: Parser<S, *>? = null,
    val whitespace: Parser<S, *>? = null,
    val allowSeparatorEnd: Boolean = false
) : Parser<S, List<E>> {

    inner class Part : Parser<S, E> {
        override val parserName: String = "${this@ListOperator.parserName}.Part"

        context(state: ParserState<S>)
        override fun parse(): E {
            if (whitespace != null) +whitespace
            if (separator != null) +separator
            if (whitespace != null) +whitespace
            return +element
        }
    }

    val part = Part()

    context(state: ParserState<S>)
    override fun parse(): List<E> {
        if (prefix != null) +prefix
        val elements = mutableListOf<E>()
        if (whitespace != null) +whitespace
        val first = (+(element.optional())).getOrNull()
        if (whitespace != null) +whitespace
        if (first != null) {
            elements += first
            while (true) {
                if (whitespace != null) +whitespace
                val result = (+(part.optional())).getOrNull()
                if (whitespace != null) +whitespace
                if (result == null) break
                else elements += result
            }
        }
        if (allowSeparatorEnd && separator != null) {
            if (whitespace != null) +whitespace
            +separator.optional()
            if (whitespace != null) +whitespace
        }
        if (suffix != null) {
            +suffix
        }
        return elements
    }

}

fun <S, E> Parser<S, E>.list(
    prefix: Parser<S, *>? = null,
    suffix: Parser<S, *>? = null,
    separator: Parser<S, *>? = null,
    whitespace: Parser<S, *>? = null,
    allowSeparatorEnd: Boolean = false
) = ListOperator(this, prefix, suffix, separator, whitespace, allowSeparatorEnd)

