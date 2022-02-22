package yaroslavgorbach.totp.data.token.local.repo

import kotlinx.coroutines.flow.Flow
import yaroslavgorbach.totp.data.token.local.model.Token

interface TokenRepo {
    fun observe(): Flow<List<Token>>

    suspend fun insert(token: Token)
}