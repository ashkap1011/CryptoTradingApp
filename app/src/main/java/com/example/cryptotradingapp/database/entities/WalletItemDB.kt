package com.example.cryptotradingapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cryptotradingapp.domain.Cryptocurrency

@Entity(tableName = "wallet_table")
data class WalletItemDB constructor(
    @PrimaryKey()
    val id: String,
    val symbol: String,
    val quantity: Double,
    val lockedQuantity:Double
)


fun List<WalletItemDB>.asDomainModel(): List<Cryptocurrency> {
    return map {
        Cryptocurrency(
            id = it.id,
            symbol = it.symbol,
            quantity = it.quantity,
            lockedQuantity = it.lockedQuantity)
    }
}


