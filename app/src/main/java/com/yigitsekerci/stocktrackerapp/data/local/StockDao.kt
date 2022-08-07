package com.yigitsekerci.stocktrackerapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StockDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanyList(entities: List<CompanyListEntity>)

    @Query("DELETE FROM companylistentity")
    suspend fun clearCompanyList()

    @Query("SELECT * FROM companylistentity WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' OR UPPER(:query) == symbol")
    suspend fun getCompanyList(query: String): List<CompanyListEntity>
}