package com.yigitsekerci.stocktrackerapp.ui.companyinfo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yigitsekerci.stocktrackerapp.data.repository.StockRepository
import com.yigitsekerci.stocktrackerapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@HiltViewModel
class CompanyInfoScreenViewModel @Inject constructor(
    private val repository: StockRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    var state by mutableStateOf(CompanyInfoState())

    init {
        viewModelScope.launch {
            val symbol =
                savedStateHandle.get<String>(CompanyInfoActivity.SYMBOL_KEY) ?: return@launch
            val companyInfoJob = async { repository.getCompanyInfo(symbol) }
            val stockInfoJob = async { repository.getIntradayTradingInfo(symbol) }
            when (val result = companyInfoJob.await()) {
                is Resource.Success -> {
                    state = state.copy(
                        company = result.data
                    )
                }
                is Resource.Error -> {
                    state = state.copy(
                        error = result.message
                    )
                }
                is Resource.Loading -> {
                    state = state.copy(
                        isLoading = result.isLoading
                    )
                }
            }
            when (val result = stockInfoJob.await()) {
                is Resource.Success -> {
                    state = state.copy(
                        stockInfos = result.data ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    state = state.copy(
                        error = result.message
                    )
                }
                is Resource.Loading -> {
                    state = state.copy(
                        isLoading = result.isLoading
                    )
                }
            }
        }
    }
}