package ir.ats.atsapp2.components.anim.helper

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import ir.ats.atsapp2.data.entities.Contact

data class ContactWrapper(
	val contact: Contact,
	var visible: MutableState<Boolean> = mutableStateOf(true),
)
