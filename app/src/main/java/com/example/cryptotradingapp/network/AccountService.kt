package com.example.cryptotradingapp.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AccountService {

//here are all the get methods


    //get all order history
    suspend fun getAllExecutedTrades()
    //get all current orders

    suspend fun getAllOpenTrades()
    //get wallet.
    @GET("/user/wallet")
    suspend fun getWallet(): Response<Wallet>
    //give it authentication with session wherever it is called from

    @POST("/user/signup")
    suspend fun postSignUpCredentials(@Header("Authorization") authHeader:String) : ResponseMessage

    @POST("/user/login")
    suspend fun postLoginCredentials(@Header("Authorization") authHeader:String) : ResponseMessage
}