package starry.akarui.core.bytes

import starry.akarui.core.ParserSource
import java.io.InputStream

class ByteSource private constructor(private val all: ByteArray) : ParserSource<Byte> {

    companion object {
        fun wrap(byteArray: ByteArray) = ByteSource(byteArray)
        fun wrap(stream: InputStream) = wrap(stream.use(InputStream::readAllBytes))
        fun empty() = ByteSource(byteArrayOf())
    }

    override var position: Long = 0

    override val size: Long
        get() = all.size.toLong()

    override fun get(index: Long) = all[index.toInt()]

}

