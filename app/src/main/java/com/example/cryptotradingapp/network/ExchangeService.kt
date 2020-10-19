package com.example.cryptotradingapp.network

import android.util.Log
import com.example.cryptotradingapp.domain.LimitOrder
import com.example.cryptotradingapp.domain.MarketOrder
import retrofit2.http.*

/**
 * Responsible for live market data such as prices and charts.
 */

interface ExchangeService {

    //maybe stop connection to market data

    //fetch price for crypto

    @GET("/user/coin_price/{symbol}")
    suspend fun getCoinPrice(@Path("symbol") symbol:String): ResponseMessage

    @GET("/user/opentrades")
    suspend fun getOpenTrades(@Header("Authorization") authHeader:String): OpenOrdersNetwork

    @POST("/user/order/new/market")
    suspend fun placeMarketOrder(@Header("Authorization") authHeader:String, @Body order: MarketOrder): ResponseMessage

    @POST("/user/order/new/limit")
    suspend fun placeLimitOrder(@Header("Authorization") authHeader:String, @Body order: LimitOrder): ResponseMessage


}





//My interests and expertise aligns with programming and logic the most.
