package com.yigitsekerci.stocktrackerapp.domain.model

import java.time.LocalDateTime

data class IntradayTradingInfo(
    val date: LocalDateTime,
    val close: Double
)