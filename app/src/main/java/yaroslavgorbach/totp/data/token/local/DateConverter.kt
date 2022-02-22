package yaroslavgorbach.totp.data.token.local

import androidx.room.TypeConverter
import yaroslavgorbach.totp.data.token.local.model.HashAlgorithm
import java.util.*

object DateConverter {
    @TypeConverter
    fun toHashAlgorithm(value: Long) = Date(value)

    @TypeConverter
    fun fromHashAlgorithm(value: Date) = value.time
}