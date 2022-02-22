package yaroslavgorbach.totp.feature.tokens.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import yaroslavgorbach.totp.R
import yaroslavgorbach.totp.data.token.local.model.Token
import yaroslavgorbach.totp.feature.tokens.model.TokensActions
import yaroslavgorbach.totp.feature.tokens.model.TokensUiMassage
import yaroslavgorbach.totp.feature.tokens.model.TokensViewState
import yaroslavgorbach.totp.feature.tokens.presentation.TokensViewModel
import yaroslavgorbach.totp.utill.UiMessage

@ExperimentalMaterialApi
@Composable
fun TokensUi() {
    TokensUi(viewModel = hiltViewModel())
}

@ExperimentalMaterialApi
@Composable
internal fun TokensUi(
    viewModel: TokensViewModel,
) {
    TokensUi(
        state = viewModel.state.collectAsState().value,
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
            is TokensUiMassage.ShowAddTokenDialog -> {
                ShowAddTokenDialog(onDismissRequest = {
                    onMessageShown(uiMessage.id)
                }, onAddClick = { label, secret ->
                    actioner(TokensActions.AddToken(label = label, key = secret))
                    onMessageShown(uiMessage.id)
                })
            }
        }
    }

    Box(Modifier.fillMaxSize()) {
        LazyColumn(Modifier.fillMaxSize()) {
            items(state.tokens) { token ->
                TokenItem(token = token)

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .padding(horizontal = 16.dp)
                        .background(color = Color.LightGray)
                )
            }
        }

        Column(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.BottomEnd)
                .padding(16.dp),
        ) {
            if (state.isAddTokenStateActive) {
                ShowAddTokenVariants {
                    actioner(TokensActions.ShowAddTokensDialog)
                    actioner(TokensActions.ChangeAddTokenUiState(false))
                }
            }

            FloatingActionButton(
                onClick = { actioner(TokensActions.ChangeAddTokenUiState(state.isAddTokenStateActive.not())) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.align(Alignment.End)
            ) {
                if (state.isAddTokenStateActive) {
                    Icon(Icons.Outlined.Close, contentDescription = "Add token", tint = Color.White)
                } else {
                    Icon(Icons.Outlined.Add, contentDescription = "Add token", tint = Color.White)
                }
            }
        }
    }
}

@Composable
private fun ShowAddTokenVariants(showAddTokenDialog: () -> Unit) {
    Column(Modifier.wrapContentSize()) {
        Row(
            modifier = Modifier
                .height(40.dp)
                .clickable { showAddTokenDialog() }
                .clip(shape = MaterialTheme.shapes.medium)
                .padding(8.dp)
                .align(Alignment.End)
        ) {

            Text(
                text = stringResource(id = R.string.add_manualy),
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            Icon(
                Icons.Outlined.Keyboard,
                contentDescription = null,
                tint = MaterialTheme.colors.primary,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 8.dp)
            )
        }

        Spacer(
            modifier = Modifier
                .width(200.dp)
                .padding(vertical = 8.dp)
                .height(1.dp)
                .background(Color.LightGray)
                .align(alignment = CenterHorizontally)
        )

        Row(modifier = Modifier
            .height(40.dp)
            .clickable { }
            .clip(shape = MaterialTheme.shapes.medium)
            .padding(8.dp)
            .align(Alignment.End)
        ) {

            Text(
                text = stringResource(id = R.string.add_by_qr_code),
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            Icon(
                Icons.Outlined.QrCodeScanner,
                contentDescription = null,
                tint = MaterialTheme.colors.primary,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 8.dp)
            )
        }

        Spacer(
            modifier = Modifier
                .wrapContentWidth()
                .height(16.dp)
        )
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ExercisesPreview() {
    MaterialTheme {
        TokensUi(
            state = TokensViewState(tokens = listOf(Token.Test), isAddTokenStateActive = true),
            onMessageShown = {},
            actioner = {}
        )
    }
}