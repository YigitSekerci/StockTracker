package com.yigitsekerci.stocktrackerapp.data.remote.dto

import com.squareup.moshi.Json
import com.yigitsekerci.stocktrackerapp.domain.model.CompanyInfo

data class CompanyInfoDto(
    @field:Json(name = "Symbol") val symbol: String?,
    @field:Json(name = "Description") val description: String?,
    @field:Json(name = "Name") val name: String?,
    @field:Json(name = "Country") val country: String?,
    @field:Json(name = "Industry") val industry: String?,
) {
    fun toCompanyInfo(): CompanyInfo {
        return CompanyInfo(
            symbol = symbol ?: "",
            description = description ?: "",
            name = name ?: "",
            country = country ?: "",
            industry = industry ?: ""
        )
    }
}