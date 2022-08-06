package com.yigitsekerci.stocktrackerapp.ui.stocklist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.yigitsekerci.stocktrackerapp.ui.theme.StockTrackerAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StockListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StockTrackerAppTheme {
                StockListScreen()
            }
        }
    }
}