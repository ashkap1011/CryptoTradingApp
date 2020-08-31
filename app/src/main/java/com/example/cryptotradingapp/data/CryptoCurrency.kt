package com.example.cryptotradingapp.data

import androidx.room.Entity

@Entity(tableName = "wallet_table")
data class CryptoCurrency (
    val symbol: String,
    val quantity: Int
)





    //model for cryptocurrency


