package ir.hamid.ilearn

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import java.util.Calendar

@HiltAndroidApp
class IlearnApplication : Application() {

    companion object {
        fun getStartOfDayTimestamp(): Long {
            val calendar = Calendar.getInstance()

            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            return calendar.timeInMillis
        }
    }
}