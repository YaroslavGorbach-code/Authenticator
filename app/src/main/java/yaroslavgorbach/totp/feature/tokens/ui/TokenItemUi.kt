package yaroslavgorbach.totp.feature.tokens.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.DelicateCoroutinesApi
import yaroslavgorbach.totp.data.token.local.model.Token

@Composable
fun TokenItem(token: Token) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = token.label,
                fontSize = 18.sp,
                style = MaterialTheme.typography.overline,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .align(Alignment.CenterHorizontally)
            )

            Row(modifier = Modifier.wrapContentHeight().padding(top = 8.dp)) {
                CircularProgressIndicator(
                    progress = token.progress.collectAsState(initial = 0f).value,
                    modifier = Modifier
                        .size(20.dp)
                        .align(Alignment.CenterVertically),
                    strokeWidth = 2.dp
                )

                Text(
                    text = token.formattedCode,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 16.dp),
                    color = MaterialTheme.colors.primary,
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.overline
                )
            }
        }

    }
}

@DelicateCoroutinesApi
@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun TokenItemPreview(token: Token = Token.Test) {
    MaterialTheme {
        TokenItem(token = token)
    }
}