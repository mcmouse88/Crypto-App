package com.mcmouse88.cryptoapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mcmouse88.cryptoapp.R
import com.mcmouse88.cryptoapp.databinding.ActivityCoinDetailBinding

class CoinDetailActivity : AppCompatActivity() {

    private var _binding: ActivityCoinDetailBinding? = null
    private val binding: ActivityCoinDetailBinding
        get() = requireNotNull(_binding) { "ActivityCoinDetailBinding is null" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCoinDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            finish()
            return
        }

        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL) ?: EMPTY_SYMBOL
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_detail_container, CoinDetailFragment.newInstance(fromSymbol))
                .commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"
        private const val EMPTY_SYMBOL = ""

        fun newIntent(context: Context, fromSymbol: String): Intent {
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.putExtra(EXTRA_FROM_SYMBOL, fromSymbol)
            return intent
        }
    }
}