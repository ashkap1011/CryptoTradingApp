package com.example.cryptotradingapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "executed_trades_table")
data class ExecutedTrade (

    @PrimaryKey(autoGenerate = true)
    val id: Long,

    val symbol: String,
    val quantity: Int,
    val isBuy: Boolean,
    val isLimit: Boolean
    //create created at



)