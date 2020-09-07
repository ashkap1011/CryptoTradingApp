package com.example.cryptotradingapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExecutedTradesDao {

    @Insert
    suspend fun insertExecutedTrade(trade: ExecutedTrade)

    @Delete
    suspend fun deleteExecutedTrade(trade: ExecutedTrade)

    @Query("SELECT * FROM executed_trades_table WHERE id = :key")
    suspend fun getExecutedTrade(key:Long):ExecutedTrade

    @Query("SELECT * FROM executed_trades_table  ORDER BY id DESC")
    fun getAllExecutedTrades(): LiveData<List<ExecutedTrade>>
}