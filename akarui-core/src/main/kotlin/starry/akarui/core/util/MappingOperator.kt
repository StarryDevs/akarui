package starry.akarui.core.util

import starry.akarui.core.Parser
import starry.akarui.core.ParserState

class MappingOperator<S, R, T>(val parser: Parser<S, R>, val mapping: context(ParserState<S>) (R) -> T) : Parser<S, T> {

    context(state: ParserState<S>)
    override fun parse(): T =
        mapping(+parser)

}

fun <S, R, T> Parser<S, R>.map(mapping: context(ParserState<S>) (R) -> T) =
    MappingOperator(this, mapping)

fun <S, R, T> Parser<S, Iterable<R>>.mapEach(mapping: context(ParserState<S>) (R) -> T) =
    map { iterable ->
        iterable.map { mapping(it) }
    }

fun <S, R, T> Parser<S, Iterable<R>>.mapEachIndexed(mapping: context(ParserState<S>) (R, index: Int) -> T) =
    map { iterable ->
        iterable.mapIndexed { index, element -> mapping(element, index) }
    }
