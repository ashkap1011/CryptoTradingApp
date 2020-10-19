package com.example.cryptotradingapp.network

import androidx.annotation.IntegerRes
import com.example.cryptotradingapp.database.entities.OpenTradeDB
import com.example.cryptotradingapp.database.entities.WalletItemDB
import kotlinx.android.synthetic.main.fragment_trading.view.*


/**
 * Contains objects used converting network objects
 * */
data class ResponseMessage(
    val isSuccessful: Boolean,
    val message: String,
)
data class MarketCoin(
    val symbol: String,
    val price: Double
)
class MarketData: ArrayList<MarketCoin>()

data class OpenOrderNetwork(
    val _id: String,
    val orderType: Integer,
    val isBuy: Boolean,
    val currency: CurrencyNetwork,
    val executionPrice: Double
)
data class CurrencyNetwork(
    val symbol: String,
    val quantity: Double
)
class OpenOrdersNetwork: ArrayList<OpenOrderNetwork>()

//TODO use GSON
data class WalletItemNetwork(
    val _id: String,
    val symbol: String,
    val quantity: Double,
    val lockedQuantity: Double
)
class NetworkWallet : ArrayList<WalletItemNetwork>()

/*
* Converts network objects to database objects
* */
fun NetworkWallet.asDatabaseModel(): List<WalletItemDB> {
    return map {
        WalletItemDB(
            id = it._id,
            symbol = it.symbol,
            quantity = it.quantity,
            lockedQuantity = it.lockedQuantity
            )
    }
}

fun OpenOrdersNetwork.asDatabaseModel(): List<OpenTradeDB>{
    return map{
        OpenTradeDB(
            id = it._id,
            symbol = it.currency.symbol,
            quantity = it.currency.quantity,
            orderType = it.orderType,
            isBuy = it.isBuy)
    }
}