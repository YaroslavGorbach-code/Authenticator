package yaroslavgorbach.totp.feature.tokens.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import yaroslavgorbach.totp.R

@Composable
fun ShowAddTokenDialog(
    onDismissRequest: () -> Unit,
    onAddClick: (label: String, secret: String) -> Unit
) {
    var secret by remember { mutableStateOf("") }
    var label by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        title = {
            Text(text = stringResource(id = R.string.add_secret_dialog_title), fontSize = 16.sp)
        }, text = {
            Column {
                OutlinedTextField(
                    value = label,
                    onValueChange = { name ->
                        label = name
                    }, label = {
                        Text(text = stringResource(id = R.string.enter_label), fontSize = 10.sp)
                    })

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                )

                OutlinedTextField(
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Ascii),
                    value = secret,
                    onValueChange = { key ->
                        secret = key
                    }, label = {
                        Text(text = stringResource(id = R.string.enter_key), fontSize = 10.sp)
                    })
            }

        }, buttons = {
            Button(
                shape = RectangleShape,
                onClick = { onAddClick(label, secret) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
            ) {
                Text(text = stringResource(id = R.string.add))
            }
        }
    )
}

@ExperimentalMaterialApi
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ShowAddTokenDialogPreview() {
    MaterialTheme {
        ShowAddTokenDialog(
            {}, { v, e ->

            })
    }
}