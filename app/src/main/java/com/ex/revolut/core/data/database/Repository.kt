package com.ex.revolut.core.data.database

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-10-10
 */
interface Repository<T> {
    fun deleteAll()
    fun insert(vararg d: T)
}