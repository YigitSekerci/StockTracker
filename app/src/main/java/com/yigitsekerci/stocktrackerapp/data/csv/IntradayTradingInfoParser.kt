package com.yigitsekerci.stocktrackerapp.data.csv


import com.opencsv.CSVReader
import com.yigitsekerci.stocktrackerapp.data.remote.dto.IntradayTradingInfoDto
import com.yigitsekerci.stocktrackerapp.domain.model.IntradayTradingInfo
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
class IntradayTradingInfoParser @Inject constructor() : CsvParser<IntradayTradingInfo> {
    override suspend fun parse(stream: InputStream): List<IntradayTradingInfo> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO) {
            csvReader
                .readAll()
                .drop(1)
                .mapNotNull { line ->
                    val timestamp = line.getOrNull(0) ?: return@mapNotNull null
                    val close = line.getOrNull(4) ?: return@mapNotNull null
                    val dto = IntradayTradingInfoDto(timestamp, close.toDouble())
                    dto.toIntradayTradingInfo()
                }
                .filter {
                    it.date.dayOfMonth == LocalDate.now().minusDays(4).dayOfMonth
                }
                .sortedBy {
                    it.date.hour
                }
                .also {
                    csvReader.close()
                }
        }
    }
}