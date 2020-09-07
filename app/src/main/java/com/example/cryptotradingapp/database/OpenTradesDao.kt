package com.example.cryptotradingapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface OpenTradesDao {

    @Insert
    suspend fun insertOpenTrade(trade: OpenTrade)

    @Delete
    suspend fun deleteOpenTrade(trade: OpenTrade)

    @Query ("SELECT * from open_trades_table WHERE id = :key")
    suspend fun getOpenTrade(key:Long):OpenTrade

    @Query("SELECT * FROM open_trades_table ORDER BY id ASC")
    fun getAllOpenTrades(): LiveData<List<OpenTrade>>

}

