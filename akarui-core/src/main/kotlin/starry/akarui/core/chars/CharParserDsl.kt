package starry.akarui.core.chars

import starry.akarui.core.operator.source

fun symbol(name: String) = CharParser.sequence("Symbol[$name]") {
    for (char in name) {
        val current = source.next()
        if (current != char)
            throw makeError("Expected '$char', but got '$current'")
    }
    name
}

fun character(predicate: (Char) -> Boolean) = CharParser.sequence("Character") {
    val current = source.next()
    if (!predicate(current))
        throw makeError("Invalid character '$current'")
    current
}
