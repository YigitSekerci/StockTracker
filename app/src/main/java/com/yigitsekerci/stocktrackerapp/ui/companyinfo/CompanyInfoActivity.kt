package com.yigitsekerci.stocktrackerapp.ui.companyinfo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.yigitsekerci.stocktrackerapp.ui.theme.StockTrackerAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompanyInfoActivity : ComponentActivity() {
    companion object {
        const val SYMBOL_KEY = "symbol_key"

        fun startActivity(
            context: Context,
            symbol: String,
        ) {
            Intent(context, CompanyInfoActivity::class.java).apply {
                putExtra(SYMBOL_KEY, symbol)
                context.startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StockTrackerAppTheme {
                CompanyInfoScreen()
            }
        }
    }
}