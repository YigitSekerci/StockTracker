package com.yigitsekerci.stocktrackerapp.data.remote.dto

import com.yigitsekerci.stocktrackerapp.domain.model.IntradayTradingInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

data class IntradayTradingInfoDto(
    val timestamp: String,
    val close: Double
) {
    fun toIntradayTradingInfo(): IntradayTradingInfo {
        val pattern = "yyyy-MM-dd HH:mm:ss"
        val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
        val localDateTime = LocalDateTime.parse(timestamp, formatter)
        return IntradayTradingInfo(
            date = localDateTime,
            close = close
        )
    }
}