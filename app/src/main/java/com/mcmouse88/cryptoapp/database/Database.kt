package com.mcmouse88.cryptoapp.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mcmouse88.cryptoapp.pojo.PriceInfo

/**
 * Создадим локальную базу данных, чтобы в случае отсутствия доступа
 * в интернет, загружались последние полученные данные.
 * База данных будет [Singleton], а не [object] так как
 * создавать ее будем не мы. База данных должна быть абстрактным классом и
 * наследоваться от [RoomDatabase]. Метод возвращения объекта ьазы данных
 * должен быть синхронизирован.
 */

@androidx.room.Database(entities = [PriceInfo::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {

    companion object {
        private var db: Database? = null
        private const val NAME_DB = "database"
        private val LOCK = Any()

        fun getInstance(context: Context): Database {
            synchronized(LOCK) {
                db?.let { return it }
                val instance = Room.databaseBuilder(
                    context,
                    Database::class.java,
                    NAME_DB
                ).build()
                db = instance
                return instance
            }
        }
    }

    abstract fun coinPriceInfoDao(): CoinPriceInfoDao
}
    /**
     * Хозяйке на заметку, если в методе в котором много параметров
     * нажать комбинацию клавиш [alt] + [enter], и выбрать put arguments on
     * separate lines, то все параметры автоматически выровняются по одному
     * на каждой строчке
     */