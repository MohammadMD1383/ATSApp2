package ir.ats.atsapp2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ir.ats.atsapp2.data.dao.ContactDao
import ir.ats.atsapp2.data.entities.Contact

@Database(entities = [Contact::class], version = 1)
abstract class ATSApp2Database : RoomDatabase() {
	companion object {
		@Volatile
		lateinit var Instance: ATSApp2Database
			private set
		
		fun initialize(context: Context) {
			Instance = Room.databaseBuilder(
				context,
				ATSApp2Database::class.java,
				"ats_app2.db"
			).build()
		}
	}
	
	abstract fun contactDao(): ContactDao
}