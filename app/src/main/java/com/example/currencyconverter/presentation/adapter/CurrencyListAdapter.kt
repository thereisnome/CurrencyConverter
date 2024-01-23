package com.example.currencyconverter.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.currencyconverter.databinding.CurrencyItemBinding
import com.example.currencyconverter.domain.CurrencyEntity
import com.example.currencyconverter.utils.formatCurrency

class CurrencyListAdapter: ListAdapter<CurrencyEntity, CurrencyListViewHolder>(
    CurrencyListDiffCallback
) {

    var onCurrencyClickListener: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyListViewHolder {
        val binding =
            CurrencyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyListViewHolder, position: Int) {
        val currency = getItem(position)

        with(holder.binding){
            currencyCharCode.text = currency.charCode
            currencyValue.text = formatCurrency(currency.value)
            currencyLayout.setOnClickListener {
                onCurrencyClickListener?.invoke(currency.id)
            }
        }
    }

//    private fun formatCurrency(value: Double): String {
//        val formattedAmount = NumberFormat.getCurrencyInstance(Locale("ru", "RU")).format(value)
//        return formattedAmount.replace(" руб.", "₽")
//    }
}