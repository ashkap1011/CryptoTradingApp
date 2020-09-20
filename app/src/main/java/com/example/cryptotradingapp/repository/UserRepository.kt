package com.example.cryptotradingapp.repository

import android.util.Log
import com.example.cryptotradingapp.database.WalletDatabase
import com.example.cryptotradingapp.network.UserService
import com.example.cryptotradingapp.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


/**
 * Repository for User Account
 * Repository for fetching data from network and caching onto disk
 * */
class UserRepository(private val walletDatabase: WalletDatabase) {
    private val userService = RetrofitInstance.getRetrofitInstance().create(UserService::class.java)




   suspend fun getWallet(){
       withContext(Dispatchers.IO) {
           val wallet = userService.getWallet()
           val currencyListWallet = wallet.body()?.listIterator()
           if(currencyListWallet != null) {
               while (currencyListWallet.hasNext()) {
                   val currency = currencyListWallet.next()
                    Log.i("MYMY",currency.symbol)
               }
           }
       }
   }



}