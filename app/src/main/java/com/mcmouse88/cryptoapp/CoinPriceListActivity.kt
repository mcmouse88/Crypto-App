package com.mcmouse88.cryptoapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mcmouse88.cryptoapp.adapters.CoinInfoAdapter
import com.mcmouse88.cryptoapp.databinding.ActivityCoinPriceListBinding
import com.mcmouse88.cryptoapp.model.CoinViewModel
import com.mcmouse88.cryptoapp.pojo.PriceInfo


/**
 * Хозяйке на заметку, чтобы автоматически убрать не
 * используемые импорты, нужно нажать сочетание клавиш
 * [ctrl] + [alt] + [o]
 */
class CoinPriceListActivity : AppCompatActivity() {

    /**
     * Создадим объект [ViewModel], с отложенной инициализацией,
     * в последствии инициализируем его в методе [onCreate]
     */
    private lateinit var viewModel: CoinViewModel
    private lateinit var binding: ActivityCoinPriceListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinPriceListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = CoinInfoAdapter(this)
        binding.recyclerViewCoinPriceList.adapter = adapter

        adapter.onCoinClick = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(priceInfo: PriceInfo) {
                val intent = CoinDetailActivity.newIntent(this@CoinPriceListActivity, priceInfo.fromSymbol)
                startActivity(intent)
            }

        }

        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]

        viewModel.priceList.observe(this, Observer {
            adapter.coinInfoList = it
        })
    }
}