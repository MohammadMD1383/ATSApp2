package ir.ats.atsapp2.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

@Composable
fun searchBoxColors(): TextFieldColors {
	return TextFieldDefaults.textFieldColors(
		backgroundColor = MaterialTheme.colors.background,
		unfocusedIndicatorColor = Color.Transparent,
		focusedIndicatorColor = Color.Transparent,
		errorIndicatorColor = Color.Transparent,
		disabledIndicatorColor = Color.Transparent,
	)
}