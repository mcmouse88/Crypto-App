package com.mcmouse88.cryptoapp.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.mcmouse88.cryptoapp.api.ApiFactory
import com.mcmouse88.cryptoapp.database.Database
import com.mcmouse88.cryptoapp.pojo.CoinPriceInfoData
import com.mcmouse88.cryptoapp.pojo.PriceInfo
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Создадим [ViewModel] для того, чтобы получать данные из
 * сети и отправлять их в базу. [ViewModel] будет наследоваться
 * от [AndroidViewModel], которой в качестве параметра необходимо передать
 * объект [Application] (для обычной [ViewModel] это не нужно)
 */
class CoinViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * Создадим объект базы данных, и передадим аргумент [application] в
     * качестве параметра [context]
     */
    private val db = Database.getInstance(application)

    /**
     * Создадим объект [CompositeDisposable], и задиспозим его в
     * методе [onCleared()]. [CompositeDisposable] добавляет и удаляет данные
     * (как то так, не знаю что еще сказать, в интернете все абстарктно написано)
     */
    private val compositeDisposable = CompositeDisposable()

    /**
     * Создадим объект [LiveData], на который будем подписываться.
     * Если подписаться на него в [Activity], то все изменения сразу будут
     * отображаться
     */
    val priceList = db.coinPriceInfoDao().getPriceList()

    /**
     * Загрузим данные автоматически при создании [ViewModel]
     */
    init {
        loadData()
    }

    /**
     * Метод, который будет загружать данные из сети, в переменную
     * [disposable] нам нужно получить названия валют(Биткоинов) через запятую (без пробелов),
     * собрать их в одну строчку, и отправить эту информацию в метод, который
     * будет загружать полную информацию о данных валютах, сделаем это через метод [map] с
     * проверкой на [null] через метод [let], затем вызываем метод [flatMap], который
     * возьмет полученную строку, и передаст ее в метод [getFullPriceList] в качестве
     * парметра [fSyms], и по этой строке мы загружаем полный price list
     */
    private fun loadData() {
        val disposable = ApiFactory.apiService.getTopCoinsInfo()
            .map {
                it.data?.let {
                    it.map { it.coinInfo?.name }
                        .joinToString(",") // на выходе получается список объектов типа CoinInfo
                }
            }
            .flatMap { ApiFactory.apiService.getFullPriceList(fSyms = it) }
            .map { getPriceListFromRawDate(it) }
            .delaySubscription(60, TimeUnit.SECONDS) // Задает интервал, через который будут обновляться данные
            .repeat() // Повторять загрузку, чтобы информация автоматически обновлялась на экране
            .retry() // Повторить загрузку после возникновения ошибки (например при отключении интернета)
            .subscribeOn(Schedulers.io()) // даем команду делать все в другом потоке
            .subscribe({
                db.coinPriceInfoDao().insertPriceList(it)
                // Log.d("MyLog", "Success: $it")
            }, {
                Log.d("MyLog", "Failure: ${it.message}")
            })
        compositeDisposable.add(disposable)
    }

    /**
     * На вход в метод в качестве аргумента приходит объект
     * [CoinPriceInfoData], который содержит [JsonObject],
     * который мы будем парсить вручную. В нем находится ключ
     * биткоина [BTC], [ETH] и т.д.(сокращенное название криптовалюты), далее
     * в этом ключе содержится еще один ключ [USD], в котором уже содержится
     * нужная нам информация (цена и т.д.). Чтобы распарсить, мы берем у [JsonObject]
     * все ключи, проходимся по ним через циклы, и получаем все вложенные [[JsonObject]]ю
     * Далее при помощи библиотеки [Gson()] (в частности статического метода [fromJson])
     * конвертируем полученные данные в объект [PriceInfo], после чего отправляем
     * объект в коллекцию
     */
    private fun getPriceListFromRawDate(coinPriceInfoData: CoinPriceInfoData): List<PriceInfo> {
        val listCoin = ArrayList<PriceInfo>()
        val json = coinPriceInfoData.jsonObject ?: return listCoin
        val coinKeySet = json.keySet()
        for (key in coinKeySet) {
            val currencyJson = json.getAsJsonObject(key)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    PriceInfo::class.java
                )
                listCoin.add(priceInfo)
            }
        }
        return listCoin
    }

    /**
     * Метод возвращает детальную информацию, об
     * отдельно взятой валюте
     */
    fun getDetailInfo(fSym: String): LiveData<PriceInfo> {
        return db.coinPriceInfoDao().getPriceInfoAboutCoin(fSym)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}