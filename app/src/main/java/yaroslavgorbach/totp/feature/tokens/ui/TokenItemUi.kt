package yaroslavgorbach.totp.feature.tokens.ui

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.DelicateCoroutinesApi
import yaroslavgorbach.totp.data.token.local.model.Token
import yaroslavgorbach.totp.feature.common.ui.theme.AuthenticatorTheme
import yaroslavgorbach.totp.utill.TimerCountDown


@Composable
fun TokenItem(token: Token) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(8.dp)
    ) {
        Log.v("xacas",  "state.timeUntilFinishedProgress.toString()")


        val progress: Float = when (val state = token.timerTillNextCode.state.collectAsState(initial = TimerCountDown.TimerState.Finish).value) {
            TimerCountDown.TimerState.Finish -> {
               // token.timerTillNextCode.startTimer()
                0f
            }
            is TimerCountDown.TimerState.Tick -> {
                Log.v("xacas",  state.timeUntilFinishedProgress.toString())

                state.timeUntilFinishedProgress
            }
        }


        CircularProgressIndicator(progress = progress, modifier = Modifier.align(alignment = Alignment.CenterStart))
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