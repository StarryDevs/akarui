package starry.akarui.core.operator

import starry.akarui.core.Parser
import starry.akarui.core.ParserState

class MappingOperator<S, R, T>(val parser: Parser<S, R>, val mapping: context(ParserState<S>) (R) -> T) : Parser<S, T> {

    context(state: ParserState<S>)
    override fun parse(): T =
        mapping(+parser)

}

fun <S, R, T> Parser<S, R>.map(mapping: context(ParserState<S>) (R) -> T) =
    MappingOperator(this, mapping)

@JvmName("mapEachIterable")
fun <S, R, T> Parser<S, Iterable<R>>.mapEach(mapping: context(ParserState<S>) (R) -> T) =
    map { iterable ->
        iterable.map { mapping(it) }
    }

@JvmName("mapEachIndexedIterable")
fun <S, R, T> Parser<S, Iterable<R>>.mapEachIndexed(mapping: context(ParserState<S>) (R, index: Int) -> T) =
    map { iterable ->
        iterable.mapIndexed { index, element -> mapping(element, index) }
    }

@JvmName("mapEachIterator")
fun <S, R, T> Parser<S, Iterator<R>>.mapEach(mapping: context(ParserState<S>) (R) -> T) =
    map { iterator ->
        iterator.asSequence().map { mapping(it) }.iterator()
    }

@JvmName("mapEachIndexedIterator")
fun <S, R, T> Parser<S, Iterator<R>>.mapEachIndexed(mapping: context(ParserState<S>) (R, index: Int) -> T) =
    map { iterator ->
        iterator.asSequence().mapIndexed { index, element -> mapping(element, index) }.iterator()
    }

@JvmName("mapEachSequence")
fun <S, R, T> Parser<S, Sequence<R>>.mapEach(mapping: context(ParserState<S>) (R) -> T) =
    map { sequence ->
        sequence.map { mapping(it) }
    }

@JvmName("mapEachIndexedSequence")
fun <S, R, T> Parser<S, Sequence<R>>.mapEachIndexed(mapping: context(ParserState<S>) (R, index: Int) -> T) =
    map { sequence ->
        sequence.mapIndexed { index, element -> mapping(element, index) }
    }

