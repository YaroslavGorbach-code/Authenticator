package yaroslavgorbach.totp.data.token.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import yaroslavgorbach.totp.data.token.local.model.Token

@Dao
interface TokensDao {

    @Query("SELECT * FROM Token")
    fun observe(): Flow<List<Token>>

    @Insert(onConflict = REPLACE)
    suspend fun insert(exercises: List<Token>)
}