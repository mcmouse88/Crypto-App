package com.mcmouse88.cryptoapp.domain.usecases

import androidx.lifecycle.LiveData
import com.mcmouse88.cryptoapp.domain.CoinInfo
import com.mcmouse88.cryptoapp.domain.CoinRepository

class GetCoinInfoUseCase(
    private val repository: CoinRepository
) {

    operator fun invoke(fromSymbol: String): LiveData<CoinInfo> = repository.getCoinInfo(fromSymbol)
}