package com.example.cryptotradingapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [OpenTrade::class], version = 1, exportSchema = false)
abstract class OpenTradesDatabase :RoomDatabase(){
    abstract val openTradesDao: OpenTradesDao

    companion object {
        @Volatile
        private var INSTANCE: OpenTradesDatabase? = null

        fun getInstance(context: Context):OpenTradesDatabase{
            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        OpenTradesDatabase::class.java,
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