package yaroslavgorbach.totp.data.token.local

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import yaroslavgorbach.totp.data.token.local.dao.TokensDao
import yaroslavgorbach.totp.data.token.local.model.Token

@TypeConverters(ExerciseNameConverter::class, DateConverter::class)
@Database(
    entities = [Token::class],
    version = 1
)
abstract class TokensDatabase : RoomDatabase() {
    companion object {

        @Volatile
        private var INSTANCE: TokensDatabase? = null

        fun getInstance(context: Application): TokensDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        TokensDatabase::class.java,
                        "db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE!!
        }
    }

    abstract val tokens: TokensDao
}