package com.example.cryptotradingapp.viewmodels

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.*
import com.example.cryptotradingapp.R
import com.example.cryptotradingapp.domain.Cryptocurrency
import com.example.cryptotradingapp.domain.LimitOrder
import com.example.cryptotradingapp.domain.MarketOrder
import com.example.cryptotradingapp.network.ExchangeService
import com.example.cryptotradingapp.network.ResponseMessage
import com.example.cryptotradingapp.network.RetrofitInstance
import com.example.cryptotradingapp.repository.ExchangeRepository
import com.example.cryptotradingapp.repository.UserRepository
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONException
import org.json.JSONObject
import java.math.BigDecimal
import kotlin.properties.Delegates

class TradingViewModel(app: Application): AndroidViewModel(app) {

    private var userRepository: UserRepository = UserRepository(app)
    private var exchangeRepository: ExchangeRepository = ExchangeRepository(app)
    private var isUserLoggedIn = userRepository.isUserLoggedIn()
    private val resources = app.resources
    var selectedTradingPair:String = resources.getStringArray(R.array.crytocurrencies)[0]

    private val _coinPrice = MutableLiveData<Double>()
    val coinPrice: LiveData<Double>
        get() = _coinPrice

    var orderType = resources.getStringArray(R.array.order_types)[0]

    private val _isMarketOrder = MutableLiveData<Boolean>()
    val isMarketOrder: LiveData<Boolean>
        get() = _isMarketOrder

    var coinQuantity:Double = 0.0

    private val _isSell = MutableLiveData<Boolean>(false)

    val isSell: LiveData<Boolean>
        get() = _isSell

    val orderValue =  Transformations.map(coinPrice){       //TODO won't update if price doesn't change
                            price -> (price * coinQuantity)
                        }

    private val _userCoinBalance = MutableLiveData<Double>(0.0)
    val userCoinBalance: LiveData<Double>
    get() = _userCoinBalance


    init{
        //setCoinPriceEditabale()
        setCurrentCoinPrice()
        if(isUserLoggedIn){

            //get balance depending on if buy or sell selected
            //Initially, get price and everything from spinner, later check bundle and get coin price from that
            //check
        }

        //connect to socket of

    }


    //retrieve coin price and sets it editability depending on order type
    fun setCoinData(){
        Log.i("spinners",selectedTradingPair )

        /*
        val exchangeService = RetrofitInstance.getRetrofitInstance().create(ExchangeService::class.java)
        viewModelScope.launch{
            Log.i("test", "placeTestOrder")
            val order:limitOrder = limitOrder(true, "btc")
            exchangeService.placeTestOrder(userRepository.getUserAuthHeader(),order)
        }*/
    }

    //todo when adding new order types this method won't work
    fun setCoinPriceEditable(){
        if(isMarketOrderType()){
            _isMarketOrder.value = true
            setCurrentCoinPrice()
        } else{
            _isMarketOrder.value = false
            Log.i("tradingview", "Now marketorder is false and ordertype is" + orderType + "market order is:" +isMarketOrder )

        }
    }

    fun isMarketOrderType():Boolean{
        Log.i("tradingview", "isMarketOrder()" + orderType + "and resource" + resources.getStringArray(R.array.order_types)[0])
        if (orderType == resources.getStringArray(R.array.order_types)[0]){
            return true
        } else{
            return false
        }
    }


    fun setCurrentCoinPrice(){
        viewModelScope.launch{
            val symbol = selectedTradingPair.split("/")[0]
            Log.i("trading", symbol)
            val responseMessage:ResponseMessage = exchangeRepository.getCoinPrice(symbol)
            if(responseMessage.isSuccessful) {
                Log.i("trading", responseMessage.message)
                _coinPrice.value = responseMessage.message.toDouble()   //TODO MIGHT CAUSE ISSUES
            }
        }

    }


    fun setCoinOrderPrice(userPrice:String){
        val price = userPrice.toDouble()
        if(price != _coinPrice.value){
            _coinPrice.value = price
        }
    }

    fun isSellSwitchAction(isChecked:Boolean){
        _isSell.value = isChecked
    }

    fun updateViewBalance() {
        var symbol = resources.getString(R.string.BTC)
        if(isSell.value!!){
            symbol = getSymbol(selectedTradingPair)
        }
        var userBalance = 0.0
        runBlocking {
            val job = viewModelScope.launch(Dispatchers.IO) {
                userBalance = userRepository.getUserCoinBalance(symbol)
            }
            job.join()
            _userCoinBalance.value = userBalance
        }



    }

    fun getSymbol(tradingPair:String): String{
        val c = tradingPair.split("/")[0]
        return c
    }

    fun executeOrder(){
        if (checkValidEntries()&& checkEnoughFunds()){
            validOrderForExecution()

        } else{
            //not valid fields
        }

        val price = coinPrice.value
        val isMarketOrder = _isMarketOrder.value
        val isSell = _isSell.value
        val orderQuantity = coinQuantity
        val ordervalue= orderValue.value
        Log.i("tradingvi","coin price: " + price + " ismarketOrder: " + isMarketOrder + " orderQuantity: " + orderQuantity + " isSELL: " + isSell)
        Log.i("tradingtra", "did it work" + ordervalue)
    }

    fun checkValidEntries():Boolean{
        return (coinPrice.value != null) && coinQuantity.toDouble() > 0.0
    }


    fun checkEnoughFunds():Boolean{
        return  true //_userCoinBalance.value!! >= orderValue.value!!
    }

    fun validOrderForExecution(){
        val isBuy = !_isSell.value!!
        val orderQuantity = coinQuantity
        val ordervalue = orderValue.value
        val coinPrice = coinPrice.value!!
        val orderSymbol = getSymbol(selectedTradingPair)
        val authHeader = userRepository.getUserAuthHeader()
        viewModelScope.launch{
            if(_isMarketOrder.value!!){         //TODO maybe make it say  
                val order = MarketOrder(isBuy, orderSymbol,orderQuantity )
                val response = exchangeRepository.placeMarketOrder(authHeader,order)
                val message = response.message
                toast(message)
            } else{ //is limit order
                val order = LimitOrder(isBuy,orderSymbol,orderQuantity,coinPrice)
                val response = exchangeRepository.placeLimitOrder(authHeader,order)
                val message = response.message
                toast(message)
            }
        }
    }

    private fun toast(text: String?) {
        val handler = Handler(Looper.getMainLooper())
        handler.post { Toast.makeText(getApplication(), text, Toast.LENGTH_LONG).show() }
    }





}
