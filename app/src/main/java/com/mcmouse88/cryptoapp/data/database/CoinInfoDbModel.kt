package com.mcmouse88.cryptoapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Аннотация [Entity] указывается для работы
 * с базой данных, аннотация [PrimaryKey]
 * указывается над свойством, которое будет
 * первичным ключом.
 */
@Entity(tableName = "full_price_list")
data class CoinInfoDbModel(
    @PrimaryKey
    val fromSymbol: String,
    val price: String?,
    val lowDay: String?,
    val highDay: String?,
    val lastMarket: String?,
    val lastUpdate: Long?,
    val toSymbol: String?,
    val imageUrl: String
)