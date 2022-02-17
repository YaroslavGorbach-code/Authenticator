package yaroslavgorbach.totp.data.token.local.model

data class Token(
    val id: Long,
    val ordinal: Long,
    val issuer: String?,
    val label: String,
    val algorithm: EncryptionAlgorithm,
    val secret: String,
    val digits: Int,
    val counter: Long,
    val period: Int,
) {
    companion object {
        val Test = Token(
            id = -1,
            ordinal = 1,
            issuer = null,
            label = "Test",
            algorithm = EncryptionAlgorithm.NONE,
            secret = "",
            digits = 0,
            counter = 0,
            period = 0
        )
    }
}