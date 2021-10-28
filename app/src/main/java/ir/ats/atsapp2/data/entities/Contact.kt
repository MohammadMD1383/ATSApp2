package ir.ats.atsapp2.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// @formatter:off
@Entity
data class Contact(
	@PrimaryKey(autoGenerate = true) val id     : Long,
	@ColumnInfo                      val name   : String,
	@ColumnInfo                      val family : String?,
	@ColumnInfo                      val number : String,
	@ColumnInfo                      val email  : String? = null,
)
// @formatter:on