package com.example.foodsapp.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContentProviderCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.foodsapp.R
import com.example.foodsapp.databinding.FragmentHomeBinding
import com.example.foodsapp.ui.adapter.FoodsAdapter
import com.example.foodsapp.ui.viewmodel.DetailViewModel
import com.example.foodsapp.ui.viewmodel.HomeViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private val detailViewModel: DetailViewModel by viewModels()
    private lateinit var foodsAdapter: FoodsAdapter
    private lateinit var alertDialog: MaterialAlertDialogBuilder
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        viewModel.foodList.observe(viewLifecycleOwner){foodList->
            foodsAdapter = FoodsAdapter(foodList, detailViewModel)
            binding.recyclerView.adapter = foodsAdapter
            binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            binding.constraintLayout.setOnClickListener {
                filterAlertDialog()
            }
        }

        return binding.root
    }
    private fun filterPrice(min: Int, max: Int){
        val filterFoodList = viewModel.foodList.value?.filter { it.foodPrice!! in min..<max }
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
}