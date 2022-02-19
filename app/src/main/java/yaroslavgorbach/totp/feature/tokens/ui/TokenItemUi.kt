package yaroslavgorbach.totp.feature.tokens.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import kotlinx.coroutines.DelicateCoroutinesApi
import yaroslavgorbach.totp.data.token.local.model.Token


@Composable
fun TokenItem(token: Token) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(8.dp)
    ) {
        CircularProgressIndicator(
            progress = token.progress.collectAsState(initial = 0f).value,
            modifier = Modifier.align(alignment = Alignment.CenterStart)
        )
        Text(text = token.formattedCode, modifier = Modifier.align(alignment = Alignment.Center))
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