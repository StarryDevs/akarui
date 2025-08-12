package starry.akarui.tokenizer.tokens

import starry.akarui.tokenizer.Token

data class StringToken(val value: String, override val raw: String) : Token
