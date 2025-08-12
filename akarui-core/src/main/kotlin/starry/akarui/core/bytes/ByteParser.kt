package starry.akarui.core.bytes

import starry.akarui.core.Parser
import starry.akarui.core.ParserState

fun interface ByteParser<R> : Parser<Byte, R> {

    companion object {
        fun <T> sequence(name: String, block: context(ParserState<Byte>) ByteParser<T>.() -> T) =
            object : ByteParser<T> {
                override val parserName = name

                context(state: ParserState<Byte>)
                override fun parse() = block()
            }
    }

}
