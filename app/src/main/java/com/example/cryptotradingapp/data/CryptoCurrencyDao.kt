package com.example.cryptotradingapp.data

import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface CryptoCurrencyDao {

    @Insert
    suspend fun insertCurrency(currency: CryptoCurrency)

    @Delete
    suspend fun deleteCurrency(currency: CryptoCurrency)

    @Update
    suspend fun updateCurrency(currency: CryptoCurrency)

    @Query("SELECT * from wallet_table WHERE id = :key")
    suspend fun getCurrency(key: CryptoCurrency) : CryptoCurrency

    @Query("SELECT * FROM wallet_table")
    fun getAllCurrencies(): LiveData<List<Note>>

}