package starry.akarui.core.operator

import starry.akarui.core.Parser
import starry.akarui.core.ParserSource
import starry.akarui.core.ParserState

context(_: ParserState<S>)
fun <S, R> include(other: Parser<S, R>): R =
    other.parse()

context(_: ParserState<S>)
operator fun <S, T> Parser<S, T>.unaryPlus(): T =
    parse()

context(parserState: ParserState<S>)
val <S> state
    get() = parserState

context(_: ParserState<S>)
val <S> source
    get() = state.source

context(_: ParserState<S>)
fun <S> Parser<S, *>.test(): Boolean {
    val history = state.mark()
    try {
        parse()
        return true
    } catch (e: Exception) {
        return false
    } finally {
        state.rollback(history)
    }
}

fun <S, R> ParserSource<S>.parse(parser: Parser<S, R>) = context(ParserState(this)) {
    parser.parse()
}
