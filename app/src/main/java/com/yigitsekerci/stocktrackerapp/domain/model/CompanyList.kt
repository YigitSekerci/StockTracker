package com.yigitsekerci.stocktrackerapp.domain.model

import com.yigitsekerci.stocktrackerapp.data.local.CompanyListEntity

data class CompanyList(
    val name:String,
    val symbol: String,
    val exchange: String,
){
    fun toCompanyListEntity(): CompanyListEntity{
        return CompanyListEntity(
            name = name,
            symbol = symbol,
            exchange = exchange
        )
    }
}
