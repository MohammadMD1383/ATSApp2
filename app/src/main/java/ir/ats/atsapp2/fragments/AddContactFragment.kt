package ir.ats.atsapp2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment

class AddContactFragment : Fragment() {
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		return ComposeView(requireContext()).apply {
			setContent {
				MaterialTheme {
					MainComponent()
				}
			}
		}
	}
}

@Composable
private fun MainComponent() {
	Column {
		Row {
			IconButton(onClick = { }) {
				Icon(imageVector = Icons.Rounded.ArrowBackIosNew, contentDescription = "back")
			}
			
			Text(text = "Add Contact")
		}
		
		
	}
}

@Preview
@Composable
fun ComponentPreview() {
	MaterialTheme {
		MainComponent()
	}
}
