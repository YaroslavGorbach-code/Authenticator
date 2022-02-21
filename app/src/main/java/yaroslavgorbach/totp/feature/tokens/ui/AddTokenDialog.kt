package yaroslavgorbach.totp.feature.tokens.ui

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ShowAddTokenDialog(onDismissRequest: () -> Unit, onAddClick: (label: String, secret: String) -> Unit) {
    var secret by remember { mutableStateOf("") }
    var label by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        title = {

        }, text = {

        }, buttons = {
            TextButton(onClick = { onAddClick(label, secret) }) {

            }
        }
    )
}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun ShowAddTokenDialogPreview() {
    MaterialTheme {
        ShowAddTokenDialog(
            {}, { v, e ->

            })
    }
}