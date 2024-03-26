package com.example.foodsapp.ui.view.food

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.foodsapp.R
import com.example.foodsapp.databinding.FragmentHomeBinding
import com.example.foodsapp.ui.adapter.FoodsAdapter
import com.example.foodsapp.ui.viewmodel.AuthViewModel
import com.example.foodsapp.ui.viewmodel.DetailViewModel
import com.example.foodsapp.ui.viewmodel.HomeViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private val detailViewModel: DetailViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()
    private lateinit var foodsAdapter: FoodsAdapter
    private lateinit var alertDialog: MaterialAlertDialogBuilder
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        foodListObserve()
        onFilterButton()
        binding.imageViewCancel.setOnClickListener {
            authViewModel.logout()
            requireActivity().finish()
        }
        return binding.root
    }

    private fun foodListObserve(){
        viewModel.foodList.observe(viewLifecycleOwner){foodList->
            foodsAdapter = FoodsAdapter(foodList, detailViewModel)
            binding.recyclerView.adapter = foodsAdapter
            binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setFilterFoodName()
        }
    }

    private fun setFilterFoodName(){
        binding.editTextFilter.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val keyword = s.toString().trim()
                filterName(keyword)
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun filterName(keyword: String){
        val filterFoodList = viewModel.foodList.value?.filter { it.foodName?.contains(keyword, ignoreCase = true) ?: false }
        binding.textViewFilter.text = "${filterFoodList?.size ?: 0} Sonuç Bulundu"
        foodsAdapter = FoodsAdapter(filterFoodList!!, detailViewModel)
        binding.recyclerView.adapter = foodsAdapter
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    }

    private fun onFilterButton(){
        binding.constraintLayout.setOnClickListener {
            filterAlertDialog()
        }
    }

    private fun filterAlertDialog() {
        val mDialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.custom_alert_dialog, null)
        val alertDialogStyle = R.style.AlertDialogStyle
        alertDialog = MaterialAlertDialogBuilder(
            ContextThemeWrapper(
                requireContext(),
                alertDialogStyle
            )
        ).setView(mDialogView)
        alertDialog.setNegativeButton("İptal") { _, _ -> }
        alertDialog.setPositiveButton("Tamam") { _, _ ->
            val minPrice = mDialogView.findViewById<EditText>(R.id.editTextMin).text.toString().trim().toInt()
            val maxPrice = mDialogView.findViewById<EditText>(R.id.editTextMax).text.toString().trim().toInt()
            filterPrice(minPrice, maxPrice)
        }
        alertDialogBackground(alertDialog)
        alertDialog.show()
    }

    private fun filterPrice(min: Int, max: Int){
        val filterFoodList = viewModel.foodList.value?.filter { it.foodPrice!! in min..<max }
        binding.textViewFilter.text = "${filterFoodList?.size ?: 0} Sonuç Bulundu"
        foodsAdapter = FoodsAdapter(filterFoodList!!, detailViewModel)
        binding.recyclerView.adapter = foodsAdapter
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun alertDialogBackground(dialogBuilder: MaterialAlertDialogBuilder) {
        val drawableResId = R.drawable.background_alert_dialog
        val backgroundDrawable = resources.getDrawable(drawableResId, null)
        dialogBuilder.background = backgroundDrawable
    }
}