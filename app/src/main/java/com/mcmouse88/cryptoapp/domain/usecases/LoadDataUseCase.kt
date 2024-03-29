package com.mcmouse88.cryptoapp.domain.usecases

import com.mcmouse88.cryptoapp.domain.CoinRepository

class LoadDataUseCase(
    private val repository: CoinRepository
) {

    operator fun invoke() {
        repository.loadData()
    }
}