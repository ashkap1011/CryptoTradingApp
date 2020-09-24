package com.example.cryptotradingapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "open_trades_table")
data class OpenTrade (
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    val symbol: String,
    val quantity: Int,
    val isBuy: Boolean,
    val isLimit: Boolean
)