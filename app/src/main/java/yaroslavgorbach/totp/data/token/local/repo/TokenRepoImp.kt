package yaroslavgorbach.totp.data.token.local.repo

import kotlinx.coroutines.flow.*
import yaroslavgorbach.totp.data.token.local.dao.TokensDao
import yaroslavgorbach.totp.data.token.local.model.Token

class TokenRepoImp(private val tokensDao: TokensDao) : TokenRepo {

    override fun observe(): Flow<List<Token>> {
        return tokensDao.observe().map {
            it.ifEmpty { listOf(Token.Test) }
        }
    }

    override suspend fun insert(token: Token) {
        tokensDao.insert(listOf(token))
    }
}