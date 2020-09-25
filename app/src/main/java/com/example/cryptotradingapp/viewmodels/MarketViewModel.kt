package com.example.cryptotradingapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.cryptotradingapp.network.ExchangeService
import com.example.cryptotradingapp.network.RetrofitInstance
import com.example.cryptotradingapp.network.UserService
import com.example.cryptotradingapp.repository.UserRepository

import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject

class MarketViewModel(app:Application): AndroidViewModel(app) {

    private var mSocket: Socket = IO.socket("http://10.0.2.2:3000")
    private var userRepository: UserRepository = UserRepository(app)


    private var isUserLoggedIn = userRepository.isUserLoggedIn()
/*
    private val _coinValue = MutableLiveData("Ada")
    private val _lastName = MutableLiveData("Lovelace")
    private val _likes =  MutableLiveData(0)

    val coinValue: LiveData<String> = _coinValue
    val lastName: LiveData<String> = _lastName
    val likes: LiveData<Int> = _likes
*/
    //todo use.value to set live data value

    init{
        setCoinData()
        if(isUserLoggedIn){
            //get balance depending on if buy or sell selected
            //Initially, get price and everything from spinner, later check bundle and get coin price from that
            //check
        }


        //connect to socket of

    }

    //retrieve coin price
    fun setCoinData(){
        val exchangeService = RetrofitInstance.getRetrofitInstance().create(ExchangeService::class.java)
        viewModelScope.launch{
            Log.i("test", "placeTestOrder")
            val order:limitOrder = limitOrder(true, "btc")
            exchangeService.placeTestOrder(userRepository.getUserAuthHeader(),order)
        }
    }



    fun startConnection(){
        Log.i("message", "heyy")
        mSocket.connect()
        mSocket.on("someevent", onNewMessage);
    }



    private val onNewMessage = Emitter.Listener { args ->
        viewModelScope.launch{
            val data = args[0] as JSONObject
            Log.i("message", data.toString())
            try {

            } catch (e: JSONException) {

            }

            Log.i("message", "heyry")

            //Log.i("message", username + message)
            // add the message to view
            //addMessage(username, message)
        }
    }
}

data class limitOrder(val isBuy:Boolean, val symbol: String)