package com.yigitsekerci.stocktrackerapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yigitsekerci.stocktrackerapp.domain.model.CompanyList

@Entity
data class CompanyListEntity(
    val name: String,
    val symbol: String,
    val exchange: String,
    @PrimaryKey val id: Int? = null
) {
    fun toCompanyList(): CompanyList {
        return CompanyList(
            name = name,
            symbol = symbol,
            exchange = exchange
        )
    }
}
