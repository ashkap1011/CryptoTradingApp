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
    //todo instantiate the userrepo
    private val userRepository: UserRepository = UserRepository(app)

    private val userService = RetrofitInstance.getRetrofitInstance().create(UserService::class.java)
    private val resources = getApplication<Application>().resources


    fun postCredentials(): Boolean = runBlocking{
            var isSuccessful = false
            //TODO MAKE SURE USERNAME AND PASSWORD MEETS INPUT REQUIREMENTS
            val job = viewModelScope.async(Dispatchers.IO) {
                isSuccessful = getPostCredentialsResponse()
                Log.i("LOGIN", "IO coroutine finished")
            }
        job.join()
        Log.i("LOGIN", "RETURNING GOOD BOOLEAN in postCredentials()")
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
                Log.i("LOGIN", "RETURNING GOOD BOOLEAN")
            } else{
                Log.i("login", "unsuccessful")
            }
            return isSuccessful

        }
        catch (e: Exception){
            Log.i("err", e.message)
        }
       Log.i("LOGIN", "RETURNING BAD BOOLEAN")
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

    private fun toast(text: String?) {
        val handler = Handler(Looper.getMainLooper())
        handler.post { Toast.makeText(getApplication(), text, Toast.LENGTH_LONG).show() }
    }
}
