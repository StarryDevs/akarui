package starry.akarui.core.exception

import starry.akarui.core.Parser
import starry.akarui.core.ParserState

class SyntaxError(message: String?, state: ParserState<*>, history: ParserState.History, parser: Parser<*, *>, cause: Throwable? = null) :
    ParserException(message, state, history, parser, cause)
