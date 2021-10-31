package ir.ats.atsapp2.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ir.ats.atsapp2.ui.theme.RoundedShapes

@Composable
fun AlertDialog(
	visible: Boolean,
	onDismiss: (() -> Unit)? = null,
	content: @Composable ColumnScope.() -> Unit
) {
	BaseDialog(
		visible = visible,
		onDismiss = onDismiss
	) {
		Column(
			modifier = Modifier
				.background(MaterialTheme.colors.background, RoundedShapes.Medium)
				.padding(16.dp)
		) {
			content()
			
			Spacer(modifier = Modifier.height(16.dp))
			
			TextButton(
				onClick = { onDismiss?.let { it() } },
				modifier = Modifier.align(Alignment.End)
			) {
				Text(text = "Okay")
			}
		}
	}
}

// @ExperimentalAnimationApi
// @Preview(showBackground = true)
// @Composable
// private fun ComponentPreview() {
// 	MaterialTheme {
// 		Scaffold(Modifier.fillMaxSize()) {
// 			AlertDialog(visible = true) {
// 				Text(text = "Hello World Inside Dialog with some long text which will wrap I think ... hmm", softWrap = true)
// 			}
// 		}
// 	}
// }
