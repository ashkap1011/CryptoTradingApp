package com.example.cryptotradingapp.database

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface CryptoCurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrency(currency: CryptoCurrency)

    @Delete
    suspend fun deleteCurrency(currency: CryptoCurrency)

    @Update
    suspend fun updateCurrency(currency: CryptoCurrency)

    @Query("SELECT * FROM wallet_table WHERE id = :key")
    suspend fun getCurrency(key: Long) : CryptoCurrency

    @Query("SELECT * FROM wallet_table ORDER BY id DESC")
    fun getAllCurrencies(): LiveData<List<CryptoCurrency>>

}