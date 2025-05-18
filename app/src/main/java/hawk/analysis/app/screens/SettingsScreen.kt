package hawk.analysis.app.screens

import android.icu.math.MathContext
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
import hawk.analysis.app.dto.UserInfo
import hawk.analysis.app.ui.components.HawkInfoSection
import hawk.analysis.app.ui.components.HawkInfoSectionHeader
import hawk.analysis.app.ui.components.HawkInfoSectionHeaderEdit
import hawk.analysis.app.ui.components.HawkOutlinedButton
import hawk.analysis.app.ui.components.HawkParameter
import hawk.analysis.app.ui.components.HawkSimpleHeader
import hawk.analysis.app.ui.theme.HawkAnalysisAppTheme
import hawk.analysis.app.utilities.accountAPI
import hawk.analysis.app.utilities.dateTimeFormat
import hawk.analysis.app.utilities.token
import hawk.analysis.app.viewmodels.SettingsViewModel
import kotlinx.datetime.Instant
import kotlinx.datetime.format

@Preview(widthDp = 440, heightDp = 1500)
@Composable
fun SettingsPreview() {
    val createdAt = Instant.parse("2025-01-01T12:00:00Z")
    val updatedAt = Instant.parse("2025-01-01T12:00:00Z")
    val state = SettingsScreenState(
        profile = UserInfo(1, "Петров П.П.", "test@test.com", createdAt, updatedAt),
        accounts = listOf(accountAPI),
        tokens = listOf(token)
    )
    HawkAnalysisAppTheme {
        Settings(state)
    }
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
            HawkInfoSection(
                header = { HawkInfoSectionHeader(name = "Профиль") }
            ) {
                HawkParameter("Имя", state.profile.name)
                HawkParameter("E-mail", state.profile.email)
                HawkParameter("Изменён", state.profile.updatedAt.format(dateTimeFormat))
                HawkParameter("Создан", state.profile.createdAt.format(dateTimeFormat))
                Row(
                    modifier = Modifier.padding(vertical = 5.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    val modifierButtons = Modifier.weight(0.5f)
                    HawkOutlinedButton(text = "Изменить e-mail", modifier = modifierButtons) {
                        /*TODO: реализовать изменение почты*/
                    }
                    HawkOutlinedButton(text = "Изменить пароль", modifier = modifierButtons) {
                        /*TODO: реализовать изменение пароля*/
                    }
                }
                Row(
                    modifier = Modifier.padding(vertical = 5.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    HawkOutlinedButton(
                        text = "Удалить аккаунт",
                        borderColor = MaterialTheme.colorScheme.onErrorContainer,
                        contentColor = MaterialTheme.colorScheme.error,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        /*TODO: реализовать изменение почты*/
                    }
                }
            }
            // Accounts
            HawkInfoSection(
                header = { HawkInfoSectionHeader("Счета") }
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    state.accounts.forEach { acc ->
                        HawkInfoSection(
                            header = { HawkInfoSectionHeaderEdit(acc.id) { /*TODO: реализовать логику редактирования*/ } }
                        ) {
                            HawkParameter("Тип", acc.name)
                            HawkParameter("Открыт", acc.openedDate.format(dateTimeFormat))
                            HawkParameter("Закрыт", acc.closedDate.format(dateTimeFormat))
                            HawkParameter("Безрисковая ставка", "${acc.riskFree?.setScale(2, MathContext.ROUND_HALF_UP)}")
                            HawkParameter("Бенчмарк", acc.tickerBenchmark.toString())
                        }
                    }
                }
            }
            // Tokens
            HawkInfoSection(
                header = { HawkInfoSectionHeader("Токены") }
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    state.tokens.forEach { token ->
                        HawkInfoSection(
                            header = { HawkInfoSectionHeaderEdit(token.name) { /*TODO: реализовать логику редактирования*/ } }
                        ) {
                            HawkParameter("Открыт", token.createdAt.format(dateTimeFormat))
                            HawkParameter("Обновлён", token.updatedAt .format(dateTimeFormat))
                        }
                    }
                    HawkOutlinedButton(text = "Добавить токен", modifier = Modifier.fillMaxWidth()) {
                        /*TODO: реализовать добавление токена*/
                    }
                }
            }
        }
    }
}
