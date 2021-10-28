package ir.ats.atsapp2.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
	small = RoundedCornerShape(4.dp),
	medium = RoundedCornerShape(4.dp),
	large = RoundedCornerShape(0.dp)
)

object RoundedShapes {
	val Small = RoundedCornerShape(4.dp)
	val Medium = RoundedCornerShape(8.dp)
	val Large = RoundedCornerShape(16.dp)
}
