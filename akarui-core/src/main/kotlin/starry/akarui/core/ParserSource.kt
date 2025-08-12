package starry.akarui.core

interface ParserSource<T> : Iterable<T>, Iterator<T> {

    val size: Long
    var position: Long

    operator fun get(index: Long): T

    fun peek(offset: Long = 0): T = get(position + offset)
    fun skip(count: Long) {
        position += count
    }

    override fun next() = get(position++)
    override fun hasNext() = position < size
    override fun iterator() = slice()

    fun slice() = slice(position, size)

    fun slice(start: Long, end: Long) = object : ParserSource<T> {
        override var position: Long = 0
        override val size: Long = end - start
        override fun get(index: Long): T = this@ParserSource[start + index]
    }

}
