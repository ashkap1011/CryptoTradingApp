package com.example.cryptotradingapp.network


/**
 * Contains objects used converting network objects
 * */

//TODO use GSON
data class CryptoCurrency(
    val id: Long,
    val symbol: String,
    val quantity: Double
)

class Wallet : ArrayList<CryptoCurrency>()

data class ResponseMessage(
    val isSuccessful: Boolean,
    val message: String,
)



/**
fun List<NetworkCryptoCurrency>.asDomainModel(): List<CryptoCurrency> {
    return map {
        CryptoCurrency(
            id = it.id,
            symbol = it.symbol,
            quantity = it.quantity
        )
    }
}*/