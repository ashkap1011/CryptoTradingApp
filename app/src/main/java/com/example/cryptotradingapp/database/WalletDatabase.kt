package com.example.cryptotradingapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cryptotradingapp.database.entities.WalletItemDB

@Database(entities = [WalletItemDB::class], version = 1, exportSchema = false)
abstract class WalletDatabase : RoomDatabase(){
    abstract val walletDao: WalletDao


    companion object {
        @Volatile
        private var INSTANCE: WalletDatabase? = null

        fun getInstanceWalletDB(context: Context):WalletDatabase{
            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(
                                                    context.applicationContext,
                                                    WalletDatabase::class.java,
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