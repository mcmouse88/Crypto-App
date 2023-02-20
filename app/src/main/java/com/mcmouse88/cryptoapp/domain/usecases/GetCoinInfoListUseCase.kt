package com.mcmouse88.cryptoapp.domain.usecases

import androidx.lifecycle.LiveData
import com.mcmouse88.cryptoapp.domain.CoinInfo
import com.mcmouse88.cryptoapp.domain.CoinRepository

class GetCoinInfoListUseCase(
    private val repository: CoinRepository
) {

    operator fun invoke(): LiveData<List<CoinInfo>> = repository.getCoinInfoList()
}