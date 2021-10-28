package ir.ats.atsapp2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.ats.atsapp2.components.ContactComponent
import ir.ats.atsapp2.components.LetterGroupIndicator
import ir.ats.atsapp2.components.TopBarComponent
import ir.ats.atsapp2.components.anim.helper.ContactWrapper
import ir.ats.atsapp2.data.ATSApp2Database
import ir.ats.atsapp2.data.entities.Contact
import ir.ats.atsapp2.ui.theme.ATSApp2Theme

class MainActivity : ComponentActivity() {
	
	@ExperimentalAnimationApi
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
		val contacts: SnapshotStateList<ContactWrapper> = SnapshotStateList<ContactWrapper>().apply {
			ATSApp2Database.Instance.contactDao().getAllContacts().forEach { add(ContactWrapper(it)) }
		}
		
		setContent {
			ATSApp2Theme {
				MainComponent(contacts)
			}
		}
	}
}

@ExperimentalAnimationApi
@Composable
private fun MainComponent(contactWrappers: SnapshotStateList<ContactWrapper>) {
	Column {
		TopBarComponent(
			onAddContact = {
			
			},
			onSearchTermChange = { searchTerm ->
				contactWrappers.forEach { cWrapper ->
					cWrapper.run {
						visible.value = (contact.name + contact.family).contains(searchTerm, true)
					}
				}
			}
		)
		
		LazyColumn(
			verticalArrangement = Arrangement.spacedBy(8.dp),
			contentPadding = PaddingValues(16.dp)
		) {
			val groupedContactWrapper = contactWrappers.groupBy { it.contact.name.first().uppercaseChar() }
			
			groupedContactWrapper.forEach {
				item {
					LetterGroupIndicator(letter = it.key)
				}
				
				items(it.value) { currentContactWrapper ->
					AnimatedVisibility(
						visible = currentContactWrapper.visible.value,
						enter = expandVertically() + fadeIn(),
						exit = shrinkVertically() + fadeOut()
					) {
						ContactComponent(contact = currentContactWrapper.contact) { }
					}
				}
			}
		}
	}
}

@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
private fun ComponentPreview() {
	val contacts = remember {
		listOf(
			ContactWrapper(Contact(1, "Amin", "Asadi", "09391252637", "eee@email.com")),
			ContactWrapper(Contact(0, "Sadra", "Soltani", "09391252637")),
			ContactWrapper(Contact(1, "lorem", "sadw", "09391252637", "eee@email.com")),
			ContactWrapper(Contact(1, "moas", "Asaddsad asi", "09391252637", "eee@email.com")),
			ContactWrapper(Contact(1, "pos", "dwad a", "09391252637", "eee@email.com")),
			ContactWrapper(Contact(1, "apppp", "dsad", "09391252637", "eee@email.com")),
			ContactWrapper(Contact(1, "awwdw", "Asawqwedi", "09391252637", "eee@email.com")),
			ContactWrapper(Contact(1, "qqqq", "Asqqqadi", "09391252637", "eee@email.com")),
		).sortedWith(compareBy { it.contact.name.lowercase() }).toMutableStateList()
	}
	
	ATSApp2Theme {
		MainComponent(contacts)
	}
}