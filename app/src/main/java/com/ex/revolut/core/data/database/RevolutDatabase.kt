package com.ex.revolut.core.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ex.revolut.core.data.rate.entities.BaseCurrencyDao
import com.ex.revolut.core.data.rate.entities.DatabaseBaseCurrencyRate
import com.ex.revolut.core.data.rate.entities.DatabaseRate
import com.ex.revolut.core.data.rate.entities.RateDao
import java.util.concurrent.Executors

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
        private val IO_EXECUTOR = Executors.newSingleThreadExecutor()

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
            ).addCallback(populateBaseCurrency(context)).build()

        /**
         * set the default base currency to EUR (Euros)
         * This will fetch
         */
        private fun populateBaseCurrency(context: Context): Callback {

            return object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)

                    ioThread {
                        getDatabase(context = context).baseCurrencyDao.insert(
                            DatabaseBaseCurrencyRate(recentBase = "EUR")
                        )
                    }
                }
            }
        }

        fun ioThread(f: () -> Unit) {
            IO_EXECUTOR.execute(f)
        }
    }
}