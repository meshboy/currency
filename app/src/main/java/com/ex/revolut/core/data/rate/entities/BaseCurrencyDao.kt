package com.ex.revolut.core.data.rate.entities

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-10-11
 */
@Dao
interface BaseCurrencyDao {
    @Query("SELECT * FROM base_currency LIMIT 1")
    fun getBaseCurrency(): LiveData<DatabaseBaseCurrencyRate>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg databaseBaseCurrencyRate: DatabaseBaseCurrencyRate)

    @Query("DELETE FROM base_currency")
    fun deleteAll()
}