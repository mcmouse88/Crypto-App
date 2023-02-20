package com.mcmouse88.cryptoapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mcmouse88.cryptoapp.data.network.model.CoinInfoDto

/**
 * Интерфейс для работы с базой данных
 * [Query] (см. бибилиотека [androidx.room.Query], для работы с сетью юиюилиотека
 * [retrofit2.http.Query]) в данном случае запрос в базу данных, в скобках
 * указывавется команда запроса ([lastUpdate] одно из полей в классе [CoinInfoDto].
 * Аннотация [Insert] значит добавить в базу данных новые значения,
 * параметр [OnConflictStrategy.REPLACE] означает, что новые данные будут записаны поверх
 * старых.
 */

@Dao
interface CoinsInfoDao {

    @Query("SELECT * FROM full_price_list ORDER BY lastUpdate DESC")
    fun getPriceList(): LiveData<List<CoinInfoDbModel>>

    @Query("SELECT * FROM full_price_list WHERE fromSymbol == :fSym LIMIT 1")
    fun getPriceInfoAboutCoin(fSym: String): LiveData<CoinInfoDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPriceList(priceList: List<CoinInfoDbModel>)
}