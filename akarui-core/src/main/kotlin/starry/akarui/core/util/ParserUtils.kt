package starry.akarui.core.util

import starry.akarui.core.Parser
import starry.akarui.core.ParserSource
import starry.akarui.core.ParserState
import starry.akarui.core.exception.ParserException
import starry.akarui.core.exception.SyntaxError


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

fun <S, R> ParserSource<S>.parse(parser: Parser<S, R>) = with(ParserState(this)) {
    parser.parse()
}

context(_: ParserState<S>)
fun <S, R> Parser<S, R>.makeError(message: String?, cause: Throwable? = null) =
    ParserException(message, state, state.mark(), this, cause)

context(_: ParserState<S>)
fun <S, R> Parser<S, R>.makeSyntaxError(message: String?, cause: Throwable? = null) =
    SyntaxError(message, state, state.mark(), this, cause)
