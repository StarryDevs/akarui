package starry.akarui.core.chars

import starry.akarui.core.ParserState
import starry.akarui.core.Parser

fun interface CharParser <R> : Parser<Char, R> {

    companion object {
        fun <T> sequence(name: String, block: context(ParserState<Char>) CharParser<T>.() -> T) = object : CharParser<T> {
            override val parserName = name

            context(state: ParserState<Char>)
            override fun parse() = block()
        }
    }

}
