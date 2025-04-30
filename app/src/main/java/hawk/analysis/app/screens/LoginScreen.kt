package hawk.analysis.app.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import hawk.analysis.app.R
import hawk.analysis.app.ui.components.HawkOutlinedButton
import hawk.analysis.app.ui.components.HawkOutlinedTextField
import hawk.analysis.app.ui.components.HawkTonalButton
import hawk.analysis.app.ui.theme.HawkAnalysisAppTheme
import hawk.analysis.app.viewmodels.LoginViewModel

@Preview(widthDp = 440, heightDp = 956)
@Composable
fun LoginPreview() {
    Login({})
}

@Composable
fun Login(
    onHomeScreen: () -> Unit
) {
    HawkAnalysisAppTheme {
        val viewModel = viewModel<LoginViewModel>()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.onPrimary)
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
                var email = ""
                var password = ""
                HawkOutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "E-mail",
                )
                HawkOutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Пароль",
                    isPassword = true
                )
                val modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 2.dp)
                HawkOutlinedButton(
                    text = "Войти",
                    modifier = modifier,
                    onClick = {  }
                )
                HawkTonalButton(
                    text = "Зарегистрироваться",
                    modifier = modifier,
                    onClick = {  }
                )
            }
        }
    }
}