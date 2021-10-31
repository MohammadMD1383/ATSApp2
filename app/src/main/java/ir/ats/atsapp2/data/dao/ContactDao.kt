package ir.ats.atsapp2.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ir.ats.atsapp2.data.entities.Contact

@Dao
interface ContactDao {
	@Insert
	fun addContact(contact: Contact)
	
	@Update
	fun updateContact(contact: Contact)
	
	@Query("DELETE FROM Contact WHERE id=:id")
	fun removeContact(id: Long)
	
	@Query("SELECT * FROM Contact")
	fun getAllContacts(): List<Contact>
}