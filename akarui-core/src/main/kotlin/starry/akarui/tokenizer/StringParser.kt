package starry.akarui.tokenizer

import starry.akarui.core.ParserState
import starry.akarui.core.chars.CharParser
import starry.akarui.core.chars.symbol
import starry.akarui.core.util.makeError
import starry.akarui.core.util.source
import starry.akarui.core.util.unaryPlus

enum class StringParser(val quote: Char, symbol: String) : CharParser<String> {
    SINGLE_QUOTE('\'', "\"'\""),
    DOUBLE_QUOTE('"', "'\"'");

    context(state: ParserState<Char>)
    override fun parse(): String {
        +symbol(quote.toString())
        val buffer = source
        val content = buildString {
            while (buffer.hasNext()) {
                val c = buffer.next()
                when (c) {
                    quote -> {
                        // 结束
                        break
                    }

                    '\\' -> {
                        if (!buffer.hasNext()) throw makeError("Unterminated escape")
                        val esc = buffer.next()
                        append(
                            when (esc) {
                                quote, '\\', '/' -> esc
                                'b' -> '\b'
                                'f' -> '\u000C'
                                'n' -> '\n'
                                'r' -> '\r'
                                't' -> '\t'
                                'v' -> '\u000B'
                                '0' -> '\u0000'
                                'x' -> {
                                    val hex = CharArray(2) {
                                        if (!buffer.hasNext()) throw makeError("Invalid \\x escape")
                                        buffer.next()
                                    }
                                    hex.concatToString().toInt(16).toChar()
                                }

                                'u' -> {
                                    val hex = CharArray(4) {
                                        if (!buffer.hasNext()) throw makeError("Invalid \\u escape")
                                        buffer.next()
                                    }
                                    hex.concatToString().toInt(16).toChar()
                                }

                                else -> throw makeError("Invalid escape: \\$esc")
                            }
                        )
                    }

                    '\n', '\r' -> throw makeError("Single-quoted string cannot contain line breaks")
                    else -> append(c)
                }
            }
        }
        return content
    }

    override val parserName = "StringParser[$symbol]"

}
