package com.example.cryptotradingapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.cryptotradingapp.database.entities.OpenTradeDB
import com.example.cryptotradingapp.database.entities.WalletItemDB

@Dao
interface OpenTradesDao {

    @Insert
    suspend fun insertOpenTrade(trade: OpenTradeDB)

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllOpenTradeItems(openTrades: List<OpenTradeDB>)


    @Delete
    suspend fun deleteOpenTrade(trade: OpenTradeDB)

    @Query ("SELECT * from open_trades_table WHERE id = :key")
    suspend fun getOpenTrade(key:String): OpenTradeDB

    @Query("SELECT * FROM open_trades_table ORDER BY id ASC")
    fun getAllOpenTrades(): LiveData<List<OpenTradeDB>>

}

