package com.example.currencyconverter.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.currencyconverter.domain.CurrencyEntity

object CurrencyListDiffCallback: DiffUtil.ItemCallback<CurrencyEntity>() {
    override fun areItemsTheSame(oldItem: CurrencyEntity, newItem: CurrencyEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CurrencyEntity, newItem: CurrencyEntity): Boolean {
        return oldItem == newItem
    }
}