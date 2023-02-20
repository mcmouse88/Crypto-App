package com.mcmouse88.cryptoapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.mcmouse88.cryptoapp.data.repository.CoinRepositoryImpl
import com.mcmouse88.cryptoapp.domain.CoinInfo
import com.mcmouse88.cryptoapp.domain.CoinRepository
import com.mcmouse88.cryptoapp.domain.usecases.GetCoinInfoListUseCase
import com.mcmouse88.cryptoapp.domain.usecases.GetCoinInfoUseCase
import com.mcmouse88.cryptoapp.domain.usecases.LoadDataUseCase
import kotlinx.coroutines.launch

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CoinRepository = CoinRepositoryImpl(application)

    private val getCoinInfoListUseCase = GetCoinInfoListUseCase(repository)
    private val getCoinInfoUseCase = GetCoinInfoUseCase(repository)
    private val loadDataUseCase = LoadDataUseCase(repository)

    val coinInfoList = getCoinInfoListUseCase()

    fun getDetailInfo(fromSymbol: String): LiveData<CoinInfo> {
        return getCoinInfoUseCase(fromSymbol)
    }

    init {
        viewModelScope.launch {
            loadDataUseCase()
        }
    }
}