package hawk.analysis.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hawk.analysis.app.ui.components.HawkInfoSection
import hawk.analysis.app.ui.components.HawkOutlinedButton
import hawk.analysis.app.ui.components.HawkParameter
import hawk.analysis.app.ui.components.HawkSimpleHeader
import hawk.analysis.app.utilities.dateTimeFormat
import hawk.analysis.app.viewmodels.SettingsViewModel
import kotlinx.datetime.format

@Preview(widthDp = 440, heightDp = 956)
@Composable
fun SettingsPreview() {
    val state = SettingsScreenState()
    Settings(state)
}

@Composable
fun SettingsVM(viewModel: SettingsViewModel) {
    val state = viewModel.state.collectAsState()
    Settings(state.value)
}

@Composable
fun Settings(
    state: SettingsScreenState
) {
    Column(
        modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surfaceContainer),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        HawkSimpleHeader("Настройки")
        Column(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Profile
            HawkInfoSection("Профиль") {
                HawkParameter("Имя", state.profile.name)
                HawkParameter("E-mail", state.profile.email)
                HawkParameter("Изменён", state.profile.updatedAt.format(dateTimeFormat))
                HawkParameter("Создан", state.profile.createdAt.format(dateTimeFormat))
                Row {
                    val modifierButtons = Modifier.fillMaxWidth()
                    HawkOutlinedButton(text = "Изменить e-mail", modifier = modifierButtons) {
                        /*TODO: реализовать изменение почты*/
                    }
                    HawkOutlinedButton(text = "Изменить пароль", modifier = modifierButtons) {
                        /*TODO: реализовать изменение пароля*/
                    }
                }
            }
            // Accounts
            HawkInfoSection("Счета") {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                }
            }
            // Tokens
            HawkInfoSection("Токены") {

            }
        }
    }
}
