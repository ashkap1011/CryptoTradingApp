package com.example.cryptotradingapp.network

import com.example.cryptotradingapp.database.entities.WalletItemDB


/**
 * Contains objects used converting network objects
 * */


data class ResponseMessage(
    val isSuccessful: Boolean,
    val message: String,
)


data class MarketCoin(
    val symbol: String,
    val price: Double
)

class MarketData: ArrayList<MarketCoin>()





//TODO use GSON
data class WalletItemNetwork(
    val _id: String,
    val symbol: String,
    val quantity: Double,
    val lockedQuantity: Double
)

class NetworkWallet : ArrayList<WalletItemNetwork>()


/*
* Converts network objects to database objects
* */
fun NetworkWallet.asDatabaseModel(): List<WalletItemDB> {
    return map {
        WalletItemDB(
            id = it._id,
            symbol = it.symbol,
            quantity = it.quantity,
            lockedQuantity = it.lockedQuantity
            )
    }
}