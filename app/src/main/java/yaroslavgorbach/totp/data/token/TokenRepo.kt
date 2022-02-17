package yaroslavgorbach.totp.data.token

import kotlinx.coroutines.flow.Flow
import yaroslavgorbach.totp.data.token.local.model.Token

interface TokenRepo {
    fun observe(): Flow<List<Token>>
}