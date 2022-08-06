package com.yigitsekerci.stocktrackerapp.data.repository

import com.yigitsekerci.stocktrackerapp.domain.model.CompanyInfo
import com.yigitsekerci.stocktrackerapp.domain.model.CompanyList
import com.yigitsekerci.stocktrackerapp.domain.model.IntradayTradingInfo
import com.yigitsekerci.stocktrackerapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {
    suspend fun getCompanyList(
        fromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyList>>>

    suspend fun getIntradayTradingInfo(
        symbol: String
    ): Resource<List<IntradayTradingInfo>>

    suspend fun getCompanyInfo(
        symbol: String
    ): Resource<CompanyInfo>
}