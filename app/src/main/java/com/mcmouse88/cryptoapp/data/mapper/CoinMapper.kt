package com.mcmouse88.cryptoapp.data.mapper

import com.google.gson.Gson
import com.mcmouse88.cryptoapp.data.database.CoinInfoDbModel
import com.mcmouse88.cryptoapp.data.network.model.CoinInfoDto
import com.mcmouse88.cryptoapp.data.network.model.CoinInfoJsonContainerDto
import com.mcmouse88.cryptoapp.data.network.model.CoinNamesListDto
import com.mcmouse88.cryptoapp.domain.CoinInfo
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class CoinMapper {

    fun mapDtoToDbModel(dto: CoinInfoDto): CoinInfoDbModel = CoinInfoDbModel(
        fromSymbol = dto.fromSymbol,
        price = dto.price,
        lowDay = dto.lowDay,
        highDay = dto.highDay,
        lastMarket = dto.lastMarket,
        lastUpdate = dto.lastUpdate,
        toSymbol = dto.toSymbol,
        imageUrl = BASE_IMAGE_URL + dto.imageUrl
    )

    fun mapJsonContainerToListCoinInfo(jsonContainer: CoinInfoJsonContainerDto): List<CoinInfoDto> {
        val result = mutableListOf<CoinInfoDto>()
        val json = jsonContainer.json ?: return result
        val coinKeySet = json.keySet()
        for (key in coinKeySet) {
            val currencyJson = json.getAsJsonObject(key)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinInfoDto::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    fun mapNamesListToString(namesListDto: CoinNamesListDto): String {
        return namesListDto.names?.map {
            it.coinName?.name
        }?.joinToString(",") ?: ""
    }

    fun mapDbModelToEntity(dbModel: CoinInfoDbModel): CoinInfo = CoinInfo(
        fromSymbol = dbModel.fromSymbol,
        price = dbModel.price,
        lowDay = dbModel.lowDay,
        highDay = dbModel.highDay,
        lastMarket = dbModel.lastMarket,
        lastUpdate = dbModel.lastUpdate.convertTimestampToTime(),
        toSymbol = dbModel.toSymbol,
        imageUrl = dbModel.imageUrl
    )

    private fun Long?.convertTimestampToTime(): String {
        if (this == null) return ""
        val stamp = Timestamp(this * 1000)
        val date = Date(stamp.time)
        val pattern = "HH:mm:ss"
        val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        dateFormat.timeZone = TimeZone.getDefault()
        return dateFormat.format(date)
    }

    companion object {
        const val BASE_IMAGE_URL = "https://cryptocompare.com/"
    }
}