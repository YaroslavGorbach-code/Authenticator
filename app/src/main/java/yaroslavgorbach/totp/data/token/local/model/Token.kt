package yaroslavgorbach.totp.data.token.local.model

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.codec.binary.Base32
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import yaroslavgorbach.totp.utill.TokenFormatter

data class Token(
    val id: Long,
    val ordinal: Long,
    val issuer: String?,
    val label: String,
    val algorithm: HashAlgorithm,
    val secret: String,
    val digits: Int,
    val counter: Long,
    val period: Int = 30,
) {
    val progress: SharedFlow<Float> = flow {
        while (true) {
            val p = (period * 1000).toLong()
            val result = p - (System.currentTimeMillis() % p)
            val progress = (100 * (result.toFloat() / (period * 1000))) / 100
            emit(progress)
            delay(100)
        }
    }.shareIn(scope = GlobalScope, started = SharingStarted.Eagerly, 0)

    val secretBytes: ByteArray
        get() = Base32().decode(secret)

    val formattedCode: String
        get() = TokenFormatter.getCode(this, 3)

    companion object {
        val Test = Token(
            id = -1,
            ordinal = 1,
            issuer = "",
            label = "Test",
            algorithm = HashAlgorithm.SHA1,
            secret = "JBSWY3DPEHPK3PXP",
            digits = 6,
            counter = 0,
            period = 30
        )
    }

}