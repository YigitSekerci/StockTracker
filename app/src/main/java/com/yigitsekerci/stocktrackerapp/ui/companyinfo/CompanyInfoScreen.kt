package com.yigitsekerci.stocktrackerapp.ui.companyinfo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yigitsekerci.stocktrackerapp.R
import com.yigitsekerci.stocktrackerapp.domain.model.CompanyInfo
import com.yigitsekerci.stocktrackerapp.domain.model.IntradayTradingInfo

@Composable
fun CompanyInfoScreen(
    companyInfoScreenViewModel: CompanyInfoScreenViewModel = hiltViewModel()
) {
    val state = companyInfoScreenViewModel.state
    val scrollState = rememberLazyListState()
    val columnPadding = 8.dp
    val columnSpace = 8.dp
    when (state.error) {
        null -> {
            state.company?.let {
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(columnSpace),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(columnPadding),
                    userScrollEnabled = true,
                    state = scrollState
                ) {
                    companyInfoComp(info = it)
                    intradayTradingInfoComp(stockInfos = state.stockInfos)
                }
            }
        }
        else -> {
            ErrorText(errorText = state.error)
        }
    }
    if (state.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

private fun LazyListScope.companyInfoComp(
    info: CompanyInfo
) {
    item {
        CompanyInfoColumn(
            columnName = stringResource(R.string.company_info_name),
            columnText = info.name,
            fontWeight = FontWeight.SemiBold
        )
    }
    item {
        CompanyInfoColumn(
            columnName = stringResource(R.string.company_info_symbol),
            columnText = info.symbol,
            fontStyle = FontStyle.Italic
        )
    }
    item {
        CompanyInfoColumn(
            columnName = stringResource(R.string.company_info_country),
            columnText = info.country
        )
    }
    item {
        CompanyInfoColumn(
            columnName = stringResource(R.string.company_info_description),
            columnText = info.description
        )
    }
}

@Composable
fun CompanyInfoColumn(
    columnName: String,
    columnText: String,
    fontWeight: FontWeight = FontWeight.Normal,
    fontStyle: FontStyle = FontStyle.Normal,
    fontSize: TextUnit = 14.sp,
    shouldBreak: Boolean = true,
) {
    val rowSpacing = 4.dp
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(rowSpacing),
        horizontalAlignment = Alignment.Start
    ) {
        CompanyInfoTexts(
            text = columnName,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal,
            fontSize = fontSize,
            shouldBreak = false
        )
        Divider(
            Modifier.fillMaxWidth(),
            color = DividerDefaults.color.copy(alpha = 0.5f)
        )
        CompanyInfoTexts(
            text = columnText,
            fontWeight = fontWeight,
            fontStyle = fontStyle,
            fontSize = fontSize,
            shouldBreak = shouldBreak,
        )
    }
}

@Composable
private fun CompanyInfoTexts(
    text: String,
    fontWeight: FontWeight = FontWeight.Normal,
    fontStyle: FontStyle = FontStyle.Normal,
    fontSize: TextUnit = 14.sp,
    shouldBreak: Boolean = true,
) {
    Text(
        text = text,
        fontSize = fontSize,
        fontWeight = fontWeight,
        fontStyle = fontStyle,
        textAlign = TextAlign.Justify,
        overflow = if (shouldBreak) TextOverflow.Ellipsis else TextOverflow.Visible,
        color = MaterialTheme.colorScheme.tertiary
    )
}

private fun LazyListScope.intradayTradingInfoComp(
    stockInfos: List<IntradayTradingInfo>
) {
    item {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text(text = "Value", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "Date", fontWeight = FontWeight.Bold)
        }
    }
    item {
        Divider(modifier = Modifier.fillMaxWidth())
    }
    items(stockInfos) { item ->
        Row(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text(text = "${item.close}")
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "${item.date}")
        }
    }
}

@Composable
private fun ErrorText(
    errorText: String
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = errorText,
            color = MaterialTheme.colorScheme.error
        )
    }
}