package starry.akarui.core.util

import starry.akarui.core.Parser
import starry.akarui.core.ParserState

class OptionalOperator<S, R>(val parser: Parser<S, R>, val rollback: Boolean = true) : Parser<S, Result<R>> {

    context(state: ParserState<S>)
    override fun parse(): Result<R> {
        val history = state.mark()
        try {
            val result = Result.success(+parser)
            return result
        } catch (throwable: Throwable) {
            if (rollback) {
                state.rollback(history)
            }
            return Result.failure(throwable)
        }
    }

}

fun <S, R> Parser<S, R>.optional(rollback: Boolean = true) =
    OptionalOperator(this, rollback)

fun <S, R> Parser<S, Result<R>>.orElse(block: context(ParserState<S>) (Throwable) -> R) =
    map { block(it.exceptionOrNull()!!) }

fun <S, R> Parser<S, Result<R>>.orElse(parser: Parser<S, R>) =
    orElse { +parser }
