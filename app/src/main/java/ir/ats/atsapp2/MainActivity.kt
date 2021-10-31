package ir.ats.atsapp2

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import ir.ats.atsapp2.components.BaseDialog
import ir.ats.atsapp2.components.ContactComponent
import ir.ats.atsapp2.components.LetterGroupIndicator
import ir.ats.atsapp2.components.TopBarComponent
import ir.ats.atsapp2.components.anim.helper.ContactWrapper
import ir.ats.atsapp2.data.ATSApp2Database
import ir.ats.atsapp2.data.entities.Contact
import ir.ats.atsapp2.ext.wrapChildrenWith
import ir.ats.atsapp2.fragments.ContactManagementFragment
import ir.ats.atsapp2.fragments.ContactManagementRole
import ir.ats.atsapp2.ui.theme.ATSApp2Theme
import ir.ats.atsapp2.ui.theme.RoundedShapes

class MainActivity : ComponentActivity() {
	
	@ExperimentalAnimationApi
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
		val contacts = getAllData()
		
		setContent {
			ATSApp2Theme {
				MainComponent(contacts) {
					contacts.run {
						clear()
						addAll(getAllData())
					}
				}
			}
		}
	}
	
	private fun getAllData() = ATSApp2Database.Instance.contactDao().getAllContacts()
		.sortedWith(compareBy { it.name.lowercase() })
		.wrapChildrenWith { ContactWrapper(it) }
		.toMutableStateList()
}

var isContactManagerShowing by mutableStateOf(false)
var contactManagerRole: ContactManagementRole? = null
var contactToEdit: Contact? = null
var isDialogShowing by mutableStateOf(false)

@ExperimentalAnimationApi
@Composable
private fun MainComponent(
	contactWrappers: SnapshotStateList<ContactWrapper>,
	updateContactsList: () -> Unit
) {
	ContactInfoDialog(updateContactsList)
	
	Box {
		AnimatedVisibility(
			visible = isContactManagerShowing,
			modifier = Modifier.zIndex(1f),
			enter = slideInVertically({ -it / 5 }) + fadeIn(),
			exit = slideOutVertically({ -it / 5 }) + fadeOut()
		) {
			ContactManagementFragment(role = contactManagerRole!!, contactToEdit) {
				isContactManagerShowing = false
				updateContactsList()
			}
		}
		
		Column {
			TopBarComponent(
				onAddContact = {
					contactManagerRole = ContactManagementRole.Add
					isContactManagerShowing = true
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
							ContactComponent(contact = currentContactWrapper.contact) {
								contactToEdit = currentContactWrapper.contact
								isDialogShowing = true
							}
						}
					}
				}
			}
		}
	}
}

@Composable
private fun ContactInfoDialog(updateContactsList: () -> Unit) {
	val context = LocalContext.current
	
	BaseDialog(
		visible = isDialogShowing,
		onDismiss = { isDialogShowing = false }
	) {
		Column(
			verticalArrangement = Arrangement.spacedBy(16.dp),
			modifier = Modifier
				.background(MaterialTheme.colors.background, RoundedShapes.Medium)
				.padding(16.dp)
		) {
			Text(
				text = "${contactToEdit!!.name} ${contactToEdit!!.family}",
				textAlign = TextAlign.Center,
				softWrap = true,
				fontWeight = FontWeight.Bold,
				modifier = Modifier.align(Alignment.CenterHorizontally)
			)
			
			Divider()
			
			if (contactToEdit!!.email!!.trim().isNotEmpty()) {
				Text(
					text = contactToEdit!!.email!!,
					textAlign = TextAlign.Center,
					modifier = Modifier.align(Alignment.CenterHorizontally)
				)
			}
			
			Text(
				text = contactToEdit!!.number,
				textAlign = TextAlign.Center,
				modifier = Modifier.align(Alignment.CenterHorizontally)
			)
			
			Row(
				horizontalArrangement = Arrangement.spacedBy(8.dp),
				modifier = Modifier.align(Alignment.CenterHorizontally)
			) {
				IconButton(onClick = {
					isDialogShowing = false
					ATSApp2Database.Instance.contactDao().removeContact(contactToEdit!!.id!!)
					updateContactsList()
				}) {
					Icon(imageVector = Icons.Rounded.Delete, contentDescription = "delete", tint = Color.Red)
				}
				
				IconButton(onClick = {
					isDialogShowing = false
					contactManagerRole = ContactManagementRole.Edit
					isContactManagerShowing = true
				}) {
					Icon(imageVector = Icons.Rounded.Edit, contentDescription = "edit", tint = Color.Yellow)
				}
				
				IconButton(onClick = {
					isDialogShowing = false
					context.startActivity(
						Intent(
							Intent.ACTION_DIAL,
							Uri.parse("tel:${contactToEdit!!.number}")
						)
					)
				}) {
					Icon(imageVector = Icons.Rounded.Call, contentDescription = "call", tint = Color.Green)
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
		MainComponent(contacts) {}
	}
}