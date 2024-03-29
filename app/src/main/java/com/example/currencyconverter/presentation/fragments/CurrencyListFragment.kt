package com.example.currencyconverter.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconverter.databinding.FragmentCurrencyListBinding
import com.example.currencyconverter.domain.CurrencyEntity
import com.example.currencyconverter.presentation.Error
import com.example.currencyconverter.presentation.Loading
import com.example.currencyconverter.presentation.Success
import com.example.currencyconverter.presentation.adapter.CurrencyListAdapter
import com.example.currencyconverter.presentation.viewModels.CurrencyListViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

@AndroidEntryPoint
class CurrencyListFragment : Fragment() {

    private var _binding: FragmentCurrencyListBinding? = null

    private val binding: FragmentCurrencyListBinding
        get() = _binding ?: throw RuntimeException("FragmentCurrencyListBinding is null")


    private val viewModel: CurrencyListViewModel by viewModels()

    private val adapter = CurrencyListAdapter()

    private var date = LocalDate.now()

    private var searchCharCode = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrencyListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fadeIn = AlphaAnimation(0f, 1f).apply {
            duration = 300
        }

        val fadeOut = AlphaAnimation(1f, 0f).apply {
            duration = 300
        }

        setupRecyclerView()

        binding.etDate.setText(formatLocalDate(date))

        binding.etDate.setOnClickListener {
            showStartDatePicker()
        }

        getAllCurrencies()

        binding.etSearch.doOnTextChanged { text, _, _, _ ->
            if (text?.isBlank() == true){
                getAllCurrencies()
            } else {
                searchCharCode = text.toString()
                getCurrencyByCharCode()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                collectFlow(fadeIn, fadeOut)
            }
        }
    }

    private fun getCurrencyByCharCode() {
        viewModel.getCurrencyListByCharCode(searchCharCode, date.toString())
    }

    private suspend fun collectFlow(
        fadeIn: AlphaAnimation, fadeOut: AlphaAnimation
    ) {
        viewModel.state.collect {
            when (it) {
                is Error -> {
                    showErrorLayout(fadeIn, fadeOut)
                }

                is Loading -> {
                    showLoadingLayout(fadeIn, fadeOut)
                }

                is Success -> {
                    showSuccessLayout(fadeIn, fadeOut)
                    updateRecyclerView(it.result)
                    adapter.onCurrencyClickListener = { currencyId ->
                        findNavController().navigate(
                            CurrencyListFragmentDirections.actionCurrencyListFragmentToCurrencyDetailsFragment(
                                currencyId, date.toString()
                            )
                        )
                    }
                }
            }
        }
    }

    private fun showSuccessLayout(fadeIn: AlphaAnimation, fadeOut: AlphaAnimation) {
        binding.tvError.startAnimation(fadeOut)
        binding.tvError.visibility = View.GONE
        binding.progressBar.startAnimation(fadeOut)
        binding.progressBar.visibility = View.GONE
        binding.rvCurrencyList.startAnimation(fadeIn)
        binding.rvCurrencyList.visibility = View.VISIBLE
    }

    private fun showLoadingLayout(fadeIn: AlphaAnimation, fadeOut: AlphaAnimation) {
        binding.tvError.startAnimation(fadeOut)
        binding.tvError.visibility = View.GONE
        binding.progressBar.startAnimation(fadeIn)
        binding.progressBar.visibility = View.VISIBLE
        binding.rvCurrencyList.startAnimation(fadeIn)
        binding.rvCurrencyList.visibility = View.VISIBLE
    }

    private fun showErrorLayout(fadeIn: AlphaAnimation, fadeOut: AlphaAnimation) {
        binding.tvError.startAnimation(fadeIn)
        binding.tvError.visibility = View.VISIBLE
        binding.progressBar.startAnimation(fadeOut)
        binding.progressBar.visibility = View.GONE
        binding.rvCurrencyList.startAnimation(fadeOut)
        binding.rvCurrencyList.visibility = View.GONE
    }

    private fun setupRecyclerView() {
        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        binding.rvCurrencyList.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.rvCurrencyList.adapter = adapter
    }

    private fun updateRecyclerView(list: List<CurrencyEntity>) {
        adapter.submitList(list)
    }

    private fun getAllCurrencies() {
        viewModel.getCurrencyList(date.toString())
    }

    private fun showStartDatePicker() {
        val datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select date")
            .setSelection(localDateToMillis(date)).build()
        datePicker.addOnPositiveButtonClickListener { dateInMillis ->
            date = millisToLocalDate(dateInMillis)
            binding.etDate.setText(formatLocalDate(date))
            if (searchCharCode.isBlank()){
                getAllCurrencies()
            } else {
                getCurrencyByCharCode()
            }
        }
        datePicker.show(childFragmentManager, "datePicker")
    }

    private fun millisToLocalDate(timeInMillis: Long): LocalDate {
        val instant = Instant.ofEpochMilli(timeInMillis)
        val zoneId = ZoneId.systemDefault()

        return instant.atZone(zoneId).toLocalDate()
    }

    private fun localDateToMillis(date: LocalDate): Long{
        return date.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
    }

    private fun formatLocalDate(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy").withLocale(Locale.getDefault())
        return formatter.format(date)
    }
}