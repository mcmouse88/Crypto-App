package com.mcmouse88.cryptoapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CoinInfoDbModel::class], version = 2, exportSchema = false)
abstract class CoinDatabase : RoomDatabase() {

    companion object {
        private var db: CoinDatabase? = null
        private const val NAME_DB = "database"
        private val LOCK = Any()

        fun getInstance(context: Context): CoinDatabase {
            synchronized(LOCK) {
                db?.let { return it }
                val instance = Room.databaseBuilder(
                    context,
                    CoinDatabase::class.java,
                    NAME_DB
                )
                    .fallbackToDestructiveMigration()
                    .build()
                db = instance
                return instance
            }
        }
    }

    abstract fun coinPriceInfoDao(): CoinsInfoDao
}