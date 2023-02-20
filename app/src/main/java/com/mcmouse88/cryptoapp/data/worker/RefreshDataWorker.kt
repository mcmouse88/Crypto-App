package com.mcmouse88.cryptoapp.data.worker

import android.content.Context
import androidx.work.*
import com.mcmouse88.cryptoapp.data.database.CoinDatabase
import com.mcmouse88.cryptoapp.data.mapper.CoinMapper
import com.mcmouse88.cryptoapp.data.network.ApiFactory
import kotlinx.coroutines.delay

class RefreshDataWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    private val coinInfoDao = CoinDatabase.getInstance(context).coinPriceInfoDao()
    private val apiService = ApiFactory.apiService
    private val mapper = CoinMapper()

    override suspend fun doWork(): Result {
        while (true) {
            try {
                val topCoins = apiService.getTopCoinsInfo(limit = 50)
                val fromSymbols = mapper.mapNamesListToString(topCoins)
                val jsonContainer = apiService.getFullPriceList(fSyms = fromSymbols)
                val coinInfoDtoList = mapper.mapJsonContainerToListCoinInfo(jsonContainer)
                val dbModelList = coinInfoDtoList.map(mapper::mapDtoToDbModel)
                coinInfoDao.insertPriceList(dbModelList)
            } catch (e: Exception) { /* no-op */ }
            delay(10_000)
        }
    }

    companion object {
        const val NAME = "RefreshDataWorker"

        private val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        fun makeRequest(): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<RefreshDataWorker>()
                .setConstraints(constraints)
                .build()
        }
    }
}
