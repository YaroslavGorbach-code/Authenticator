package yaroslavgorbach.totp.business.token

import yaroslavgorbach.totp.data.token.local.model.Token
import yaroslavgorbach.totp.data.token.local.repo.TokenRepo
import javax.inject.Inject

class AddTokenInteractor  @Inject constructor(private val repo: TokenRepo) {
    suspend operator fun invoke(label: String, secret: String) {
        repo.insert(Token(label = label, secret = secret))
    }
}