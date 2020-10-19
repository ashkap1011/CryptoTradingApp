package com.example.cryptotradingapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cryptotradingapp.domain.Cryptocurrency
import com.example.cryptotradingapp.domain.OpenTrade
import com.example.cryptotradingapp.network.CurrencyNetwork

@Entity(tableName = "open_trades_table")
data class OpenTradeDB (

    @PrimaryKey()
    val id: String,
    val symbol: String,
    val quantity: Double,
    val orderType: Integer,
    val isBuy: Boolean,
)

fun List<OpenTradeDB>.asDomainModel(): List<OpenTrade>{
    return map{
        OpenTrade(
            id = it.id,
            symbol = it.symbol,
            quantity = it.quantity,
            orderType = it.orderType,
            isBuy = it.isBuy
        )
    }
}
