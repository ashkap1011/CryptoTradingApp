package com.example.cryptotradingapp.repository

import android.app.Application
import android.content.Context
import android.util.Base64
import android.util.Log
import com.example.cryptotradingapp.R
import com.example.cryptotradingapp.network.ExchangeService
import com.example.cryptotradingapp.network.ResponseMessage
import com.example.cryptotradingapp.network.RetrofitInstance
import com.example.cryptotradingapp.network.UserService
import com.example.cryptotradingapp.viewmodels.limitOrder


class ExchangeRepository(app: Application) {
    private val userService = RetrofitInstance.getRetrofitInstance().create(UserService::class.java)
    private val exchangeService = RetrofitInstance.getRetrofitInstance().create(ExchangeService::class.java)
    private val application = app
    private val resources = application.resources

    suspend fun getCoinPrice(symbol:String): ResponseMessage{
       return exchangeService.getCoinPrice(symbol)

    }

    suspend fun placeMarketOrder(authHeader: String): ResponseMessage{
        return exchangeService.placeMarketOrder(authHeader)

    }

    suspend fun placeLimitOrder(authHeader:String): ResponseMessage{
        return exchangeService.placeLimitOrder(authHeader)
    }


    suspend fun placeTestOrder(limitOrder: limitOrder){

    }




}