package com.mcmouse88.cryptoapp.presentation

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mcmouse88.cryptoapp.R
import com.mcmouse88.cryptoapp.databinding.FragmentCoinDetailBinding
import com.mcmouse88.cryptoapp.domain.CoinInfo
import com.squareup.picasso.Picasso

class CoinDetailFragment : Fragment(R.layout.fragment_coin_detail) {

    private var _binding: FragmentCoinDetailBinding? = null
    private val binding: FragmentCoinDetailBinding
        get() = requireNotNull(_binding) { "FragmentCoinDetailBinding is null" }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCoinDetailBinding.bind(view)

        val viewModel = ViewModelProvider(this)[CoinViewModel::class.java]

        val fromSymbol = getSymbol()
        viewModel.getDetailInfo(fromSymbol).observe(viewLifecycleOwner, ::updateUi)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUi(coin: CoinInfo) {
        binding.apply {
            textViewFullPrice.text = coin.price
            textViewByMinDay.text = coin.lowDay
            textViewByMaxDay.text = coin.highDay
            textViewLastDeal.text = coin.lastMarket
            textViewFullUpdate.text = coin.lastUpdate
            textViewFullCoin.text = coin.fromSymbol
            textViewFullCurrency.text = coin.toSymbol
            Picasso.get().load(coin.imageUrl).into(fullImageView)
        }
    }

    private fun getSymbol(): String {
        return requireArguments().getString(EXTRA_FROM_SYMBOL, EMPTY_SYMBOL)
    }

    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"
        private const val EMPTY_SYMBOL = ""

        fun newInstance(fromSymbol: String): Fragment {
            return CoinDetailFragment().apply {
                arguments = bundleOf(EXTRA_FROM_SYMBOL to fromSymbol)
            }
        }
    }
}