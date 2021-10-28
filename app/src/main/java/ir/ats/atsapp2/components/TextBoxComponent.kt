package ir.ats.atsapp2.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.ats.atsapp2.ui.theme.searchBoxColors

@Composable
fun TextBoxComponent(
	text: MutableState<TextFieldValue>,
	modifier: Modifier = Modifier,
	placeholder: (@Composable () -> Unit)? = null,
	leadingIcon: (@Composable () -> Unit)? = null,
	trailingIcon: (@Composable () -> Unit)? = null
) {
	val shape = RoundedCornerShape(4.dp)
	
	TextField(
		value = text.value,
		onValueChange = { text.value = it },
		modifier = modifier.then(Modifier.border(1.dp, Color.LightGray, shape)),
		shape = shape,
		colors = searchBoxColors(),
		placeholder = placeholder,
		leadingIcon = leadingIcon,
		trailingIcon = trailingIcon
	)
}

@Preview(showBackground = true)
@Composable
private fun ComponentPreview() {
	MaterialTheme {
		TextBoxComponent(remember { mutableStateOf(TextFieldValue()) }, Modifier.fillMaxWidth())
	}
}
