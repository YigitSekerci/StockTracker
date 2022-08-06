package com.yigitsekerci.stocktrackerapp.data.repository

import com.yigitsekerci.stocktrackerapp.data.csv.CsvParser
import com.yigitsekerci.stocktrackerapp.data.local.StockDatabase
import com.yigitsekerci.stocktrackerapp.data.remote.StockApi
import com.yigitsekerci.stocktrackerapp.domain.model.CompanyInfo
import com.yigitsekerci.stocktrackerapp.domain.model.CompanyList
import com.yigitsekerci.stocktrackerapp.domain.model.IntradayTradingInfo
import com.yigitsekerci.stocktrackerapp.util.Resource
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val api: StockApi,
    private val database: StockDatabase,
    private val companyListParser: CsvParser<CompanyList>,
    private val intradayInfoParser: CsvParser<IntradayTradingInfo>
) : StockRepository {
    private val dao = database.dao

    override suspend fun getCompanyList(
        fromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyList>>> = flow {
        emit(Resource.Loading(isLoading = true))
        val localList = dao.getCompanyList(query)
        emit(Resource.Success(data = localList.map { it.toCompanyList() }))
        val shouldFetchFromRemote = localList.isEmpty() || fromRemote

        if (!shouldFetchFromRemote) {
            emit(Resource.Loading(isLoading = false))
            return@flow
        }

        val remoteList = try {
            val response = api.getStockList()
            companyListParser.parse(response.byteStream())
        } catch (ioException: IOException) {
            emit(Resource.Error(message = ioException.message))
            null
        } catch (httpException: HttpException) {
            emit(Resource.Error(message = httpException.message))
            null
        }

        remoteList?.let { list ->
            dao.clearCompanyList()
            dao.insertCompanyList(list.map { it.toCompanyListEntity() })
            emit(Resource.Success(dao.getCompanyList("").map { it.toCompanyList() }))
            emit(Resource.Loading(isLoading = false))
        }
    }

    override suspend fun getIntradayTradingInfo(symbol: String): Resource<List<IntradayTradingInfo>> {
        return try {
            val response = api.getIntradayTradingInfo(symbol)
            val results = intradayInfoParser.parse(response.byteStream())
            Resource.Success(results)
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(
                message = "Couldn't load intraday trading info"
            )
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(
                message = "Couldn't load intraday trading info"
            )
        }
    }

    override suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo> {
        return try {
            val result = api.getCompanyInfo(symbol)
            Resource.Success(result.toCompanyInfo())
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(
                message = "Couldn't load company info"
            )
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(
                message = "Couldn't load company info"
            )
        }
    }

}