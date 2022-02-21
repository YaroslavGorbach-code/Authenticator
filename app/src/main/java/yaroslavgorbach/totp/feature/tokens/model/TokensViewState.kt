package yaroslavgorbach.totp.feature.tokens.model

import yaroslavgorbach.totp.data.token.local.model.Token
import yaroslavgorbach.totp.utill.UiMessage

data class TokensViewState(
    val tokens: List<Token> = emptyList(),
    val isAddTokenStateActive: Boolean = false,
    val message: UiMessage<TokensUiMassage>? = null
) {
    companion object {
        val Empty = TokensViewState()
    }
}
