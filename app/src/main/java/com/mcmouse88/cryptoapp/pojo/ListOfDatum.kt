package com.mcmouse88.cryptoapp.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ListOfDatum(
    @SerializedName("Data")
    @Expose
    val data: List<Datum>? = null
) {
}