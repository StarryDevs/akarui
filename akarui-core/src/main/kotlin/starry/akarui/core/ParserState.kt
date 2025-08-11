package starry.akarui.core

class ParserState<S>(val source: ParserSource<S>) {

    data class History(val position: Long)

    fun mark() = History(source.position)

    fun rollback(history: History) {
        source.position = history.position
    }

}
