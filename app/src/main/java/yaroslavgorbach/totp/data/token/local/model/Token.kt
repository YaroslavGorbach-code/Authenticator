package yaroslavgorbach.totp.data.token.local.model

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.codec.binary.Base32
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import yaroslavgorbach.totp.utill.TimerCountDown
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
    private val millisUntilNextUpdate: Long
        get() = (period * 1000) - (System.currentTimeMillis()) % (period * 1000)

    val secretBytes: ByteArray
        get() = Base32().decode(secret)

    val formattedCode: String
        get() = TokenFormatter.getCode(this, 3)

    @DelicateCoroutinesApi
    val timerTillNextCode = TimerCountDown(coroutineScope = GlobalScope, millisInFuture = millisUntilNextUpdate).startTimer()

    companion object {
        val Test = Token(
            id = -1,
            ordinal = 1,
            issuer = "",
            label = "Test",
            algorithm = HashAlgorithm.SHA1,
            secret = "",
            digits = 0,
            counter = 0,
            period = 30
        )
    }

}