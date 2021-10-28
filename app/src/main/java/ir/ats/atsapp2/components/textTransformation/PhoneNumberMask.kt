package ir.ats.atsapp2.components.textTransformation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PhoneNumberMask : VisualTransformation {
	override fun filter(text: AnnotatedString): TransformedText {
		var newText = ""
		text.forEachIndexed { i, c ->
			newText += if (i == 4 || i == 7) "-$c" else c
		}
		
		return TransformedText(AnnotatedString(newText), PhoneNumberOffsetTranslator)
	}
}

private object PhoneNumberOffsetTranslator : OffsetMapping {
	override fun originalToTransformed(offset: Int): Int {
		return when {
			offset <= 4 -> offset
			offset <= 7 -> offset + 1
			else -> offset + 2
		}
	}
	
	override fun transformedToOriginal(offset: Int): Int {
		return when {
			offset <= 4 -> offset
			offset <= 8 -> offset - 1
			else -> offset - 2
		}
	}
}
