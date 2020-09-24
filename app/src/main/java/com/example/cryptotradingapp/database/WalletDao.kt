package com.example.cryptotradingapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.cryptotradingapp.database.entities.WalletItemDB


@Dao
interface WalletDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrency(currency: WalletItemDB)

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllWalletItem(wallet: List<WalletItemDB>)

    @Delete
    suspend fun deleteCurrency(currency: WalletItemDB)

    @Update
    suspend fun updateCurrency(currency: WalletItemDB)

    @Query("SELECT * FROM wallet_table WHERE id = :key")
    suspend fun getCurrency(key: String) : WalletItemDB

    @Query("SELECT * FROM wallet_table ORDER BY id DESC")
    fun getAllWalletItems(): LiveData<List<WalletItemDB>>


}