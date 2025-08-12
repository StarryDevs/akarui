package starry.akarui.core.bytes

import starry.akarui.core.operator.source

fun symbol(name: ByteArray) = ByteParser.sequence("Symbol[$name]") {
    for (byte in name) {
        val current = source.next()
        if (current != byte)
            throw makeError("Expected '$byte', but got '$current'")
    }
    name
}

fun byte(predicate: (Byte) -> Boolean) = ByteParser.sequence("Byte") {
    val current = source.next()
    if (!predicate(current))
        throw makeError("Invalid character '$current'")
    current
}
