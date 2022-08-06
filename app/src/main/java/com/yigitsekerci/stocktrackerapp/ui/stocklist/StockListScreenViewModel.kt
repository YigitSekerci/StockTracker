package com.yigitsekerci.stocktrackerapp.ui.stocklist

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yigitsekerci.stocktrackerapp.data.repository.StockRepository
import com.yigitsekerci.stocktrackerapp.domain.model.CompanyList
import com.yigitsekerci.stocktrackerapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@HiltViewModel
class StockListScreenViewModel @Inject constructor(
    private val repository: StockRepository
) : ViewModel() {
    var stockList = mutableStateListOf<CompanyList>()
    var searchTextFieldValue = mutableStateOf("")
    var isRefreshing = mutableStateOf(false)

    private var job: Job? = null

    init {
        refreshStockList()
    }

    fun onEvent(event: StockListScreenEvent) {
        when (event) {
            is StockListScreenEvent.RefreshStockList -> {
                refreshStockList(fromRemote = true)
            }
            is StockListScreenEvent.UpdateTextField -> {
                searchTextFieldValue.value = event.newValue
            }
            is StockListScreenEvent.SearchCompanyInfo -> {
                refreshStockList()
            }
        }
    }

    private fun refreshStockList(
        query: String = searchTextFieldValue.value.lowercase(),
        fromRemote: Boolean = false
    ) {
        job?.cancel()
        job = viewModelScope.launch {
            isRefreshing.value = true
            repository
                .getCompanyList(fromRemote = fromRemote, query = query)
                .collect { resource ->
                    when (resource) {
                        is Resource.Loading -> Unit
                        is Resource.Error -> Unit
                        is Resource.Success -> {
                            stockList.clear()
                            stockList.addAll(resource.data ?: emptyList())
                        }
                    }
                }
            isRefreshing.value = false
        }
    }
}