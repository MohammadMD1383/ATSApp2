package ir.ats.atsapp2.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog

@Composable
fun BaseDialog(
	visible: Boolean,
	onDismiss: (() -> Unit)? = null,
	content: @Composable () -> Unit
) {
	if (visible) {
		Dialog(
			onDismissRequest = { onDismiss?.let { it() } },
			content = content
		)
	}
}
