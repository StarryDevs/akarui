package starry.akarui.core.chars

import starry.akarui.core.util.makeError
import starry.akarui.core.util.source

fun symbol(name: String) = CharParser.sequence("symbol($name)") {
    for (char in name) {
        val current = source.next()
        if (current != char) throw makeError("Expected '$char', but got '$current'")
    }
    name
}

fun character(predicate: (Char) -> Boolean) = CharParser.sequence("character") {
    val current = source.next()
    if (!predicate(current)) throw makeError("Invalid character '$current'")
    current
}
