package com.mcmouse88.cryptoapp.pojo

import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinPriceInfoData(
    @SerializedName("RAW")
    @Expose
    val jsonObject: JsonObject? = null

) {
}