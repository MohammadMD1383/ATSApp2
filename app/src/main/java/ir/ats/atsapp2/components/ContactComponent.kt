package ir.ats.atsapp2.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PermIdentity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.ats.atsapp2.data.entities.Contact
import ir.ats.atsapp2.ui.theme.ATSApp2Theme

@Composable
fun ContactComponent(
	contact: Contact,
	onClick: () -> Unit
) {
	Row(
		verticalAlignment = Alignment.CenterVertically,
		modifier = Modifier
			.fillMaxWidth()
			.clickable(onClick = onClick)
	
	) {
		Icon(
			imageVector = Icons.Rounded.PermIdentity,
			contentDescription = "contact",
			modifier = Modifier
				.border(1.dp, Color.LightGray, CircleShape)
				.padding(16.dp)
		)
		
		Spacer(modifier = Modifier.width(24.dp))
		
		Text(text = "${contact.name} ${contact.family}")
	}
}

@Preview(showBackground = true)
@Composable
private fun ComponentPreview() {
	ATSApp2Theme {
		ContactComponent(contact = Contact(0, "John", "Doe", "09391252637")) {}
	}
}
