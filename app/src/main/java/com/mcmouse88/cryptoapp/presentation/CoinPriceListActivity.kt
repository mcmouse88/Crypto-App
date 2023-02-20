package com.mcmouse88.cryptoapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mcmouse88.cryptoapp.R
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
            override fun onCoinClick(coin: CoinInfo) {
                if (isOnePaneMode()) {
                    launchDetailActivity(coin.fromSymbol)
                } else {
                    launchDetailFragment(coin.fromSymbol)
                }
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

    private fun isOnePaneMode(): Boolean = binding.fragmentEndContainer == null

    private fun launchDetailActivity(fromSymbol: String) {
        val intent = CoinDetailActivity.newIntent(
            this@CoinPriceListActivity,
            fromSymbol
        )
        startActivity(intent)
    }

    private fun launchDetailFragment(fromSymbol: String) {
        supportFragmentManager.popBackStack()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_end_container, CoinDetailFragment.newInstance(fromSymbol))
            .addToBackStack(null)
            .commit()
    }
}