package com.example.cryptotradingapp.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.DEFAULT_CONCURRENCY
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList


class MarketDataService {
    private var mSocket: Socket = IO.socket("http://10.0.2.2:3000")
    private val _marketData = MutableLiveData<List<MarketCoin>>()
    val marketData: LiveData<List<MarketCoin>>
        get() = _marketData
    val gson = GsonBuilder().create()


    //make connection to market data
    fun startConnection(){
        Log.i("marketData", "heyy")
        mSocket.connect()
        listenMarketData()
    }

    fun listenMarketData(){
        mSocket.on("marketdata", onNewMarketData())
    }

    private fun onNewMarketData(): Emitter.Listener{
        var marketData:List<MarketCoin> = ArrayList()
        val listener = Emitter.Listener { args ->
            CoroutineScope(Dispatchers.IO).launch{
                val data = args[0]
                try {

                    _marketData.postValue(gson.fromJson(data.toString(), Array<MarketCoin>::class.java).toList())

                } catch (e: JSONException) {

                    Log.i("marketdata", "")

                }

                Log.i("marketdata", "newevent")
                //Log.i("message", username + message)
                // add the message to view
                //addMessage(username, message)
            }
        }
        return listener
    }





//
//    private val onNewMessage = Emitter.Listener { args ->
//        viewModelScope.launch{
//            val data = args[0] as JSONObject
//            Log.i("message", data.toString())
//            try {
//
//            } catch (e: JSONException) {
//
//            }
//
//            Log.i("message", "heyry")
//
//            //Log.i("message", username + message)
//            // add the message to view
//            //addMessage(username, message)
//        }
//    }
//











    //eventually create method to start listening to certain data










}