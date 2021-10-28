package ir.ats.atsapp2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import ir.ats.atsapp2.components.TextBoxComponent
import ir.ats.atsapp2.components.textTransformation.PhoneNumberMask
import ir.ats.atsapp2.data.ATSApp2Database
import ir.ats.atsapp2.data.entities.Contact
import ir.ats.atsapp2.ui.theme.RoundedShapes

class ContactManagementFragment(private val onFinished: () -> Unit) : Fragment() {
	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		return ComposeView(requireContext()).apply {
			setContent {
				MaterialTheme {
					MainComponent(onFinished)
				}
			}
		}
	}
}

@Composable
private fun MainComponent(onFinished: () -> Unit) {
	val contactName = remember { mutableStateOf(TextFieldValue()) }
	val contactFamily = remember { mutableStateOf(TextFieldValue()) }
	val contactNumber = remember { mutableStateOf(TextFieldValue()) }
	val contactEmail = remember { mutableStateOf(TextFieldValue()) }
	val scrollState = rememberScrollState()
	
	Column(modifier = Modifier.scrollable(scrollState, Orientation.Vertical)) {
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
				text = "Add Contact",
				fontSize = 18.sp,
				fontWeight = FontWeight.Bold,
			)
			
			IconButton(
				modifier = Modifier
					.shadow(4.dp, RoundedShapes.Small)
					.background(MaterialTheme.colors.background),
				onClick = {
					val isValid = validateInputs(contactName, contactNumber)
					if (isValid) {
						ATSApp2Database.Instance.contactDao().addContact(
							Contact(
								id = null,
								name = contactName.value.text,
								family = contactFamily.value.text,
								number = contactNumber.value.text,
								email = contactEmail.value.text
							)
						)
						
						onFinished()
					} else {
						/* Dialog */
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

@Preview(showBackground = true)
@Composable
private fun ComponentPreview() {
	MaterialTheme {
		MainComponent { }
	}
}
