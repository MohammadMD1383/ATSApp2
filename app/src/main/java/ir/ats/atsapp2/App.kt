package ir.ats.atsapp2

import android.app.Application
import ir.ats.atsapp2.data.ATSApp2Database

class App : Application() {
	override fun onCreate() {
		super.onCreate()
		
		ATSApp2Database.initialize(this)
	}
}