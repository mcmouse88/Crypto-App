package com.mcmouse88.cryptoapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mcmouse88.cryptoapp.databinding.ActivityCoinPriceListBinding
import com.mcmouse88.cryptoapp.domain.CoinInfo
import com.mcmouse88.cryptoapp.presentation.adapters.CoinInfoAdapter

class CoinPriceListActivity : AppCompatActivity() {

    private var _binding: ActivityCoinPriceListBinding? = null
    private val binding: ActivityCoinPriceListBinding
        get() = requireNotNull(_binding) { "ActivityCoinPriceListBinding is null" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCoinPriceListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = CoinInfoAdapter(this)
        adapter.onCoinClick = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(priceInfo: CoinInfo) {
                val intent =
                    CoinDetailActivity.newIntent(this@CoinPriceListActivity, priceInfo.fromSymbol)
                startActivity(intent)
            }

        }

        binding.recyclerViewCoinPriceList.adapter = adapter
        binding.recyclerViewCoinPriceList.itemAnimator = null

        val viewModel = ViewModelProvider(this)[CoinViewModel::class.java]

        viewModel.coinInfoList.observe(this, adapter::submitList)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}