package hawk.analysis.app.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import hawk.analysis.app.viewmodels.AccountViewModel

@Preview
@Composable
fun AccountPreview() {
    Account()
}

@Composable
fun AccountVM(viewModel: AccountViewModel) {
    Account()
}

@Composable
fun Account() {

}