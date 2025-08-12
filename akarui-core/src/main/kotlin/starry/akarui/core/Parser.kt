package starry.akarui.core

import starry.akarui.core.exception.ParserException
import starry.akarui.core.exception.SyntaxError
import starry.akarui.core.operator.state

interface Parser<S, R> {

    val parserName: String
        get() = this::class.simpleName ?: "Anonymous"

    context(state: ParserState<S>)
    fun parse(): R


    context(_: ParserState<S>)
    fun <S, R> Parser<S, R>.makeError(message: String?, cause: Throwable? = null) =
        ParserException(message, state, state.mark(), this, cause)

    context(_: ParserState<S>)
    fun <S, R> Parser<S, R>.makeSyntaxError(message: String?, cause: Throwable? = null) =
        SyntaxError(message, state, state.mark(), this, cause)

}
