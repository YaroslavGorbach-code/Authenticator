package yaroslavgorbach.totp.feature.tokens.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import yaroslavgorbach.totp.data.token.local.model.Token
import yaroslavgorbach.totp.feature.common.ui.theme.AuthenticatorTheme
import yaroslavgorbach.totp.feature.tokens.model.TokensActions
import yaroslavgorbach.totp.feature.tokens.model.TokensViewState
import yaroslavgorbach.totp.feature.tokens.presentation.TokensViewModel

@ExperimentalMaterialApi
@Composable
fun TokensUi() {
    TokensUi(
        viewModel = hiltViewModel(),
    )
}

@ExperimentalMaterialApi
@Composable
internal fun TokensUi(
    viewModel: TokensViewModel,
) {
    val viewState = viewModel.state.collectAsState()

    TokensUi(
        state = viewState.value,
        onMessageShown = viewModel::clearMessage,
        actioner = viewModel::submitAction
    )
}

@ExperimentalMaterialApi
@Composable
internal fun TokensUi(
    state: TokensViewState,
    actioner: (TokensActions) -> Unit,
    onMessageShown: (id: Long) -> Unit,
) {

    state.message?.let { uiMessage ->
        when (uiMessage.message) {
            else -> {}
        }

        onMessageShown(uiMessage.id)
    }

    LazyColumn {
        items(state.tokens) { token ->
            TokenItem(token = token)
        }
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun ExercisesPreview() {
    AuthenticatorTheme {
        TokensUi(
            state = TokensViewState(tokens = listOf(Token.Test)),
            onMessageShown = {},
            actioner = {}
        )
    }
}