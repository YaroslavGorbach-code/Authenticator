package yaroslavgorbach.totp.feature.tokens.model

sealed class TokensActions {
    data class ChangeAddTokenUiState(var isActive: Boolean = false) : TokensActions()
    data class AddToken(var label: String, val key: String) : TokensActions()
    object ShowAddTokensDialog : TokensActions()
}