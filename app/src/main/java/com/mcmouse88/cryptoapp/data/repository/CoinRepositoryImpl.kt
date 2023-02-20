package com.mcmouse88.cryptoapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.mcmouse88.cryptoapp.data.database.CoinDatabase
import com.mcmouse88.cryptoapp.data.mapper.CoinMapper
import com.mcmouse88.cryptoapp.data.worker.RefreshDataWorker
import com.mcmouse88.cryptoapp.domain.CoinInfo
import com.mcmouse88.cryptoapp.domain.CoinRepository

class CoinRepositoryImpl(
    private val application: Application
) : CoinRepository {

    private val coinInfoDao = CoinDatabase.getInstance(application).coinPriceInfoDao()
    private val mapper = CoinMapper()

    override fun getCoinInfoList(): LiveData<List<CoinInfo>> {
        return Transformations.map(coinInfoDao.getPriceList()) { listDbModel ->
            listDbModel.map(mapper::mapDbModelToEntity)
        }
    }

    override fun getCoinInfo(fromSymbol: String): LiveData<CoinInfo> {
        return Transformations.map(coinInfoDao.getPriceInfoAboutCoin(fromSymbol)) { dbModel ->
            mapper.mapDbModelToEntity(dbModel)
        }
    }

    override fun loadData() {
        val workManager = WorkManager.getInstance(application)
        workManager.enqueueUniqueWork(
            RefreshDataWorker.NAME,
            ExistingWorkPolicy.REPLACE,
            RefreshDataWorker.makeRequest()
        )
    }
}