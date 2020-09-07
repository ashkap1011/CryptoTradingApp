package com.example.cryptotradingapp.repository

import android.util.Log
import com.example.cryptotradingapp.database.WalletDatabase
import com.example.cryptotradingapp.network.AccountService
import com.example.cryptotradingapp.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


/**
 * Repository for fetching data from network and caching onto disk
 * */
class WalletRepository(private val database: WalletDatabase) {
    private val accountService = RetrofitInstance.getRetrofitInstance().create(AccountService::class.java)

   suspend fun refreshWallet(){
       withContext(Dispatchers.IO) {
           val wallet = accountService.getWallet()
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