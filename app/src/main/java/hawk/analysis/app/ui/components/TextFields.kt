package hawk.analysis.app.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HawkOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    isError: Boolean = false,
    isEnabled: Boolean = true
) {
    OutlinedTextField (
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = { Text(
            text = label,
            style = MaterialTheme.typography.bodySmall
        ) },
        trailingIcon = { Icon(
            imageVector = if (isError) Icons.Filled.Error else Icons.Outlined.Cancel,
            contentDescription = "Cancel"
        ) },
        textStyle = MaterialTheme.typography.bodyLarge,
        visualTransformation =
            if (isPassword)
                PasswordVisualTransformation()
            else
                VisualTransformation.None,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,

            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            focusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            focusedTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,

            errorTextColor = MaterialTheme.colorScheme.onSurface,
            errorLabelColor = MaterialTheme.colorScheme.error,
            errorBorderColor = MaterialTheme.colorScheme.error,
            errorContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            errorTrailingIconColor = MaterialTheme.colorScheme.error,

            cursorColor = MaterialTheme.colorScheme.primary,
            errorCursorColor = MaterialTheme.colorScheme.error
        ),
        isError = isError,
        enabled = isEnabled
    )
}

@Preview
@Composable
fun HawkOutlinedTextFieldPreview() {
    HawkOutlinedTextField("Значение", {}, "Значение")
}