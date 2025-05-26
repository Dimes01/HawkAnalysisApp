package hawk.analysis.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hawk.analysis.app.dto.AccountInfo
import hawk.analysis.app.ui.components.ErrorMessage
import hawk.analysis.app.ui.components.HawkOutlinedButton
import hawk.analysis.app.ui.components.HawkOutlinedTextField
import hawk.analysis.app.ui.components.HawkSimpleHeader
import hawk.analysis.app.ui.theme.HawkAnalysisAppTheme
import kotlinx.coroutines.launch

@Preview(widthDp = 440, heightDp = 956)
@Composable
fun AddAuthTokenPreview() {
    HawkAnalysisAppTheme {
        AddAuthToken({ _, _, _ -> true }, {})
    }
}

@Preview(widthDp = 440, heightDp = 956)
@Composable
fun EditAuthTokenPreview() {
    HawkAnalysisAppTheme {
        EditAuthToken(1, { _, _, _ -> true }, {})
    }
}


@Composable
fun AddAuthToken(
    actSave: suspend (name: String, password: String, authToken: String) -> Boolean,
    navToBack: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    var authToken: String by remember { mutableStateOf("") }
    var name: String by remember { mutableStateOf("") }
    var password: String by remember { mutableStateOf("") }
    var error: String? by remember { mutableStateOf(null) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainer),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HawkSimpleHeader("Добавление auth-токена", Modifier.padding(vertical = 20.dp))
        Column(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            HawkOutlinedTextField(
                value = authToken,
                onValueChange = { authToken = it },
                label = "Auth-токен",
                modifier = Modifier.fillMaxWidth()
            )
            HawkOutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = "Название токена",
                modifier = Modifier.fillMaxWidth()
            )
            HawkOutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = "Пароль",
                isPassword = true,
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                HawkOutlinedButton(
                    text = "Отменить",
                    borderColor = MaterialTheme.colorScheme.onErrorContainer,
                    contentColor = MaterialTheme.colorScheme.error,
                    modifier = Modifier.weight(0.5f),
                    onClick = navToBack
                )
                HawkOutlinedButton(
                    text = "Сохранить",
                    modifier = Modifier.weight(0.5f),
                    onClick = { coroutineScope.launch {
                        val res = actSave(name, password, authToken)
                        if (res) navToBack()
                        else error = "Не удалось создать токен"
                    } }
                )
            }
            error?.also { ErrorMessage(error!!) }
        }
    }
}



@Composable
fun EditAuthToken(
    tokenId: Int,
    actSave: suspend (id: Int, name: String, password: String) -> Boolean,
    navToBack: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    var name: String by remember { mutableStateOf("") }
    var password: String by remember { mutableStateOf("") }
    var error: String? by remember { mutableStateOf(null) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainer),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HawkSimpleHeader("Изменение auth-токена", Modifier.padding(vertical = 20.dp))
        Column(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            HawkOutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = "Название токена",
                modifier = Modifier.fillMaxWidth()
            )
            HawkOutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = "Пароль",
                isPassword = true,
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                HawkOutlinedButton(
                    text = "Отменить",
                    borderColor = MaterialTheme.colorScheme.onErrorContainer,
                    contentColor = MaterialTheme.colorScheme.error,
                    modifier = Modifier.weight(0.5f),
                    onClick = navToBack
                )
                HawkOutlinedButton(
                    text = "Сохранить",
                    modifier = Modifier.weight(0.5f),
                    onClick = { coroutineScope.launch {
                        val res = actSave(tokenId, name, password)
                        if (res) navToBack()
                        else error = "Не удалось изменить информацию"
                    } }
                )
            }
            error?.also { ErrorMessage(error!!) }
        }
    }
}