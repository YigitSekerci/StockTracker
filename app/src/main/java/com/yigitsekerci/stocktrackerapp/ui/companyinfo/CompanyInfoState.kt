package com.yigitsekerci.stocktrackerapp.ui.companyinfo

import com.yigitsekerci.stocktrackerapp.domain.model.CompanyInfo
import com.yigitsekerci.stocktrackerapp.domain.model.IntradayTradingInfo

data class CompanyInfoState(
    val stockInfos: List<IntradayTradingInfo> = emptyList(),
    val company: CompanyInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)