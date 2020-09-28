package com.example.cryptotradingapp.database.entities

import androidx.lifecycle.Transformations
import androidx.lifecycle.Transformations.map
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


fun WalletItemDB.asDomainModel():Cryptocurrency {
    return Cryptocurrency(this.id,this.symbol,this.quantity, this.lockedQuantity)
     }



fun List<WalletItemDB>.asDomainModels(): List<Cryptocurrency> {
    return map {
        Cryptocurrency(
            id = it.id,
            symbol = it.symbol,
            quantity = it.quantity,
            lockedQuantity = it.lockedQuantity)
    }
}


