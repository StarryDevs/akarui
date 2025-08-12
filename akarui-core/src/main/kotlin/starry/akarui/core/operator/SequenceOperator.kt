package starry.akarui.core.operator

import starry.akarui.core.Parser
import starry.akarui.core.ParserState

class SequenceOperator<S, R>(val name: String? = null, val block: context(ParserState<S>) SequenceOperator<S, R>.() -> R) : Parser<S, R> {

    override val parserName: String
        get() = name ?: super.parserName

    context(state: ParserState<S>)
    override fun parse(): R = block()

}

context(state: ParserState<S>)
fun <S, R> sequence(name: String? = null, block: context(ParserState<S>) SequenceOperator<S, R>.() -> R) =
    SequenceOperator(name, block)
