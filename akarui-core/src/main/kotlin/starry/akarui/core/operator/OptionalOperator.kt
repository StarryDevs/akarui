package starry.akarui.core.operator

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
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

fun <S, R> Parser<S, Result<R>>.mapToOption() = map {
    it.map(::Some).getOrElse { None }
}

@JvmName("orElseResult")
fun <S, R> Parser<S, Result<R>>.orElse(block: context(ParserState<S>) (Throwable) -> R) =
    map { it.fold({ result -> result }, { error -> block(error) }) }

@JvmName("orElseResult")
fun <S, R> Parser<S, Result<R>>.orElse(parser: Parser<S, R>) =
    orElse { +parser }

@JvmName("orElseOption")
fun <S, R> Parser<S, Option<R>>.orElse(block: context(ParserState<S>) () -> R) =
    map { it.fold({ block() }, { result -> result }) }

@JvmName("orElseOption")
fun <S, R> Parser<S, Option<R>>.orElse(parser: Parser<S, R>) =
    orElse { +parser }

