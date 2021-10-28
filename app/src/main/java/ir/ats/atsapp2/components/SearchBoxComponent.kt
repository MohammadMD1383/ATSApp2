package ir.ats.atsapp2.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import ir.ats.atsapp2.ui.theme.ATSApp2Theme
import ir.ats.atsapp2.ui.theme.searchBoxColors

@Composable
fun SearchBoxComponent(
	onSearchTermChange: (String) -> Unit,
	visible: MutableState<Boolean>
) {
	val searchTerm = remember { mutableStateOf(TextFieldValue()) }
	
	TextField(
		value = searchTerm.value,
		onValueChange = { searchTerm.value = it; onSearchTermChange(it.text) },
		shape = RectangleShape,
		singleLine = true,
		placeholder = { Text(text = "Search...") },
		modifier = Modifier.fillMaxWidth(),
		colors = searchBoxColors(),
		leadingIcon = { Icon(imageVector = Icons.Rounded.Search, contentDescription = "search") },
		trailingIcon = {
			IconButton(onClick = {
				if (searchTerm.value.text.isEmpty()) {
					visible.value = false
				} else {
					searchTerm.value = TextFieldValue()
					onSearchTermChange("")
				}
			}) {
				Icon(imageVector = Icons.Rounded.Close, contentDescription = "clear search")
			}
		}
	)
}

@Preview
@Composable
private fun ComponentPreview() {
	ATSApp2Theme {
		SearchBoxComponent(onSearchTermChange = {}, remember { mutableStateOf(true) })
	}
}
