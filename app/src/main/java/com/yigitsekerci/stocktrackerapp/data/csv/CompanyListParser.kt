package com.yigitsekerci.stocktrackerapp.data.csv

import com.opencsv.CSVReader
import com.yigitsekerci.stocktrackerapp.domain.model.CompanyList
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
class CompanyListParser @Inject constructor() : CsvParser<CompanyList> {
    override suspend fun parse(stream: InputStream): List<CompanyList> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO) {
            csvReader
                .readAll()
                .drop(1)
                .mapNotNull { oneLine ->
                    val symbol = oneLine.getOrNull(0)
                    val name = oneLine.getOrNull(1)
                    val exchange = oneLine.getOrNull(2)
                    CompanyList(
                        name = name ?: return@mapNotNull null,
                        symbol = symbol ?: return@mapNotNull null,
                        exchange = exchange ?: return@mapNotNull null
                    )
                }
                .also {
                    csvReader.close()
                }
        }
    }
}