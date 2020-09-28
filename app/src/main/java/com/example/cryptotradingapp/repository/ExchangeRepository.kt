package com.example.cryptotradingapp.repository

import android.app.Application
import android.content.Context
import android.util.Base64
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.cryptotradingapp.R
import com.example.cryptotradingapp.domain.LimitOrder
import com.example.cryptotradingapp.domain.MarketOrder
import com.example.cryptotradingapp.network.*


class ExchangeRepository(app: Application) {
    private val userService = RetrofitInstance.getRetrofitInstance().create(UserService::class.java)
    private val exchangeService = RetrofitInstance.getRetrofitInstance().create(ExchangeService::class.java)
    private val marketDataService = MarketDataService()
    private val application = app
    private val resources = application.resources

    suspend fun getCoinPrice(symbol:String): ResponseMessage{
       return exchangeService.getCoinPrice(symbol)

    }

    suspend fun placeMarketOrder(authHeader: String, order: MarketOrder): ResponseMessage{

        return exchangeService.placeMarketOrder(authHeader,order)
    }

    suspend fun placeLimitOrder(authHeader:String, order: LimitOrder): ResponseMessage{
        return exchangeService.placeLimitOrder(authHeader,order)
    }

    fun startConnection() : LiveData<List<MarketCoin>>{
        marketDataService.startConnection()
        return marketDataService.marketData
    }


}