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
interface RateDao {

    @Query("SELECT * FROM rates")
    fun getRatesByBase(): LiveData<List<DatabaseRate>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg databaseRate: DatabaseRate)

    @Query("DELETE FROM rates")
    fun deleteAll()
}