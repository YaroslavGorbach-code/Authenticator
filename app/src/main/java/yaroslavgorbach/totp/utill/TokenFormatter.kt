package yaroslavgorbach.totp.utill

import yaroslavgorbach.totp.data.token.local.model.HashAlgorithm
import yaroslavgorbach.totp.data.token.local.model.Token
import java.nio.ByteBuffer
import java.text.NumberFormat
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.math.pow

object TokenFormatter {

    fun getCode(
        token: Token,
        chunkSize: Int
    ): String {
        return formatToken(formatTokenString(prepareToken(token), token.digits), chunkSize)
    }

    private fun formatToken(s: String, chunkSize: Int): String = s.chunked(chunkSize).joinToString(" ")

    private fun prepareToken(token: Token): Int {
        val fullToken = TOTP(token.secretBytes, token.period, (System.currentTimeMillis() / 1000), token.algorithm)
        val div = 10.0.pow(token.digits.toDouble()).toInt()

        return fullToken % div
    }

    private fun TOTP(key: ByteArray, period: Int, time: Long, algorithm: HashAlgorithm): Int {
        return HOTP(key, time / period, algorithm)
    }

    private fun HOTP(key: ByteArray, counter: Long, algorithm: HashAlgorithm): Int {
        val data = ByteBuffer.allocate(8).putLong(counter).array()
        return generateHash(algorithm, key, data).truncate()
    }

    private fun generateHash(algorithm: HashAlgorithm, key: ByteArray, data: ByteArray): ByteArray {
        val algo = "Hmac$algorithm"

        val mac = Mac.getInstance(algo)
        mac.init(SecretKeySpec(key, algo))

        return mac.doFinal(data)
    }

    private fun ByteArray.truncate(): Int {
        val offset = (this[this.size - 1].toInt() and 0xf)
        return (this[offset].toInt() and 0x7f shl 24
                or (this[offset + 1].toInt() and 0xff shl 16)
                or (this[offset + 2].toInt() and 0xff shl 8)
                or (this[offset + 3].toInt() and 0xff))
    }


    private fun formatTokenString(token: Int, digits: Int): String {
        val numberFormat = NumberFormat.getInstance(Locale.ENGLISH)
        numberFormat.minimumIntegerDigits = digits
        numberFormat.isGroupingUsed = false

        return numberFormat.format(token.toLong())
    }
}