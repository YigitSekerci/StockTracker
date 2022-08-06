package com.yigitsekerci.stocktrackerapp.ui.stocklist

sealed class StockListScreenEvent {
    object RefreshStockList : StockListScreenEvent()
    object SearchCompanyInfo : StockListScreenEvent()
    data class UpdateTextField(
        val newValue: String
    ) : StockListScreenEvent()
}
