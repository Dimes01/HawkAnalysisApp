package hawk.analysis.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import hawk.analysis.app.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val bodyFontFamily = FontFamily(
    Font(
        googleFont = GoogleFont("Noto Sans"),
        fontProvider = provider,
    )
)

val displayFontFamily = FontFamily(
    Font(
        googleFont = GoogleFont("Tinos"),
        fontProvider = provider,
    )
)

val AppTypography = Typography(
    displayLarge = TextStyle(fontFamily = displayFontFamily, fontSize = 34.sp, fontWeight = FontWeight.Bold, lineHeight = 40.sp, letterSpacing = 0.25.sp),
    displayMedium = TextStyle(fontFamily = displayFontFamily, fontSize = 30.sp, fontWeight = FontWeight.Bold, lineHeight = 36.sp, letterSpacing = 0.25.sp),
    displaySmall = TextStyle(fontFamily = displayFontFamily, fontSize = 26.sp, fontWeight = FontWeight.Bold, lineHeight = 32.sp, letterSpacing = 0.25.sp),
    headlineLarge = TextStyle(fontFamily = displayFontFamily, fontSize = 24.sp, fontWeight = FontWeight.Bold, lineHeight = 28.sp, letterSpacing = 0.sp),
    headlineMedium = TextStyle(fontFamily = displayFontFamily, fontSize = 20.sp, fontWeight = FontWeight.Bold, lineHeight = 24.sp, letterSpacing = 0.sp),
    headlineSmall = TextStyle(fontFamily = displayFontFamily, fontSize = 16.sp, fontWeight = FontWeight.Bold, lineHeight = 20.sp, letterSpacing = 0.sp),
    titleLarge = TextStyle(fontFamily = displayFontFamily, fontSize = 22.sp, fontWeight = FontWeight.Medium, lineHeight = 28.sp, letterSpacing = 0.15.sp),
    titleMedium = TextStyle(fontFamily = displayFontFamily, fontSize = 18.sp, fontWeight = FontWeight.Medium, lineHeight = 24.sp, letterSpacing = 0.15.sp),
    titleSmall = TextStyle(fontFamily = displayFontFamily, fontSize = 16.sp, fontWeight = FontWeight.Medium, lineHeight = 20.sp, letterSpacing = 0.15.sp),
    bodyLarge = TextStyle(fontFamily = bodyFontFamily, fontSize = 16.sp, fontWeight = FontWeight.Normal, lineHeight = 24.sp, letterSpacing = 0.5.sp),
    bodyMedium = TextStyle(fontFamily = bodyFontFamily, fontSize = 14.sp, fontWeight = FontWeight.Normal, lineHeight = 20.sp, letterSpacing = 0.5.sp),
    bodySmall = TextStyle(fontFamily = bodyFontFamily, fontSize = 12.sp, fontWeight = FontWeight.Normal, lineHeight = 16.sp, letterSpacing = 0.5.sp),
    labelLarge = TextStyle(fontFamily = bodyFontFamily, fontSize = 14.sp, fontWeight = FontWeight.Bold, lineHeight = 20.sp, letterSpacing = 1.sp),
    labelMedium = TextStyle(fontFamily = bodyFontFamily, fontSize = 12.sp, fontWeight = FontWeight.Bold, lineHeight = 16.sp, letterSpacing = 1.sp),
    labelSmall = TextStyle(fontFamily = bodyFontFamily, fontSize = 10.sp, fontWeight = FontWeight.Bold, lineHeight = 12.sp, letterSpacing = 1.sp),
)

