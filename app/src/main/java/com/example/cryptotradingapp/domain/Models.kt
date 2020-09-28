package com.example.cryptotradingapp.domain

import androidx.room.PrimaryKey

/**
 * Models for all objects manipulated and presented by the app
 * */



//CryptoCurrency in user's wallet
data class Cryptocurrency(
    val id: String, //maybe don't need id
    val symbol: String,
    val quantity: Double,
    val lockedQuantity:Double
)

//Executed Trade, i.e. trades from order history
data class ExecutedTrade(
    val id: Long,
    val symbol: String,
    val quantity: Int,
    val isBuy: Boolean,
    val isLimit: Boolean
)

//OpenTrade i.e. current trades
data class OpenTrade (
    val id: Long,
    val symbol: String,
    val quantity: Int,
    val isBuy: Boolean,
    val isLimit: Boolean
)


data class MarketOrder(val isBuy:Boolean, val symbol: String, val quantity: Double)

data class LimitOrder(val isBuy:Boolean, val symbol: String, val quantity:Double, val executionPrice: Double)







