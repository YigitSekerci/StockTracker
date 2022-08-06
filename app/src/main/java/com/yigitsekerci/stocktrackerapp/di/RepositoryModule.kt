package com.yigitsekerci.stocktrackerapp.di

import com.yigitsekerci.stocktrackerapp.data.csv.CompanyListParser
import com.yigitsekerci.stocktrackerapp.data.csv.CsvParser
import com.yigitsekerci.stocktrackerapp.data.csv.IntradayTradingInfoParser
import com.yigitsekerci.stocktrackerapp.data.repository.StockRepository
import com.yigitsekerci.stocktrackerapp.data.repository.StockRepositoryImpl
import com.yigitsekerci.stocktrackerapp.domain.model.CompanyList
import com.yigitsekerci.stocktrackerapp.domain.model.IntradayTradingInfo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindCompanyListingsParser(
        companyListingsParser: CompanyListParser
    ): CsvParser<CompanyList>

    @Binds
    @Singleton
    abstract fun bindIntradayTradingInfoParser(
        intradayTradingInfoParser: IntradayTradingInfoParser
    ): CsvParser<IntradayTradingInfo>

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImpl: StockRepositoryImpl
    ): StockRepository
}