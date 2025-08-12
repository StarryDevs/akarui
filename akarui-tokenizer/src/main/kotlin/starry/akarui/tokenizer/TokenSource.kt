package starry.akarui.tokenizer

import starry.akarui.core.ParserSource

class TokenSource private constructor(private val all: List<Token>) : ParserSource<Token> {

    companion object {
        fun wrap(tokens: Iterable<Token>) = TokenSource(tokens.toList())
        fun empty() = TokenSource(listOf())
    }

    override var position: Long = 0

    override val size: Long
        get() = all.size.toLong()

    override fun get(index: Long) = all[index.toInt()]

}
