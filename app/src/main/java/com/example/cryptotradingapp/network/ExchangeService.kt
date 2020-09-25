package com.example.cryptotradingapp.network

import com.example.cryptotradingapp.viewmodels.limitOrder
import retrofit2.http.*

/**
 * Responsible for live market data such as prices and charts.
 */

interface ExchangeService {

    //make connection to market data

    //maybe stop connection to market data

    //fetch price for crypto


    @GET("/user/coin_price/{symbol}")
    suspend fun getCoinPrice(@Path("symbol") symbol:String): ResponseMessage

    @POST("/user/order/new/market")
    suspend fun placeMarketOrder(@Header("Authorization") authHeader:String): ResponseMessage

    @POST("/user/order/new/limit")
    suspend fun placeLimitOrder(@Header("Authorization") authHeader:String): ResponseMessage

    @POST("/user/test")
    suspend fun placeTestOrder(@Header("Authorization") authHeader:String, @Body limitOrder: limitOrder): ResponseMessage






}



//My interests and expertise aligns with programming and logic the most.
