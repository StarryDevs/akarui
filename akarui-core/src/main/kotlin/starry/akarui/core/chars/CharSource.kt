package starry.akarui.core.chars

import starry.akarui.core.ParserSource

class CharSource private constructor(private val all: CharArray) : ParserSource<Char> {

    companion object {
        fun wrap(charArray: CharArray) = CharSource(charArray)
        fun wrap(string: CharSequence) = CharSource(string.toList().toCharArray())
        fun empty() = CharSource(charArrayOf())
    }

    override var position: Long = 0

    override val size: Long
        get() = all.size.toLong()

    override fun get(index: Long) = all[index.toInt()]

}

