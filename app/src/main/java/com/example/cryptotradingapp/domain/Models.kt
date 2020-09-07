package com.example.cryptotradingapp.domain

import androidx.room.PrimaryKey

/**
 * Models for all objects manipulated and presented by the app
 * */



//CryptoCurrency in user's wallet

data class CryptoCurrency(
    val id: Long, //maybe don't need id
    val symbol: String,
    val quantity: Double
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






data class DevByteVideo(val title: String,
                        val description: String,
                        val url: String,
                        val updated: String,
                        val thumbnail: String)

