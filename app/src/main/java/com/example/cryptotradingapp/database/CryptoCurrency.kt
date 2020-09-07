package com.example.cryptotradingapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wallet_table")
data class CryptoCurrency constructor(

    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val symbol: String,
    val quantity: Double

)






    //model for cryptocurrency


