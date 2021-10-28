package ir.ats.atsapp2.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LetterGroupIndicator(letter: Char) {
	Text(
		text = letter.toString(),
		fontSize = 18.sp,
		fontWeight = FontWeight.Bold,
		modifier = Modifier
			.fillMaxWidth()
			.border(
				2.dp,
				Brush.horizontalGradient(
					0f to MaterialTheme.colors.secondary,
					0.8f to Color.Transparent
				),
				CircleShape
			)
			.padding(vertical = 8.dp, horizontal = 16.dp)
	)
}

@Preview(showBackground = true)
@Composable
private fun ComponentPreview() {
	LetterGroupIndicator(letter = 'A')
}
