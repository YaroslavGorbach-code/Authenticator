package yaroslavgorbach.totp.data.token.local.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.codec.binary.Base32
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import yaroslavgorbach.totp.utill.TokenFormatter
import java.util.*

@Entity
data class Token(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: Date = Date(),
    val label: String,
    val algorithm: HashAlgorithm = HashAlgorithm.SHA1,
    val secret: String,
    val digits: Int = 6,
    val period: Int = 30,
) {
    @Ignore
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
            date = Date(),
            label = "Test",
            algorithm = HashAlgorithm.SHA1,
            secret = "JBSWY3DPEHPK3PXP",
            digits = 6,
            period = 30
        )
    }

}
