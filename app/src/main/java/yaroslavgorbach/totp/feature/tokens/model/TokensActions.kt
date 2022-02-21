package yaroslavgorbach.totp.feature.tokens.model

sealed class TokensActions {
    data class ChangeAddTokenUiState(var isActive: Boolean = false) : TokensActions()
    object ShowAddTokensDialog : TokensActions()
}