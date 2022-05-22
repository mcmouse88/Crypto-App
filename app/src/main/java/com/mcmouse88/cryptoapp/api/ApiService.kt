package com.mcmouse88.cryptoapp.api

import com.mcmouse88.cryptoapp.pojo.CoinPriceInfoData
import com.mcmouse88.cryptoapp.pojo.ListOfDatum
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    /**
     * Получить список самых популярных валют через API
     * @see <a href="https://min-api.cryptocompare.com/data/top/totalvolfull?limit=10&tsym=USD">
     * https://min-api.cryptocompare.com/data/top/totalvolfull?limit=10&tsym=USD</a>,
     * где имеются следующие параметры - limit количество отображаемых валют в списке,
     * и tsym название валюты в которую будет конвертироваться курс,
     * для этого у фуннкции указываем аннотацию [GET],
     * и в скобках указываем endpoint API - top/totalvolfull, до самих параметров,
     * в параметр apiKey передаем ключ полученный на сайте, параметры инициализируем, чтобы
     * при вызове метода их не передавать
     */

    @GET ("top/totalvolfull")
    fun getTopCoinsInfo(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = KEY_FOR_ACCESS,
        @Query(QUERY_PARAM_LIMIT) limit: Int = 10, // Если указать другое то выведет такое количество валют
        @Query(QUERY_PARAM_TO_SYMBOL) tSym: String = CURRENCY,
    ): Single<ListOfDatum>


    /**
     * Данный метод будет загружать полную информацию о криптовалюте через API
     * @see <a href="https://min-api.cryptocompare.com/data/pricemultifull?fsyms=BTC&tsyms=USD">
     *     https://min-api.cryptocompare.com/data/pricemultifull?fsyms=BTC&tsyms=USD</a>
     *  В котором параметры будут fsyms (from_symbols) и tsyms (to_symbols).
     * @param fsyms из какой валюты биткоинов (список через запятуюю) конвертировать, в данный параметр мы будем получать
     * список популярных валют через метод [getTopCoinsInfo], поэтому он будет обязательныи параметром
     * @param tsyms в какую валюту (список валют через запятую) мы будем использовать USD.
     * <h> В качестве endpoint запросу [GET] передадим [pricemultifull]</h>
     */
    @GET("pricemultifull")
    fun getFullPriceList(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = KEY_FOR_ACCESS,
        @Query(QUERY_PARAM_FROM_SYMBOLS) fSyms: String,
        @Query(QUERY_PARAM_TO_SYMBOLS) tSyms: String = CURRENCY,
    ): Single<CoinPriceInfoData>

    /**
     * В [companion object] создадим константы, которые будем передавать в качестве
     * параметров функции [getTopCoinsInfo()]
     */
    companion object {
        private const val QUERY_PARAM_API_KEY = "api_key"
        private const val QUERY_PARAM_LIMIT = "limit"
        private const val QUERY_PARAM_TO_SYMBOL = "tsym"
        private const val QUERY_PARAM_TO_SYMBOLS = "tsyms"
        private const val QUERY_PARAM_FROM_SYMBOLS = "fsyms"

        private const val CURRENCY = "USD"
        private const val KEY_FOR_ACCESS = "15353878f925b9b11cbdb1d8b3ba9bec14466f9aca71f3a129fc2fa8f263039b"
    }
}