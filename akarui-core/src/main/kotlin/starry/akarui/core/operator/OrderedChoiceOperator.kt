package starry.akarui.core.operator

import starry.akarui.core.Parser
import starry.akarui.core.ParserState
import starry.akarui.core.exception.SyntaxError

class OrderedChoiceOperator<S, R>(val choices: List<Parser<S, out R>>) : Parser<S, R> {

    context(state: ParserState<S>)
    override fun parse(): R {
        var exception: Throwable? = null
        for (choice in choices) {
            val result = +choice.optional()
            if (result.isSuccess) return result.getOrThrow()
            else if (result.exceptionOrNull() is SyntaxError) {
                exception = result.exceptionOrNull()
                break
            }
        }
        if (exception != null) throw exception
        else throw makeError("Invalid syntax")
    }

}

fun <S, R> choose(vararg choices: Parser<S, out R>) = OrderedChoiceOperator(choices.toList())

infix fun <S, T> Parser<S, out T>.or(other: Parser<S, out T>) = when {
    this is OrderedChoiceOperator<S, T> -> OrderedChoiceOperator(this.choices + other)
    other is OrderedChoiceOperator<S, T> -> OrderedChoiceOperator(listOf(this) + other.choices)
    else -> choose(this, other)
}
