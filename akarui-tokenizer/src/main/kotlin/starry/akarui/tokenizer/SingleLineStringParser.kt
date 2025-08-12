package starry.akarui.tokenizer

import starry.akarui.core.ParserState
import starry.akarui.core.chars.CharParser
import starry.akarui.core.chars.CharSource
import starry.akarui.core.chars.symbol
import starry.akarui.core.operator.parse
import starry.akarui.core.operator.source
import starry.akarui.core.operator.unaryPlus
import starry.akarui.tokenizer.tokens.StringToken

enum class SingleLineStringParser(val quote: Char, val symbol: String) : CharParser<StringToken> {
    SINGLE_QUOTE('\'', "\"'\""),
    DOUBLE_QUOTE('"', "'\"'");

    context(state: ParserState<Char>)
    override fun parse(): StringToken {
        val start = source.position
        +symbol(quote.toString())
        var content = ""
        while (source.hasNext()) {
            when (val character = source.next()) {
                quote -> return StringToken(
                    content,
                    source.slice(start, source.position).toList().toCharArray().concatToString()
                )
                '\\' -> {
                    if (!source.hasNext())
                        throw makeError("Unterminated escape")
                    val esc = source.next()
                    content += when (esc) {
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
                                if (!source.hasNext())
                                    throw makeError("Invalid \\x escape")
                                source.next()
                            }
                            hex.concatToString().toInt(16).toChar()
                        }
                        'u' -> {
                            val hex = CharArray(4) {
                                if (!source.hasNext())
                                    throw makeError("Invalid \\u escape")
                                source.next()
                            }
                            hex.concatToString().toInt(16).toChar()
                        }

                        else -> throw makeError("Invalid escape: \\$esc")
                    }

                }

                '\n', '\r' -> throw makeError("Single-quoted string cannot contain line breaks")
                else -> content += character
            }
        }
        throw makeError("Unterminated string literal: expected $symbol")
    }

    override val parserName = "${super.parserName}[$symbol]"

}

fun main() {
    CharSource.wrap("'hello, world!'")
        .parse(SingleLineStringParser.SINGLE_QUOTE)
        .also(::println)
}
