package com.example.cryptotradingapp.viewmodels

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptotradingapp.R
import com.example.cryptotradingapp.network.ResponseMessage
import com.example.cryptotradingapp.network.RetrofitInstance
import com.example.cryptotradingapp.network.UserService
import com.example.cryptotradingapp.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking


class LoginViewModel(app: Application) : AndroidViewModel(app) {
    lateinit var username : String
    lateinit var password : String
    var isSignIn =false //holds value of isSignin OR is Login Switch
    private lateinit var saveButton : Button
    private val userRepository: UserRepository = UserRepository(app)

    fun postCredentials(): Boolean = runBlocking{
            var isSuccessful = false
            //TODO make sure password and username meets user requirements
            val job = viewModelScope.async(Dispatchers.IO) {
                isSuccessful = getPostCredentialsResponse()
            }
        job.join()
        return@runBlocking isSuccessful
    }


   suspend fun getPostCredentialsResponse():Boolean {
        val credentialsString = username + ":" + password
        val authHeader = "Basic " + Base64.encodeToString(
            credentialsString.toByteArray(),
            Base64.NO_WRAP
        )

        var isSuccessful = false
        var response: ResponseMessage
        try{
            response = if(isSignIn){
                userRepository.verifyLoginCredentials(authHeader)    //todo maybe put this part in the repo
            }else{
                userRepository.postSignUpCredentials(authHeader)
            }

            toast(response.message)

            if(response.isSuccessful){
                isSuccessful = true
                userRepository.updateUserSession(username,password) //updates system to add user as logged in
                userRepository.retrieveUserData()                   //retrieves user data (from userService) after login
            } else{
            }
            return isSuccessful

        }
        catch (e: Exception){
            Log.i("err", e.message)
        }
       return isSuccessful
    }

    fun afterUsernameChange(s: CharSequence) {
        this.username = s.toString()
    }

    fun afterPasswordChange(s: CharSequence) {
        this.password = s.toString()
    }

    private fun toast(text: String?) {
        val handler = Handler(Looper.getMainLooper())
        handler.post { Toast.makeText(getApplication(), text, Toast.LENGTH_LONG).show() }
    }
}
