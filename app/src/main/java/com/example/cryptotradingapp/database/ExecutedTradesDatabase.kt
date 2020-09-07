package com.example.cryptotradingapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ExecutedTrade::class], version = 1, exportSchema = false)
abstract class ExecutedTradesDatabase :RoomDatabase(){
    abstract val executedTradesDao: ExecutedTradesDao

    companion object {
        @Volatile
        private var INSTANCE: ExecutedTradesDatabase? = null

        fun getInstance(context: Context):ExecutedTradesDatabase{
            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ExecutedTradesDatabase::class.java,
                        "wallet_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}