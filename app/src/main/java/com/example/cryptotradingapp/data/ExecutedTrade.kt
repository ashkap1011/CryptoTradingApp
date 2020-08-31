package com.example.cryptotradingapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ExecutedTrade (

    @PrimaryKey(autoGenerate = true)
    val id: Long,

    val symbol: String,
    val quantity: Int,
    val isBuy: Boolean,
    val isLimit: Boolean
    //create created at

)