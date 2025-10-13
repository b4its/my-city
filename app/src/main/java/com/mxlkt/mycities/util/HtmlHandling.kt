package com.mxlkt.mycities.util

import android.text.Spanned
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.core.text.HtmlCompat
import toAnnotatedString

@Composable
fun HtmlText(html: String, modifier: Modifier = Modifier, style: TextStyle = LocalTextStyle.current) {
    // 1. Parse HTML menjadi Spanned
    val spanned = remember(html) {
        HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT)
    }
    // 2. Konversi Spanned menjadi AnnotatedString
    val annotatedString = remember(spanned) {
        spanned.toAnnotatedString()
    }

    // 3. Tampilkan di Text Composable
    Text(
        text = annotatedString,
        modifier = modifier,
        style = style
    )
}