package ir.ats.atsapp2.components.textTransformation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PhoneNumberMask : VisualTransformation {
	override fun filter(text: AnnotatedString): TransformedText {
		return text.run {
			var newText = ""
			
			TransformedText(AnnotatedString(newText), PhoneNumberOffsetTranslator)
		}
	}
}

private object PhoneNumberOffsetTranslator : OffsetMapping {
	override fun originalToTransformed(offset: Int): Int {
		TODO()
	}
	
	override fun transformedToOriginal(offset: Int): Int {
		TODO()
	}
	
}
