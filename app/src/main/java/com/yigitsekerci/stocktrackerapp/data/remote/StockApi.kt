package com.yigitsekerci.stocktrackerapp.data.remote

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {
    companion object{
        const val BASE_URL = "https://alphavantage.co"
        const val API_KEY = "MK2RJC9STY60IZOK"
    }

    @GET("query?function=LISTING_STATUS")
    suspend fun getStocks(
        @Query("apikey") apiKey: String
    ): ResponseBody
}