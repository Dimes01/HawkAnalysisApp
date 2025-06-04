package hawk.analysis.app.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hawk.analysis.app.R
import hawk.analysis.app.di.commonModule
import hawk.analysis.app.services.AuthService
import hawk.analysis.app.ui.components.ErrorMessage
import hawk.analysis.app.ui.components.HawkOutlinedButton
import hawk.analysis.app.ui.components.HawkOutlinedTextField
import hawk.analysis.app.ui.components.HawkTonalButton
import hawk.analysis.app.ui.theme.HawkAnalysisAppTheme
import kotlinx.coroutines.launch
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject

@Preview(name = "Light", widthDp = 440, heightDp = 956)
@Preview(name = "Dark", widthDp = 440, heightDp = 956, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoginPreview() {
    KoinApplication(application = {
        modules(commonModule)
    }) {
        Login({}, {})
    }
}

@Composable
fun Login(
    onHomeScreen: () -> Unit,
    onRegisterScreen: () -> Unit,
) {
    val authService = koinInject<AuthService>()
    val coroutineScope = rememberCoroutineScope()
    var email = remember { mutableStateOf("demo@mail.ru") }
    var password = remember { mutableStateOf("12345678") }
    val modifierForButtons = Modifier
        .fillMaxWidth()
        .padding(0.dp, 2.dp)
    val modifierForTextFields = Modifier.fillMaxWidth()
    var error by remember { mutableStateOf("") }
    HawkAnalysisAppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .padding(horizontal = 50.dp, vertical = 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Bottom),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.logo),
                    contentDescription = "logo"
                )
                Text(
                    text = "Вход",
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (error.isNotBlank()) {
                    ErrorMessage(
                        text = error,
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                1.dp,
                                MaterialTheme.colorScheme.error,
                                RoundedCornerShape(10.dp)
                            )
                            .padding(10.dp)
                    )
                }
                HawkOutlinedTextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    label = "E-mail",
                    isError = error.isNotBlank(),
                    modifier = modifierForTextFields,
                )
                HawkOutlinedTextField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    label = "Пароль",
                    isPassword = true,
                    isError = error.isNotBlank(),
                    modifier = modifierForTextFields
                )
                HawkOutlinedButton(
                    text = "Войти",
                    modifier = modifierForButtons,
                    onClick = {
                        coroutineScope.launch {
                            try {
                                authService.login(email.value, password.value)
                                onHomeScreen()
                            } catch (e: Exception) {
                                error = e.message ?: "Неопределенная ошибка"
                            }
                        }
                    }
                )
                HawkTonalButton(
                    text = "Зарегистрироваться",
                    modifier = modifierForButtons,
                    onClick = onRegisterScreen
                )
            }
        }
    }
}