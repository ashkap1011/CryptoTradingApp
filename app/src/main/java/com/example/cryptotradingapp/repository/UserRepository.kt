package com.example.cryptotradingapp.repository

import android.app.Application
import android.content.Context
import android.util.Base64
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.cryptotradingapp.R
import com.example.cryptotradingapp.database.ExecutedTradesDatabase.Companion.getInstanceExecTradesDB
import com.example.cryptotradingapp.database.OpenTradesDatabase.Companion.getInstanceOpenTradesDB
import com.example.cryptotradingapp.database.WalletDatabase.Companion.getInstanceWalletDB
import com.example.cryptotradingapp.database.entities.asDomainModel
import com.example.cryptotradingapp.database.entities.asDomainModels
import com.example.cryptotradingapp.domain.Cryptocurrency
import com.example.cryptotradingapp.fragments.AccountFragment
import com.example.cryptotradingapp.fragments.LoginFragment
import com.example.cryptotradingapp.network.ResponseMessage
import com.example.cryptotradingapp.network.RetrofitInstance
import com.example.cryptotradingapp.network.UserService
import com.example.cryptotradingapp.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*


/**
 * Repository for User Account
 * Repository for fetching data from network and caching onto disk
 * */
class UserRepository(app: Application) {    //TODO use depenedcy injection

    private val userService = RetrofitInstance.getRetrofitInstance().create(UserService::class.java)
    private  val walletDao = getInstanceWalletDB(app).walletDao
    private val executedTradesDao = getInstanceExecTradesDB(app).executedTradesDao
    private val openTradesDao = getInstanceOpenTradesDB(app).openTradesDao

    val wallet: LiveData<List<Cryptocurrency>> = Transformations.map(walletDao.getAllWalletItems()) {
        it.asDomainModels()
    }

//todo asdomainmodel seems a bit redundant
    suspend fun getUserCoinBalance(symbol: String): Double{
        val walletItem = walletDao.getCurrency(symbol)
        if (walletItem != null){
        return walletItem.asDomainModel().quantity
        }
        return 0.0
    }

    private val application = app
    private val resources = app.resources

    //login here
    suspend fun verifyLoginCredentials(authHeader: String):ResponseMessage{
       return userService.postLoginCredentials(authHeader)
        //if successful login then retrieve all user account info
    }

    suspend fun postSignUpCredentials(authHeader: String):ResponseMessage{
        return userService.postSignUpCredentials(authHeader)
    }

    fun updateUserSession(username: String, password: String){
        var sp = application.getSharedPreferences(
            resources.getString(R.string.user_session),
            Context.MODE_PRIVATE
        )
        var spEditor = sp.edit()

        spEditor.putBoolean(resources.getString(R.string.pref_key_login), true)
        spEditor.putString(resources.getString(R.string.pref_key_username), username)
        spEditor.putString(resources.getString(R.string.pref_key_password), password)
        spEditor.commit()
    }


    suspend fun retrieveUserData(){
        val authHeader = getUserAuthHeader()

        refreshWallet(authHeader)
        //send query to get wallet
        //then cache it in the db
        //now u cache all these values, then work on recycler view by then it will be 4
        //get wallet, open trades, get executed trades
    }

    fun isUserLoggedIn():Boolean{
        var isLoggedIn = false

        val sharedPref = application.getSharedPreferences(resources.getString(R.string.user_session),Context.MODE_PRIVATE)
        if (sharedPref.contains(resources.getString(R.string.pref_key_login))){
            isLoggedIn = sharedPref.getBoolean(resources.getString(R.string.pref_key_login),false)
        }
        Log.i("pref", isLoggedIn.toString())
        if(isLoggedIn) {
            return true
        }
        return false

        }

    /*
    suspend fun refreshVideos() {
        withContext(Dispatchers.IO) {
            Timber.d("refresh videos is called");
            val playlist = DevByteNetwork.devbytes.getPlaylist()
            database.videoDao.insertAll(playlist.asDatabaseModel())
        }
    }#

     */

    //todo then work on saving fetched data.

    //todo then work on integrating data in to view using recycler view fragment

    //fetch wallet from remote server
   suspend fun refreshWallet(authHeader: String){
       withContext(Dispatchers.IO) {
           val wallet = userService.getWallet(authHeader)
           Log.i("Wallet", "Luck, wallet retrieved")
           walletDao.insertAllWalletItem(wallet.asDatabaseModel())
           Log.i("Wallet", "Luck, databasemodel retrieved")
       }
   }

    fun getUserAuthHeader(): String{
        val sharedPref = application.getSharedPreferences(
            resources.getString(R.string.user_session),
            Context.MODE_PRIVATE
        )
        if (sharedPref.contains(resources.getString(R.string.pref_key_login)) && sharedPref.getBoolean(
                resources.getString(
                    R.string.pref_key_login
                ), false
            )){
            val username:String? = sharedPref.getString(
                resources.getString(R.string.pref_key_username),
                ""
            )
            val password: String? = sharedPref.getString(
                resources.getString(R.string.pref_key_password),
                ""
            )
            val credentialsString = username + ":" + password
            return "Basic " + Base64.encodeToString(
                credentialsString.toByteArray(),
                Base64.NO_WRAP
            )
        }
        return ""
    }



    /*
    *  suspend fun refreshWallet(){
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
    *
    * */





}