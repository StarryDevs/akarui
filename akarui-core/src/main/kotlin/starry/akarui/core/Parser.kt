package starry.akarui.core

interface Parser<S, R> {

    val parserName: String
        get() = this::class.simpleName ?: "Anonymous"

    context(state: ParserState<S>)
    fun parse(): R

}
