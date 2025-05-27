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
import hawk.analysis.app.dto.UserInfo
import hawk.analysis.app.ui.components.ErrorMessage
import hawk.analysis.app.ui.components.HawkOutlinedButton
import hawk.analysis.app.ui.components.HawkOutlinedTextField
import hawk.analysis.app.ui.components.HawkSimpleHeader
import hawk.analysis.app.ui.theme.HawkAnalysisAppTheme
import hawk.analysis.app.utilities.HawkResponse
import kotlinx.coroutines.launch

@Preview(widthDp = 440, heightDp = 956)
@Composable
fun EditEmailPreview() {
    HawkAnalysisAppTheme {
        EditEmail({ _, _ -> null }, {})
    }
}

@Preview(widthDp = 440, heightDp = 956)
@Composable
fun EditPasswordPreview() {
    HawkAnalysisAppTheme {
        EditPassword({ _, _ -> null }, {})
    }
}


@Composable
fun EditEmail(
    actSave: suspend (String, String) -> HawkResponse<UserInfo>,
    navToBack: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    var email: String by remember { mutableStateOf("") }
    var password: String by remember { mutableStateOf("") }
    var error: String? by remember { mutableStateOf(null) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainer),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HawkSimpleHeader("Изменение e-mail", Modifier.padding(vertical = 20.dp))
        Column(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            HawkOutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = "E-mail",
                modifier = Modifier.fillMaxWidth()
            )
            HawkOutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = "Пароль",
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
                        val info = actSave(email, password)
                        if (info != null) navToBack()
                        else error = "Не удалось изменить e-mail"
                    } }
                )
            }
            if (error != null) { ErrorMessage(error!!) }
        }
    }
}



@Composable
fun EditPassword(
    actSave: suspend (String, String) -> HawkResponse<UserInfo>,
    navToBack: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    var oldPassword: String by remember { mutableStateOf("") }
    var newPassword: String by remember { mutableStateOf("") }
    var error: String? by remember { mutableStateOf(null) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainer),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HawkSimpleHeader("Изменение пароля", Modifier.padding(vertical = 20.dp))
        Column(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            HawkOutlinedTextField(
                value = oldPassword,
                onValueChange = { oldPassword = it },
                label = "Старый пароль",
                modifier = Modifier.fillMaxWidth(),
                isPassword = true
            )
            HawkOutlinedTextField(
                value = newPassword,
                onValueChange = { newPassword = it },
                label = "Новый пароль",
                modifier = Modifier.fillMaxWidth(),
                isPassword = true
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
                        val info = actSave(oldPassword, newPassword)
                        if (info != null) navToBack()
                        else error = "Не удалось изменить пароль"
                    } }
                )
            }
            if (error != null) { ErrorMessage(error!!) }
        }
    }
}