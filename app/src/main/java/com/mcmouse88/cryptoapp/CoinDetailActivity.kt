package com.mcmouse88.cryptoapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mcmouse88.cryptoapp.databinding.ActivityCoinDetailBinding
import com.mcmouse88.cryptoapp.model.CoinViewModel
import com.squareup.picasso.Picasso

class CoinDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoinDetailBinding
    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (!intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            finish()
            return
        }

        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL)
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        fromSymbol?.let {
            viewModel.getDetailInfo(it).observe(this, Observer {
                binding.apply {
                    textViewFullPrice.text = it.price
                    textViewByMinDay.text = it.lowDay
                    textViewByMaxDay.text = it.highDay
                    textViewLastDeal.text = it.lastMarket
                    textViewFullUpdate.text = it.getFormattedTime()
                    textViewFullCoin.text = it.fromSymbol
                    textViewFullCurrency.text = it.toSymbol
                    Picasso.get().load(it.getFullUrlImage()).into(fullImageView)
                }
            })
        }
    }

    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"

        fun newIntent(context: Context, fromSymbol: String): Intent {
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.putExtra(EXTRA_FROM_SYMBOL, fromSymbol)
            return intent
        }
    }
}