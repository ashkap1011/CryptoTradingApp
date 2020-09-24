package com.example.cryptotradingapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptotradingapp.repository.UserRepository
import kotlinx.coroutines.launch
import java.io.IOException

class AccountViewModel(app: Application) : AndroidViewModel(app) {
    private val userRepository: UserRepository = UserRepository(app)
    val wallet = userRepository.wallet



    init {
        refreshDataFromRepository()
    }

    /**
     * Refresh data from the repository. Use a coroutine launch to run in a
     * background thread.
     */
    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                userRepository.retrieveUserData()

               // _eventNetworkError.value = false
             //   _isNetworkErrorShown.value = false

            } catch (networkError: IOException) {
                // Show a Toast error message and hide the progress bar.
                /*
                if(playlist.value.isNullOrEmpty())
                    _eventNetworkError.value = true */
            }
        }
    }

}