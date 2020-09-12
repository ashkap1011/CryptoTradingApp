package com.example.cryptotradingapp.viewmodels

import android.app.Application
import android.content.Context
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.SwitchCompat
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptotradingapp.R
import com.example.cryptotradingapp.network.AccountService
import com.example.cryptotradingapp.network.ResponseMessage
import com.example.cryptotradingapp.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async


class LoginViewModel (app: Application) : AndroidViewModel(app) {
    lateinit var username : String
    lateinit var password : String
    var isSignIn =false
    private lateinit var saveButton : Button
    private val accountService = RetrofitInstance.getRetrofitInstance().create(AccountService::class.java)
    private val resources = getApplication<Application>().resources



    fun postCredentials(): Boolean{


            var isSuccessful = false

            viewModelScope.async(Dispatchers.IO) {
                isSuccessful = getPostCredentialsResponse()
            }
                return isSuccessful
            //Toast.makeText(activity, response.message, Toast.LENGTH_SHORT).show()

        }

   suspend fun getPostCredentialsResponse():Boolean {
        val credentialsString = username + ":" + password
        val authHeader = "Basic " + Base64.encodeToString(
            credentialsString.toByteArray(),
            Base64.NO_WRAP
        )

        var sp = getApplication<Application>().getSharedPreferences("Login", Context.MODE_PRIVATE)
        var spEditor = sp.edit()
        var isSuccessful = false
        var response: ResponseMessage
        try{
            response = if(isSignIn){
                accountService.postLoginCredentials(authHeader)
            }else{
                accountService.postSignUpCredentials(authHeader)
            }
            if(response.isSuccessful){
                Log.i("login", "successful")
                spEditor.putBoolean(resources.getString(R.string.prefKeyLogin), true)
                spEditor.putString(resources.getString(R.string.prefKeyUsername), username)
                spEditor.putString(resources.getString(R.string.prefKeyPassword), password)
                spEditor.commit()
                isSuccessful = true
            } else{
                Log.i("login", "unsuccessful")
            }
        }
        catch (e: Exception){
            Log.i("err", e.message)
        }
        return isSuccessful
    }

    fun afterUsernameChange(s: CharSequence) {
        //Log.i("truc", s.toString());
        this.username = s.toString()
    }

    fun afterPasswordChange(s: CharSequence) {
        //Log.i("truc", s.toString());
        this.password = s.toString()
    }


}
