package yaroslavgorbach.totp.business.token

import kotlinx.coroutines.flow.Flow
import yaroslavgorbach.totp.data.token.local.repo.TokenRepo
import yaroslavgorbach.totp.data.token.local.model.Token
import javax.inject.Inject

class ObserveTokensInteractor @Inject constructor(private val tokenRepo: TokenRepo) {
    operator fun invoke(): Flow<List<Token>> = tokenRepo.observe()
}