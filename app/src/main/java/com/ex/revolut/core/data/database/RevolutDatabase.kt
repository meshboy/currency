package com.ex.revolut.core.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ex.revolut.core.data.rate.entities.BaseCurrencyDao
import com.ex.revolut.core.data.rate.entities.DatabaseBaseCurrencyRate
import com.ex.revolut.core.data.rate.entities.DatabaseRate
import com.ex.revolut.core.data.rate.entities.RateDao

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-10-10
 */
@Database(
    entities = [
        DatabaseRate::class,
        DatabaseBaseCurrencyRate::class
    ],
    version = 1,
    exportSchema = true
)
abstract class RevolutDatabase : RoomDatabase() {

    abstract val rateDao: RateDao
    abstract val baseCurrencyDao: BaseCurrencyDao

    companion object {
        private lateinit var INSTANCE: RevolutDatabase

        fun getDatabase(context: Context): RevolutDatabase {
            synchronized(RevolutDatabase::class.java) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = buildRoomDatabase(context)
                }
            }
            return INSTANCE
        }

        private fun buildRoomDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                RevolutDatabase::class.java,
                "revolut"
            ).build()
    }
}