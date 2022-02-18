package yaroslavgorbach.totp.data.token.local.factory

import android.net.Uri
import yaroslavgorbach.totp.data.token.local.model.HashAlgorithm
import yaroslavgorbach.totp.data.token.local.model.Token
import java.util.*
import javax.crypto.Mac

object TokenFactory {

    fun create(uri: Uri): Token {
        doOnValidTotpUri(uri) {
            var path = uri.path

            // Strip the path of its leading '/'
            var j = 0
            while (path!![j] == '/') {
                path = path.substring(1)
                j++
            }

            val i = path.indexOf(':')
            val issuerExt = if (i < 0) "" else path.substring(0, i)
            val issuerInt = uri.getQueryParameter("issuer")

            val issuer = if (issuerInt != null && issuerInt.isNotBlank()) issuerInt else issuerExt
            val label = path.substring(if (i >= 0) i + 1 else 0)

            var algo = uri.getQueryParameter("algorithm")
            if (algo == null) algo = "sha1"
            algo = algo.uppercase(Locale.getDefault())

            Mac.getInstance("Hmac$algo")

            var d = uri.getQueryParameter("digits")
            if (d == null) {
                d = if (issuerExt == "Steam") "5" else "6"
            }
            val digits = d.toInt()
            if (issuerExt != "Steam" && digits != 6 && digits != 7 && digits != 8 && digits != 5)
                throw IllegalArgumentException("Digits must be 5 to 8")

            var p = uri.getQueryParameter("period")
            if (p == null) p = "30"
            val period = p.toInt()

            var c = uri.getQueryParameter("counter")
            if (c == null) c = "0"

            val counter = c.toLong() - 1

            val secret = uri.getQueryParameter("secret") ?: throw IllegalArgumentException("Secret is null")

            return Token(
                id = 0,
                ordinal = -System.currentTimeMillis(), // One way to make the token to the top of the list
                issuer = issuer,
                label = label,
                algorithm = HashAlgorithm.SHA1,
                secret = secret,
                digits = digits,
                counter = counter,
                period = period,
            )
        }
        throw IllegalArgumentException("URI is not valid")
    }

    fun create(label: String, algorithm: HashAlgorithm, secret: String, digits: Int, counter: Long, period: Int): Token {
        return Token(
            id = 0,
            ordinal = -System.currentTimeMillis(), // One way to make the token to the top of the list
            label = label,
            algorithm = algorithm,
            secret = secret,
            digits = digits,
            counter = counter,
            period = period,
            issuer = null
        )
    }

    private inline fun doOnValidTotpUri(uri: Uri, onValid: () -> Unit) {
        if (uri.scheme != "otpauth")
            throw IllegalArgumentException("URI does not starts with otpauth")

        if (uri.authority != "totp")
            throw IllegalArgumentException("URI does not contain totp type")

        if (uri.path == null)
            throw IllegalArgumentException("Token path is null")

        if (uri.path!!.isEmpty())
            throw IllegalArgumentException("Token path is empty")

        onValid()
    }
}