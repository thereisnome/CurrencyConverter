package com.example.currencyconverter.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import com.example.currencyconverter.R
import com.example.currencyconverter.databinding.CurrencyItemBinding
import com.example.currencyconverter.domain.CurrencyEntity
import com.example.currencyconverter.utils.formatCurrency
import com.example.currencyconverter.utils.formatDiff


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
            val value = currency.value/currency.nominal
            currencyValue.text = formatCurrency(value)
            val diff = currency.value - currency.previous
            currencyDiff.text = formatDiff(diff)
            currencyDiff.setTextColor(calculateColor(diff, holder))
            currencyLayout.setOnClickListener {
                onCurrencyClickListener?.invoke(currency.id)
            }
        }
    }

    private fun calculateColor(diff: Double, holder: CurrencyListViewHolder): Int{
        val typedArray = holder.binding.root.context.theme.obtainStyledAttributes(R.styleable.CurrencyDiffStyle)
        val posColor = typedArray.getColor(
            R.styleable.CurrencyDiffStyle_currencyDiffPos,
            ContextCompat.getColor(holder.binding.root.context, R.color.md_theme_light_currency_diff_pos))
        val negColor = typedArray.getColor(
            R.styleable.CurrencyDiffStyle_currencyDiffNeg,
            ContextCompat.getColor(holder.binding.root.context, R.color.md_theme_light_currency_diff_neg))
        return if (diff < 0){
            posColor
        } else negColor
    }
}