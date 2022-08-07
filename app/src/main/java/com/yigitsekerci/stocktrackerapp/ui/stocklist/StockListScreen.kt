package com.yigitsekerci.stocktrackerapp.ui.stocklist

import android.app.Activity
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.yigitsekerci.stocktrackerapp.R
import com.yigitsekerci.stocktrackerapp.domain.model.CompanyList
import com.yigitsekerci.stocktrackerapp.ui.companyinfo.CompanyInfoActivity

@Composable
fun StockListScreen() {
    val columnPadding = 12.dp
    val columnSpacing = 12.dp

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.primary
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(columnSpacing),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = columnPadding)
        ) {
            AppName()
            SearchBar()
            SwipeRefreshCompanyList()
        }
    }
}

@Composable
fun AppName() {
    val fontSize = 24.sp
    Text(
        text = stringResource(id = R.string.app_name),
        color = MaterialTheme.colorScheme.tertiary,
        fontSize = fontSize,
        fontWeight = FontWeight.SemiBold
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    viewModel: StockListScreenViewModel = hiltViewModel()
) {
    val textFieldMainColor = MaterialTheme.colorScheme.secondary
    val textFieldFontSize = 18.sp
    val elevation = 1.dp

    OutlinedTextField(
        value = viewModel.searchTextFieldValue.value,
        onValueChange = { newValue ->
            viewModel.onEvent(StockListScreenEvent.UpdateTextField(newValue))
        },
        enabled = true,
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation),
        singleLine = true,
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.tertiary,
            textAlign = TextAlign.Left,
            fontStyle = FontStyle.Normal,
            fontSize = textFieldFontSize
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = textFieldMainColor,
            disabledBorderColor = textFieldMainColor,
            focusedBorderColor = textFieldMainColor,
            unfocusedBorderColor = textFieldMainColor,
            cursorColor = textFieldMainColor,
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                viewModel.onEvent(StockListScreenEvent.SearchCompanyInfo)
            },
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
    )
}

@Composable
fun SwipeRefreshCompanyList(
    viewModel: StockListScreenViewModel = hiltViewModel()
) {
    val swipeState = rememberSwipeRefreshState(isRefreshing = viewModel.isRefreshing.value)
    val spaceBetweenStocks = 8.dp
    SwipeRefresh(
        state = swipeState,
        onRefresh = {
            viewModel.onEvent(StockListScreenEvent.RefreshStockList)
        },
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(spaceBetweenStocks),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            items(viewModel.stockList.size) { index ->
                CompanyListInfoComp(companyList = viewModel.stockList[index])
            }
        }
    }
}

@Composable
fun CompanyListInfoComp(
    companyList: CompanyList
) {
    val context = LocalContext.current as Activity
    val rowHeight = 100.dp
    val rowPadding = 12.dp
    val rowBorder = 1.dp
    val rowColor = MaterialTheme.colorScheme.secondary
    val cornerSize = 4.dp
    val shape = RoundedCornerShape(cornerSize)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(rowHeight)
            .graphicsLayer {
                this.shape = shape
                clip = true
            }
            .border(rowBorder, rowColor, shape)
            .clickable {
                CompanyInfoActivity.startActivity(context, companyList.symbol)
            }
            .padding(rowPadding),
    ) {
        Column(
            modifier = Modifier.weight(7f)
        ) {
            Text(
                text = companyList.name,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.tertiary,
                maxLines = 1
            )
            Text(
                text = companyList.symbol,
                color = MaterialTheme.colorScheme.tertiary,
                textDecoration = TextDecoration.Underline,
                fontStyle = FontStyle.Italic
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = companyList.exchange,
            modifier = Modifier.weight(2f),
            color = MaterialTheme.colorScheme.tertiary,
            textAlign = TextAlign.Center
        )
    }
}