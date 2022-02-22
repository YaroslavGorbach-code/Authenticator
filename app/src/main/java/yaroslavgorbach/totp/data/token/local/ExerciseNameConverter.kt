package yaroslavgorbach.totp.data.token.local

import androidx.room.TypeConverter
import yaroslavgorbach.totp.data.token.local.model.HashAlgorithm

object ExerciseNameConverter {
    @TypeConverter
    fun toHashAlgorithm(value: String) = enumValueOf<HashAlgorithm>(value)

    @TypeConverter
    fun fromHashAlgorithm(value: HashAlgorithm) = value.name
}