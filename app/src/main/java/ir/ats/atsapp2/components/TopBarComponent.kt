package ir.ats.atsapp2.components

import androidx.compose.animation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import ir.ats.atsapp2.ui.theme.ATSApp2Theme

@ExperimentalAnimationApi
@Composable
fun TopBarComponent(
	onAddContact: () -> Unit,
	onSearchTermChange: (String) -> Unit
) {
	val searchVisible = remember { mutableStateOf(false) }
	
	Box {
		TopAppBar(
			title = {
				Text(text = "MyContacts")
			},
			actions = {
				IconButton(onClick = onAddContact) {
					Icon(imageVector = Icons.Rounded.Add, contentDescription = "add contact")
				}
				
				IconButton(onClick = { searchVisible.value = true }) {
					Icon(imageVector = Icons.Rounded.Search, contentDescription = "search contacts")
				}
			}
		)
		
		AnimatedVisibility(
			visible = searchVisible.value,
			enter = expandHorizontally() + fadeIn(),
			exit = shrinkHorizontally() + fadeOut()
		) {
			SearchBoxComponent(
				onSearchTermChange = onSearchTermChange,
				visible = searchVisible
			)
		}
	}
}

@ExperimentalAnimationApi
@Preview
@Composable
private fun ComponentPreview() {
	ATSApp2Theme {
		TopBarComponent({}, {})
	}
}
