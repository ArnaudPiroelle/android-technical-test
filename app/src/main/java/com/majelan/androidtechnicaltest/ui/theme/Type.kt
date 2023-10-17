package com.majelan.androidtechnicaltest.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.majelan.androidtechnicaltest.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val OpenSansFont = GoogleFont(name = "Open Sans")
val OpenSansFontFamily = FontFamily(
    Font(googleFont = OpenSansFont, fontProvider = provider, weight = FontWeight.Bold),
    Font(googleFont = OpenSansFont, fontProvider = provider, weight = FontWeight.Black),
    Font(googleFont = OpenSansFont, fontProvider = provider, weight = FontWeight.Light),
    Font(googleFont = OpenSansFont, fontProvider = provider, weight = FontWeight.Medium),
    Font(googleFont = OpenSansFont, fontProvider = provider, weight = FontWeight.ExtraBold),
    Font(googleFont = OpenSansFont, fontProvider = provider, weight = FontWeight.ExtraLight),
    Font(googleFont = OpenSansFont, fontProvider = provider, weight = FontWeight.Normal),
    Font(googleFont = OpenSansFont, fontProvider = provider, weight = FontWeight.SemiBold),
    Font(googleFont = OpenSansFont, fontProvider = provider, weight = FontWeight.Thin),
)

// Set of Material typography styles to start with
val defaultTypo = Typography()
val Typography = Typography(
    displayLarge = defaultTypo.displayLarge.copy(fontFamily = OpenSansFontFamily),
    displayMedium = defaultTypo.displayMedium.copy(fontFamily = OpenSansFontFamily),
    displaySmall = defaultTypo.displaySmall.copy(fontFamily = OpenSansFontFamily),
    headlineLarge = defaultTypo.headlineLarge.copy(fontFamily = OpenSansFontFamily),
    headlineMedium = defaultTypo.headlineMedium.copy(fontFamily = OpenSansFontFamily),
    headlineSmall = defaultTypo.headlineSmall.copy(fontFamily = OpenSansFontFamily),
    titleLarge = defaultTypo.titleLarge.copy(fontFamily = OpenSansFontFamily),
    titleMedium = defaultTypo.titleMedium.copy(fontFamily = OpenSansFontFamily),
    titleSmall = defaultTypo.titleSmall.copy(fontFamily = OpenSansFontFamily),
    bodyLarge = defaultTypo.bodyLarge.copy(fontFamily = OpenSansFontFamily),
    bodyMedium = defaultTypo.bodyMedium.copy(fontFamily = OpenSansFontFamily),
    bodySmall = defaultTypo.bodySmall.copy(fontFamily = OpenSansFontFamily),
    labelLarge = defaultTypo.labelLarge.copy(fontFamily = OpenSansFontFamily),
    labelMedium = defaultTypo.labelMedium.copy(fontFamily = OpenSansFontFamily),
    labelSmall = defaultTypo.labelSmall.copy(fontFamily = OpenSansFontFamily),
)