package ir.ats.atsapp2.fragments

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.ats.atsapp2.components.AlertDialog
import ir.ats.atsapp2.components.TextBoxComponent
import ir.ats.atsapp2.components.textTransformation.PhoneNumberMask
import ir.ats.atsapp2.data.ATSApp2Database
import ir.ats.atsapp2.data.entities.Contact
import ir.ats.atsapp2.ui.theme.RoundedShapes

enum class ContactManagementRole(val title: String) {
	Add("Add Contact"),
	Edit("Edit Contact")
}

@Composable
fun ContactManagementFragment(
	role: ContactManagementRole,
	contact: Contact? = null,
	onFinished: () -> Unit
) {
	val contactName = remember { mutableStateOf(TextFieldValue()) }
	val contactFamily = remember { mutableStateOf(TextFieldValue()) }
	val contactNumber = remember { mutableStateOf(TextFieldValue()) }
	val contactEmail = remember { mutableStateOf(TextFieldValue()) }
	val scrollState = rememberScrollState()
	var dialogVisible by remember { mutableStateOf(false) }
	
	if (role == ContactManagementRole.Edit) {
		contact!!
		contactName.value = TextFieldValue(contact.name)
		contact.family?.let { contactFamily.value = TextFieldValue(it) }
		contactNumber.value = TextFieldValue(contact.number)
		contact.email?.let { contactEmail.value = TextFieldValue(it) }
	}
	
	AlertDialog(
		visible = dialogVisible,
		onDismiss = { dialogVisible = false }
	) {
		Text(
			text = "Please fill out all required fields",
			softWrap = true,
		)
	}
	
	Column(
		modifier = Modifier
			.scrollable(scrollState, Orientation.Vertical)
			.fillMaxSize()
			.background(MaterialTheme.colors.background)
	) {
		Row(
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.SpaceBetween,
			modifier = Modifier
				.padding(8.dp)
				.fillMaxWidth()
		) {
			IconButton(
				modifier = Modifier
					.shadow(4.dp, RoundedShapes.Small)
					.background(MaterialTheme.colors.background),
				onClick = { onFinished() }
			) {
				Icon(imageVector = Icons.Rounded.ArrowBackIosNew, contentDescription = "back")
			}
			
			Text(
				text = role.title,
				fontSize = 18.sp,
				fontWeight = FontWeight.Bold,
			)
			
			IconButton(
				modifier = Modifier
					.shadow(4.dp, RoundedShapes.Small)
					.background(MaterialTheme.colors.background),
				onClick = {
					if (validateInputs(contactName, contactNumber)) {
						val contactId =
							if (role == ContactManagementRole.Edit) contact!!.id
							else null
						
						val newContact = Contact(
							id = contactId,
							name = contactName.value.text,
							family = contactFamily.value.text,
							number = contactNumber.value.text,
							email = contactEmail.value.text
						)
						
						val contactDao = ATSApp2Database.Instance.contactDao()
						
						if (role == ContactManagementRole.Add)
							contactDao.addContact(newContact)
						else
							contactDao.updateContact(newContact)
						
						onFinished()
					} else {
						dialogVisible = true
					}
				}
			) {
				Icon(imageVector = Icons.Rounded.Check, contentDescription = "Save")
			}
		}
		
		Spacer(modifier = Modifier.height(16.dp))
		
		TextBoxComponent(
			placeholder = { Text(text = "Name") },
			text = contactName,
			modifier = Modifier
				.padding(vertical = 4.dp, horizontal = 16.dp)
				.fillMaxWidth()
		)
		
		TextBoxComponent(
			placeholder = { Text(text = "Family") },
			text = contactFamily,
			modifier = Modifier
				.padding(vertical = 4.dp, horizontal = 16.dp)
				.fillMaxWidth()
		)
		
		TextBoxComponent(
			placeholder = { Text(text = "Number") },
			text = contactNumber,
			visualTransformation = PhoneNumberMask(),
			keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
			modifier = Modifier
				.padding(vertical = 4.dp, horizontal = 16.dp)
				.fillMaxWidth()
		)
		
		TextBoxComponent(
			placeholder = { Text(text = "Email") },
			text = contactEmail,
			keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
			modifier = Modifier
				.padding(vertical = 4.dp, horizontal = 16.dp)
				.fillMaxWidth()
		)
	}
}

private fun validateInputs(
	contactName: MutableState<TextFieldValue>,
	contactNumber: MutableState<TextFieldValue>
): Boolean {
	return !(contactName.value.text.trim().isEmpty() || contactNumber.value.text.trim().isEmpty())
}

@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
private fun ComponentPreview() {
	MaterialTheme {
		ContactManagementFragment(ContactManagementRole.Add) {}
	}
}
