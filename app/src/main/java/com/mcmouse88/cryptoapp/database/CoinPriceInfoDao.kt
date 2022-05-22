package com.mcmouse88.cryptoapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mcmouse88.cryptoapp.pojo.PriceInfo

/**
 * Интерфейс для работы с базой данных
 * [Query] (см. бибилиотека [androidx.room.Query], для работы с сетью юиюилиотека
 * [retrofit2.http.Query]) в данном случае запрос в базу данных, в скобках
 * указывавется команда запроса ([lastUpdate] одно из полей в классе [PriceInfo].
 * Аннотация [Insert] значит добавить в базу данных новые значения,
 * параметр [OnConflictStrategy.REPLACE] означает, что новые данные будут записаны поверх
 * старых.
 */

@Dao
interface CoinPriceInfoDao {

    @Query("select * from full_price_list order by lastUpdate desc")
    fun getPriceList(): LiveData<List<PriceInfo>>

    @Query("select * from full_price_list where fromSymbol == :fSym limit 1")
    fun getPriceInfoAboutCoin(fSym: String): LiveData<PriceInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPriceList(priceList: List<PriceInfo>)
}