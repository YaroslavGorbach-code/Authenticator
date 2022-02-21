package yaroslavgorbach.totp.feature.tokens.model

sealed class TokensUiMassage {
    object ShowAddTokenDialog : TokensUiMassage()
}