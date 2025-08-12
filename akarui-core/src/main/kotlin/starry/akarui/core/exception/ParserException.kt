package starry.akarui.core.exception

import starry.akarui.core.Parser
import starry.akarui.core.ParserState

open class ParserException(
    message: String?,
    val state: ParserState<*>,
    val history: ParserState.History,
    val parser: Parser<*, *>,
    cause: Throwable? = null
) :
    Exception(message, cause) {

    override val message: String = "An error occurred while parsing ${parser.parserName}: $message"

}
