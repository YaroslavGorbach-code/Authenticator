package yaroslavgorbach.totp.data.token

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import yaroslavgorbach.totp.data.token.local.model.Token

class TokenRepoImp(private val context: Context) : TokenRepo {
    private val testTokens = listOf(Token.Test, Token.Test)

    override fun observe(): Flow<List<Token>> {
        return flowOf(testTokens)
    }
}